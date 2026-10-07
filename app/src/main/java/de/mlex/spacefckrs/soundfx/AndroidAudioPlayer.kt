package de.mlex.spacefckrs.soundfx

import android.content.Context
import android.media.MediaPlayer

class AndroidAudioPlayer(private val context: Context) : AudioPlayer {

    private var player: MediaPlayer? = null

    override fun playFile(file: Int) {
        player?.stop()
        player?.release()
        player = null

        val newPlayer = MediaPlayer.create(context, file) ?: return
        player = newPlayer
        newPlayer.setOnCompletionListener { mp ->
            mp.release()
            if (player == mp) {
                player = null
            }
        }
        newPlayer.start()
    }

    override fun stopPlaying() {
        player?.stop()
        player?.release()
        player = null
    }
}