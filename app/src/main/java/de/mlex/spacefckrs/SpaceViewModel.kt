package de.mlex.spacefckrs

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
        executeMove(0)
        _isReady.value = true

        gameState = aliens.map {
            if (it.size > 25) GameState.GameOver
            else GameState.GameIsRunning
        }.stateIn(viewModelScope, SharingStarted.Eagerly, GameState.GameIsRunning)
    }

    fun executeMove(cannon: Int) {
        determineDamage(cannon)
        deleteDeadAliens()
        createNewRowOfAliens()
        getNextDamage()
    }

    private fun determineDamage(cannon: Int) {
        var remainingDamage = _nextDamage.intValue
        _aliens.value
            .filterIndexed { index, _ ->
                index % 5 == cannon - 1
            }
            .reversed()
            .filterIsInstance<Alien>()
            .forEach {
                if (remainingDamage > 0) {
                    if (it.life >= remainingDamage) {
                        it.life -= remainingDamage
                        _score.intValue += remainingDamage
                        remainingDamage = 0
                    } else {
                        remainingDamage -= it.life
                        _score.intValue += it.life
                        it.life = 0
                    }
                    if (it.life == 0) aniExpIsPlaying.value = true
                }
            }
    }

    private fun deleteDeadAliens() {
        val newAliens: MutableList<USO> = mutableListOf()
        _aliens.value.reversed().forEach { it ->
            when (it) {
                is JustSpace -> newAliens.add(JustSpace())
                is Alien -> {
                    if (it.life > 0) {
                        newAliens.add(it)
                    } else newAliens.add(JustScrap())
                }
                is JustScrap -> newAliens.add(JustSpace())
            }
            println(newAliens.size)
            if (newAliens.size == 5 && !(newAliens.any { it is Alien })) {
                newAliens.clear()
                println("list cleared")
            }
        }
        viewModelScope.launch {
            _aliens.emit(newAliens.reversed())
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
        _nextDamage.value = (4..6).random()
    }

    fun resetGame() {
        val newAliens: MutableList<USO> = mutableListOf()
        viewModelScope.launch {
            _aliens.emit(newAliens)
        }
        executeMove(0)
        _score.intValue = 0
    }
}



