package com.example.bptracker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bptracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnAddReading.setOnClickListener {
            startActivity(Intent(this, com.example.bptracker.ui.AddReadingActivity::class.java))
        }

        binding.btnViewHistory.setOnClickListener {
            startActivity(Intent(this, com.example.bptracker.ui.HistoryActivity::class.java))
        }

        binding.btnViewChart.setOnClickListener {
            startActivity(Intent(this, com.example.bptracker.ui.ChartActivity::class.java))
        }
    }
}