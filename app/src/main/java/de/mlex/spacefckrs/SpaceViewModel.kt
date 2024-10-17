package de.mlex.spacefckrs

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.asIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.mlex.spacefckrs.data.Alien
import de.mlex.spacefckrs.data.JustSpace
import de.mlex.spacefckrs.data.USO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SpaceViewModel : ViewModel() {
    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    private var _aliens = MutableStateFlow<List<USO>>(emptyList())
    val aliens = _aliens.asStateFlow()

    private var _nextDamage: MutableIntState = mutableIntStateOf(1)
    val nextDamage = _nextDamage.asIntState()

    private val _alienRows: MutableIntState = mutableIntStateOf(0)
    val alienRows = _alienRows.asIntState()

    private val _score: MutableIntState = mutableIntStateOf(0)
    val score = _score.asIntState()

    init {
        executeMove(0)
        _isReady.value = true
    }

    fun executeMove(cannon: Int) {
        determineDamage(cannon)
        createNewRowOfAliens()
        //TODO: berechnung stimmt nicht
        _score.intValue += nextDamage.intValue
        getNextDamage()
    }

    private fun determineDamage(cannon: Int) {
        var remainingDamage = _nextDamage.intValue
        _aliens.value
            .filterIndexed { index, _ ->
                index == 0 + cannon - 1 ||
                        index == 5 + cannon - 1 ||
                        index == 10 + cannon - 1 ||
                        index == 15 + cannon - 1 ||
                        index == 20 + cannon - 1
            }
            .reversed()
            .filterIsInstance<Alien>()
            .forEach {
                if (remainingDamage > 0) {
                    if (it.life >= _nextDamage.intValue) {
                        it.life -= nextDamage.intValue
                        remainingDamage = 0
                    } else {
                        remainingDamage -= it.life
                        it.life = 0
                    }
                }

            }
        //TODO: tote Aliens löschen
//        _aliens.value
//            .filterIsInstance<Alien>()
//            .forEachIndexed { index, value ->
//            if (value.life == 0) {
//                _aliens.value[index].set(JustSpace())
//            }
//        }
    }

    private fun createNewRowOfAliens() {
        _alienRows.intValue++
        val newAliens: MutableList<USO> = mutableListOf()
        for (n in 1..5) {
            when ((0..5).random()) {
                0 -> newAliens.add(Alien(R.drawable.sf_alien1, 1))
                1 -> newAliens.add(Alien(R.drawable.sf_alien2, 2))
                2 -> newAliens.add(Alien(R.drawable.sf_alien3, 3))
                3 -> newAliens.add(Alien(R.drawable.sf_alien4, 4))
                else -> newAliens.add(JustSpace())
            }
        }
        newAliens += _aliens.value.toMutableList()
        viewModelScope.launch {
            _aliens.emit(newAliens)
        }
    }

    private fun getNextDamage() {
        _nextDamage.value = (1..5).random()
    }

    //TODO: da passiert einfach nix
    fun resetGame() {
        _aliens = MutableStateFlow(emptyList())
        executeMove(0)
        _score.intValue = 0
    }
}



