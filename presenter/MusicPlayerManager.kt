package com.example.samplethread.presenter

import android.media.MediaPlayer

class MusicPlayerManager {
    private var songState: SongState? = null
    private var mMediaPlayer: MediaPlayer? = null

    init {
        songState = SongState.IDLE
    }

    private enum class SongState {
        IDLE,
        PLAYING
    }

    fun play(uri: String) {
        if (songState != SongState.IDLE) {
            return
        }
        mMediaPlayer = MediaPlayer()
        mMediaPlayer?.setDataSource(uri)
        mMediaPlayer?.prepare()
        mMediaPlayer?.setOnPreparedListener {
            mMediaPlayer?.start()
            songState = SongState.PLAYING
        }
        mMediaPlayer?.setOnCompletionListener {
            mMediaPlayer?.release()
            songState = SongState.IDLE
        }

    }

    fun isSongPlaying(): Boolean {
        return songState == SongState.PLAYING
    }

    fun stop() {
        if (songState != SongState.IDLE) {
            mMediaPlayer?.stop()
            mMediaPlayer?.release()
            mMediaPlayer = null
            songState = SongState.IDLE
        }
    }

}
