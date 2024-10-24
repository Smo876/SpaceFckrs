package de.mlex.spacefckrs.data

import android.content.Context

class Preference(context: Context) {

    val PREF_NAME = "SharedPreferencesSpaceFCKRS"
    val PREF_HIGHSCORE = "HighScore"
    val preference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun getHighScore() : Int {
        return preference.getInt(PREF_HIGHSCORE, 0)
    }

    fun setHighScore(score: Int) {
        val editor = preference.edit()
        editor.putInt(PREF_HIGHSCORE, score)
        editor.apply()
    }

}