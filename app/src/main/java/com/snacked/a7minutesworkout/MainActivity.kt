package com.snacked.a7minutesworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import com.snacked.a7minutesworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //val flStarButton : FrameLayout = findViewById(R.id.flStart)
        binding?.flStart?.setOnClickListener {
           val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }

        binding?.bmi?.setOnClickListener {
            val intent = Intent(this, CalculateBmi::class.java )
            startActivity(intent)
        }

        binding?.history?.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}