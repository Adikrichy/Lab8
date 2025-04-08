package com.example.myapplication8

import android.app.Service
import android.content.Intent
import android.os.IBinder

class RandomCharService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val randomChar = ('A'..'Z').random().toString()

        // Отправляем broadcast
        val broadcastIntent = Intent("com.example.myapplication8.RANDOM_CHAR")
        broadcastIntent.putExtra("random_char", randomChar)
        sendBroadcast(broadcastIntent)

        // Останавливаем сервис сразу после работы
        stopSelf()
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
