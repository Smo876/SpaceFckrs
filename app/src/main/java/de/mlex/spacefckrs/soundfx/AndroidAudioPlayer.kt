package de.mlex.spacefckrs.soundfx

import android.content.Context
import android.media.MediaPlayer

class AndroidAudioPlayer(private val context: Context) : AudioPlayer {

    private var player: MediaPlayer? = null

    override fun playFile(file: Int) {
        MediaPlayer.create(context, file).apply {
            player = this
            start()
        }
    }

    override fun stopPlaying() {
        player?.stop()
        player?.release()
        player = null
    }
}