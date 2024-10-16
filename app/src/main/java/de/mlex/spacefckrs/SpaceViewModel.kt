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

    init {
        nextMove()
        _isReady.value = true
    }

    fun nextMove() {
        createNewRowOfAliens()
        getNexDamage()
    }

    fun createNewRowOfAliens() {
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

    fun getNexDamage() {
        _nextDamage.value = (1..5).random()
    }
}



