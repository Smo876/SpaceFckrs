package de.mlex.spacefckrs

import android.app.Application
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.asIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import de.mlex.spacefckrs.data.Alien
import de.mlex.spacefckrs.data.JustScrap
import de.mlex.spacefckrs.data.JustSpace
import de.mlex.spacefckrs.data.USO
import de.mlex.spacefckrs.soundfx.AndroidAudioPlayer
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

enum class GameState {
    GameOver, GameIsRunning
}

enum class CannonState {
    IsReady, IsFiring, WasDestroyed
}


class SpaceViewModel(appContext: Application) : AndroidViewModel(appContext) {

    val gameState: StateFlow<GameState>

    private val _cannonState = MutableStateFlow(CannonState.IsReady)
    val cannonState = _cannonState.asStateFlow()

    private val _viewModelIsReady = MutableStateFlow(false)
    val viewModelIsReady = _viewModelIsReady.asStateFlow()

    private var _aliens = MutableStateFlow<List<USO>>(emptyList())
    val aliens = _aliens.asStateFlow()

    private var _nextDamage: MutableIntState = mutableIntStateOf(1)
    val nextDamage = _nextDamage.asIntState()

    private val _score: MutableIntState = mutableIntStateOf(0)
    val score = _score.asIntState()

    private val _soundIsOn = MutableStateFlow(false)
    val soundIsOn = _soundIsOn.asStateFlow()

    private val audioPlayer by lazy {
        AndroidAudioPlayer(appContext)
    }

    init {
        reset()
        gameState = aliens.map {
            if (it.size > 30) {
                _cannonState.value = CannonState.WasDestroyed
                GameState.GameOver
            } else GameState.GameIsRunning
        }.stateIn(viewModelScope, SharingStarted.Eagerly, GameState.GameIsRunning)
        _viewModelIsReady.value = true
    }

    fun setSound(soundIsOn: Boolean) {
        _soundIsOn.value = soundIsOn
    }

    private fun reset() {
        createNewRowOfAliens()
        getNextDamage()
        _cannonState.value = CannonState.IsReady
        //if (_soundIsOn.value) audioPlayer.stopPlaying()

    }

    private fun createNewRowOfAliens() {
        val newAliens: MutableList<USO> = mutableListOf()
        do {
            newAliens.clear()
            for (n in 1..5) {
                when ((0..12).random()) {
                    0 -> newAliens.add(Alien(R.drawable.sf_alien1, 1))
                    1 -> newAliens.add(Alien(R.drawable.sf_alien2, 2))
                    2 -> newAliens.add(Alien(R.drawable.sf_alien3, 3))
                    3 -> newAliens.add(Alien(R.drawable.sf_alien4, 4))
                    else -> newAliens.add(JustSpace())
                }
            }
        } while (!newAliens.any { it is Alien })
        newAliens += _aliens.value.toMutableList()
        viewModelScope.launch {
            _aliens.emit(newAliens)
        }
    }

    private fun getNextDamage() {
        _nextDamage.intValue = (4..6).random()
    }

    fun determineDamageAndExplode(cannon: Int) {
        _cannonState.value = CannonState.IsFiring
        var remainingDamage = _nextDamage.intValue
        var hasChanged = false
        val newList = _aliens.value
            .reversed()
            .mapIndexed { index, field ->
                if (field is Alien && index % 5 == 5 - cannon) {
                    if (remainingDamage > 0) {
                        if (field.life >= remainingDamage) {
                            field.life -= remainingDamage
                            _score.intValue += remainingDamage
                            remainingDamage = 0
                        } else {
                            remainingDamage -= field.life
                            _score.intValue += field.life
                            field.life = 0
                        }
                    }
                    if (field.life == 0) {
                        hasChanged = true
                        JustScrap()
                    } else field
                } else field
            }.reversed()
        if (hasChanged) {
            viewModelScope.launch {
                delay(0.2.seconds)
                _aliens.tryEmit(newList)
            }
        } else cleanUp()
    }

    fun cleanUp() {
        cleanUpScraps()
        cleanEmptyRows()
        reset()
    }

    private fun cleanUpScraps() {
        var hasChanged = false
        val newList = _aliens.value.map {
            if (it is JustScrap) {
                hasChanged = true
                JustSpace()
            } else it
        }
        if (hasChanged) {
            viewModelScope.launch {
                _aliens.emit(newList)
            }
        }
    }

    private fun cleanEmptyRows() {
        var hasChanged = false
        var hasSeenAlien = false
        var hasDeleteOneRow = false
        val newList = _aliens.value
            .chunked(5)
            .reversed()
            .asSequence()
            .onEach { chunk ->
                if (chunk.any { it is Alien }) {
                    hasSeenAlien = true
                }
            }
            .filterNot { chunk ->
                val filter = chunk.none { it is Alien } && !hasDeleteOneRow
                if (filter && hasSeenAlien) hasDeleteOneRow = true
                hasChanged = true
                filter
            }
            .toList()
            .reversed()
            .flatten()
        if (hasChanged) {
            viewModelScope.launch {
                _aliens.emit(newList)
            }
        }
    }

    fun resetGame() {
        viewModelScope.launch {
            _aliens.emit(emptyList())
        }
        _score.intValue = 0
        _cannonState.value = CannonState.IsReady
        reset()
    }

    fun playPiuSound() {
        if (soundIsOn.value) audioPlayer.playFile(R.raw.piu)
    }

    fun playBrrrSound() {
        if (soundIsOn.value) audioPlayer.playFile(R.raw.brrr)
    }
}

