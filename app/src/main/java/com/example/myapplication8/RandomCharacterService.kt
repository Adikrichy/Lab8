package com.example.myapplication8

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import kotlin.random.Random

class RandomCharacterService : Service() {
    private var isRandomGeneratorOn = false
    private val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()
    private val TAG = "RandomCharacterService"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Используем контекст сервиса для Toast
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show()
        Log.i(TAG, "Service started...")

        isRandomGeneratorOn = true
        Thread {
            try {
                startRandomGenerator()
            } catch (e: Exception) {
                Log.e(TAG, "Error in thread: ${e.message}")
            }
        }.start()

        return START_STICKY
    }

    private fun startRandomGenerator() {
        while (isRandomGeneratorOn) {
            try {
                Thread.sleep(1000)
                if (isRandomGeneratorOn) {
                    // Генерация случайного индекса через Kotlin Random
                    val randomIdx = Random.nextInt(0, 25)
                    val randomChar = alphabet[randomIdx]

                    Log.i(TAG, "Random Character: $randomChar")

                    // Отправка Broadcast
                    val broadcastIntent = Intent("my.custom.action.tag.lab6").apply {
                        putExtra("randomCharacter", randomChar)
                    }
                    sendBroadcast(broadcastIntent)
                }
            } catch (e: InterruptedException) {
                Log.i(TAG, "Thread Interrupted")
                Thread.currentThread().interrupt() // Восстанавливаем статус прерывания
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isRandomGeneratorOn = false // Остановка цикла
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show()
        Log.i(TAG, "Service Destroyed")
    }

    override fun onBind(intent: Intent?): IBinder? = null
}