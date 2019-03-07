package com.example.samplethread.presenter.service

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.example.samplethread.view.MainActivity
import com.example.samplethread.R
import com.example.samplethread.model.Music
import com.example.samplethread.presenter.Constant

class MusicService : Service() {
    var songName: String? = null
    private var songArtist: String? = null

    companion object {
        const val TAG = "MusicService"
        const val CHANNEL_ID = "notification"
    }

    private val broadcastNotification = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            songName = intent?.getStringExtra(Constant.INTENT_NAME_SONG)
            songArtist = intent?.getStringExtra(Constant.INTENT_NAME_ARTIST)
            if (songName != null && songArtist != null) {
                startNotification()
            }
        }

    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
    }


    override fun onCreate() {
        super.onCreate()
        val intentFilter = IntentFilter()
        intentFilter.addAction(applicationContext.packageName)

        registerReceiver(broadcastNotification, intentFilter)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    private fun startNotification() {
        val notificationIntent = Intent(this, MusicService::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "notification_name"
            val description = "notification_description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
            Notification.Builder(this, CHANNEL_ID)
        } else {
            Notification.Builder(this)
        }
            .setContentText(songName)
            .setContentTitle(songArtist)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(Constant.NOTIFICATION_ID, notification)
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastNotification)
    }


}