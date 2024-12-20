package de.mlex.spacefckrs

import android.app.Application
import android.util.Log
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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

enum class GameState {
    GameOver, WaitingForPlayer, ProcessingUsingInput, IsAnimating, CleanUp, Preparing
}

class SpaceViewModel(appContext: Application) : AndroidViewModel(appContext) {

    private val _gameState = MutableStateFlow(GameState.Preparing)
    val gameState = _gameState.asStateFlow()

    private val _viewModelIsReady = MutableStateFlow(false)
    val viewModelIsReady = _viewModelIsReady.asStateFlow()

    private var _aliens = MutableStateFlow<List<USO>>(emptyList())
    val aliens = _aliens.asStateFlow()

    private var _nextDamage: MutableIntState = mutableIntStateOf(1)
    val nextDamage = _nextDamage.asIntState()

    private val _score: MutableIntState = mutableIntStateOf(0)
    val score = _score.asIntState()

    private val _soundIsOn = MutableStateFlow(false)
    private val soundIsOn = _soundIsOn.asStateFlow()

    private val audioPlayer by lazy {
        AndroidAudioPlayer(appContext)
    }

    init {
        viewModelScope.launch {
            _gameState.collect {
                Log.i("GameState", it.name)
                when (it) {
                    GameState.Preparing -> {
                        reset()
                    }

                    GameState.WaitingForPlayer -> {
                    }

                    GameState.ProcessingUsingInput -> {
                    }

                    GameState.IsAnimating -> {
                        animateExplosion()
                    }

                    GameState.CleanUp -> {
                        cleanUp()
                    }

                    GameState.GameOver -> {
                        playOhSound()
                    }
                }
            }
        }

        _viewModelIsReady.value = true
    }

    private fun reset() {
        getNextDamage()
        createNewRowOfAliens()
    }

    private fun getNextDamage() {
        _nextDamage.intValue = (4..6).random()
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
            if (gameOverCheck()) _gameState.value = GameState.GameOver
            else _gameState.value = GameState.WaitingForPlayer
        }
    }


    private fun gameOverCheck(): Boolean {
        if (_aliens.value.size > 30) return true
        else return false
    }

    fun onShot() {
        _gameState.value = GameState.ProcessingUsingInput
    }

    fun afterShot(cannon: Int) {
        if (determineDamage(cannon)) GameState.IsAnimating
        else _gameState.value = GameState.CleanUp
    }

    private fun determineDamage(cannon: Int): Boolean {
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
                        field.hitHart = true
                        field
                    } else field
                } else field
            }.reversed()

        if (hasChanged) {
            viewModelScope.launch {
                _aliens.tryEmit(newList)
                _gameState.value = GameState.IsAnimating
            }
        }
        return hasChanged
    }

    private fun animateExplosion() {
        var onlyOnce = false
        val newList = _aliens.value
            .reversed()
            .map {
                if (it is Alien && it.hitHart && !onlyOnce) {
                    onlyOnce = true
                    JustScrap()
                } else it
            }.reversed()

        viewModelScope.launch {
            _aliens.emit(newList)
        }

        viewModelScope.launch {
            delay(0.3.seconds)
            val moreScrap = _aliens.value.find {
                (it is Alien && it.hitHart)
            }
            if (moreScrap != null) animateExplosion()
            else {
                delay(0.5.seconds)
                _gameState.value = GameState.CleanUp
            }
        }
    }

//    fun animationFinished() {
//        val moreScrap = _aliens.value.find {
//            (it is Alien && it.hitHart)
//        }
//        if (moreScrap != null) animateExplosion()
//        else _gameState.value = GameState.CleanUp
//    }


    fun resetGame() {
        viewModelScope.launch {
            _aliens.emit(emptyList())
        }
        _score.intValue = 0
        _gameState.value = GameState.Preparing
    }


    private fun cleanUp() {
        cleanUpScraps()
        cleanEmptyRows()
        _gameState.value = GameState.Preparing
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


    fun setSound(soundIsOn: Boolean) {
        _soundIsOn.value = soundIsOn
    }

    fun playPiuSound() {
        if (soundIsOn.value) audioPlayer.playFile(R.raw.piu)
    }

    fun playBrrrSound() {
        if (soundIsOn.value) audioPlayer.playFile(R.raw.brrr)
    }

    fun playDuedeldueSound() {
        if (soundIsOn.value) audioPlayer.playFile(R.raw.duedeldue)
    }

    fun playPiepSound() {
        if (soundIsOn.value) audioPlayer.playFile(R.raw.piep)
    }

    private fun playOhSound() {
        if (soundIsOn.value) audioPlayer.playFile(R.raw.oh)
    }

}

