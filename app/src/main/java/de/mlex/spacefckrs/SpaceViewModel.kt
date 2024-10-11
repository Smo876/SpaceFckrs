package de.mlex.spacefckrs

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class SpaceViewModel : ViewModel() {

    //private val aliens: MutableList<Alien> = mutableListOf()
    private lateinit var aliens: Flow<MutableList<Alien>>


    fun getAliens(): Flow<MutableList<Alien>> {
        return aliens
    }

    fun createNewRowOfAliens() {
        for (n in 1..5) {
            lateinit var alien: Alien
            when ((0..3).random()) {
                0 -> {
                    alien = Alien(R.drawable.sf_alien1, 1)
                }

                1 -> {
                    alien = Alien(R.drawable.sf_alien1, 2)
                }

                2 -> {
                    alien = Alien(R.drawable.sf_alien1, 3)
                }

                3 -> {
                    alien = Alien(R.drawable.sf_alien1, 4)
                }
            }
            aliens.add(alien)
        }

    }
}

data class Alien(val type: Int = 0, val live: Int = 0)
