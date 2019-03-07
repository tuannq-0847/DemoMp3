package com.example.samplethread.view

import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samplethread.R
import com.example.samplethread.model.Music
import com.example.samplethread.presenter.Constant
import com.example.samplethread.presenter.MusicPlayerManager
import com.example.samplethread.presenter.MusicManager
import com.example.samplethread.presenter.service.MusicService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MusicAdapter.OnItemClickListener {
    private lateinit var mAdapter: MusicAdapter
    private lateinit var musicPlayerManager: MusicPlayerManager


    companion object {
        const val TAG = "MainActivity"
    }


    override fun onItemClicked(music: Music, position: Int) {
        if (musicPlayerManager.isSongPlaying()) {
            musicPlayerManager.stop()
        }
        val intent = Intent()
        intent.action = applicationContext.packageName
        intent.putExtra(Constant.INTENT_NAME_ARTIST, music.songArtist)
        intent.putExtra(Constant.INTENT_NAME_SONG, music.songName)
        sendBroadcast(intent)
        musicPlayerManager.play(music.songUri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponents()
        val intent = Intent(this, MusicService::class.java)
        startService(intent)
    }

    private fun initComponents() {
        musicPlayerManager = MusicPlayerManager()
        val musicManager = MusicManager()
        mAdapter = MusicAdapter(this, musicManager.getSongs(this))
        mAdapter.setOnItemClickListener(this)
        rcvSong.layoutManager = LinearLayoutManager(this)
        rcvSong.adapter = mAdapter
    }


}
