package de.mlex.spacefckrs.data

import android.content.Context
import android.content.SharedPreferences

class Preference(context: Context) {

    private val PREF_NAME = "SharedPreferencesSpaceFCKRS"
    private val PREF_HIGHSCORE = "HighScore"
    private val PREF_SOUND = "Sound"
    private val preference: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun getHighScore(): Int {
        return preference.getInt(PREF_HIGHSCORE, 0)
    }

    fun setHighScore(score: Int) {
        val editor = preference.edit()
        editor.putInt(PREF_HIGHSCORE, score)
        editor.apply()
    }

    fun getSound(): Boolean {
        return preference.getBoolean(PREF_SOUND, true)
    }

    fun setSound(soundOn: Boolean) {
        val editor = preference.edit()
        editor.putBoolean(PREF_SOUND, soundOn)
        editor.apply()
    }
}