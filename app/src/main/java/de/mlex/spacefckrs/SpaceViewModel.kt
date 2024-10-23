package de.mlex.spacefckrs

import android.util.Log
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.asIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.mlex.spacefckrs.data.Alien
import de.mlex.spacefckrs.data.JustScrap
import de.mlex.spacefckrs.data.JustSpace
import de.mlex.spacefckrs.data.USO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class GameState {
    GameOver, GameIsRunning
}

class SpaceViewModel : ViewModel() {

    val gameState: StateFlow<GameState>

    var aniExpIsPlaying = mutableStateOf(false)

    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    private var _aliens = MutableStateFlow<List<USO>>(emptyList())
    val aliens = _aliens.asStateFlow()

    private var _nextDamage: MutableIntState = mutableIntStateOf(1)
    val nextDamage = _nextDamage.asIntState()

    private val _score: MutableIntState = mutableIntStateOf(0)
    val score = _score.asIntState()


    init {
        reset()
        _isReady.value = true

        gameState = aliens.map {
            if (it.size > 25) GameState.GameOver
            else GameState.GameIsRunning
        }.stateIn(viewModelScope, SharingStarted.Eagerly, GameState.GameIsRunning)
    }

    fun determineDamageAndExplode(cannon: Int) {
        var remainingDamage = _nextDamage.intValue
        var hasChanged = false
        val newList = _aliens.value.reversed().mapIndexed { index, field ->
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
            _aliens.tryEmit(newList)
            aniExpIsPlaying.value = true
        } else cleanUp()
    }

    fun cleanUp() {

        cleanUpScraps()
        cleanUpFirstRow()
        reset()
    }

    private fun reset() {

        createNewRowOfAliens()
        getNextDamage()
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
            _aliens.tryEmit(newList)
        }

    }

    private fun cleanUpFirstRow() {

        val hasEmptyRow = _aliens.value.chunked(5).last().none { it is Alien }
        if (hasEmptyRow) {
            Log.i("cleanUpFirstRow", "row deleted")
            val newList = _aliens.value.chunked(5).toMutableList()
            newList.removeAt(newList.lastIndex)
            viewModelScope.launch { _aliens.emit(newList.flatten()) }
        }

    }

    private fun createNewRowOfAliens() {
        val newAliens: MutableList<USO> = mutableListOf()
        do {
            newAliens.clear()
            for (n in 1..5) {
                when ((0..8).random()) {
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

    fun resetGame() {

        viewModelScope.launch {
            _aliens.emit(emptyList())
        }
        reset()
        _score.intValue = 0
    }
}



