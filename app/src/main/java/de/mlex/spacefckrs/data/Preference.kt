package de.mlex.spacefckrs.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class Preference(context: Context) {

    private val preference: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun getHighScore(): Int {
        return preference.getInt(PREF_HIGHSCORE, 0)
    }

    fun setHighScore(score: Int) {
        preference.edit {
            putInt(PREF_HIGHSCORE, score)
        }
    }

    fun getSound(): Boolean {
        return preference.getBoolean(PREF_SOUND, true)
    }

    fun setSound(soundOn: Boolean) {
        preference.edit {
            putBoolean(PREF_SOUND, soundOn)
        }
    }

    companion object {
        private const val PREF_NAME = "SharedPreferencesSpaceFCKRS"
        private const val PREF_HIGHSCORE = "HighScore"
        private const val PREF_SOUND = "Sound"
    }
}
