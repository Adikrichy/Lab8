package com.example.myapplication8

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat.START_STICKY
import androidx.core.app.ServiceCompat.startForeground
import androidx.core.content.ContextCompat.getSystemService
import java.security.Provider

class MyService : Provider.Service() {
    private lateinit var soundPlayer: MediaPlayer
    private val CHANNEL_ID = "channelId"

    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this, "Service Created", Toast.LENGTH_SHORT).show()
        soundPlayer = MediaPlayer.create(this, R.raw.song).apply {
            isLooping = false
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show()
        createNotificationChannel()
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_music)
            .setContentTitle("My Music Player")
            .setContentText("Music is playing")
            .build()
        startForeground(1, notification)
        soundPlayer.start()
        return START_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply { description = "Music Channel" }
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show()
        soundPlayer.stop()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}