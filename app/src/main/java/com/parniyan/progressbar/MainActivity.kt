package com.parniyan.progressbar

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val progressBar = findViewById<ProgressbarView>(R.id.customProgressBar)
       progressBar.setProgressColor(Color.RED)
        //progressBar.setMarkerSize(2000f)
        val colors = intArrayOf(
            Color.parseColor("#FF0000"), // Red
            Color.parseColor("#FFFF00"), // Yellow
            Color.parseColor("#00FF00"), // Green
            Color.parseColor("#00FFFF"), // Cyan
            Color.parseColor("#0000FF"), // Blue
            Color.parseColor("#FF00FF")  // Magenta
        )
        progressBar.setProgressColorPalette(colors)
        progressBar.setBorderColor(Color.BLACK)
        progressBar.setBorderSize(2f)
        progressBar.setTextColor(Color.BLACK)
        progressBar.setTextSize(24f)
        //progressBar.setProgressColors(colors)
        val markerDrawable = ContextCompat.getDrawable(this, R.drawable.cat)
        if (markerDrawable != null) {
            progressBar.setMarkerDrawable(markerDrawable)
        }

        val seekBar = findViewById<SeekBar>(R.id.seekBar)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                progressBar.setProgress(progress.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
    }
