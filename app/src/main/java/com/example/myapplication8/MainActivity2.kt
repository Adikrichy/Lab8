package com.example.myapplication8

import android.Manifest
import android.content.*
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity2 : AppCompatActivity() {

    private lateinit var textViewChar: TextView

    // Лаунчер для запроса разрешений
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                val randomChar = ('A'..'Z').random()
                Toast.makeText(this, "Разрешение получено. Буква: $randomChar", Toast.LENGTH_SHORT).show()
                textViewChar.text = randomChar.toString()
            } else {
                Toast.makeText(this, "Разрешение отклонено", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        textViewChar = findViewById(R.id.textViewChar)
        val buttonStart = findViewById<Button>(R.id.buttonStartService)

        buttonStart.setOnClickListener {
            requestPermission()
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            // Для старых версий Android просто покажем букву
            val randomChar = ('A'..'Z').random()
            Toast.makeText(this, "Буква: $randomChar", Toast.LENGTH_SHORT).show()
            textViewChar.text = randomChar.toString()
        }
    }
}
