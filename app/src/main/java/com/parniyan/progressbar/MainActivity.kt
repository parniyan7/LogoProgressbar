package com.parniyan.progressbar

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import com.parniyan.logoprogressbar.ProgressbarView

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val progressBar = findViewById<ProgressbarView>(R.id.customProgressBar)


        //progressBar.setProgressColor(Color.RED)
        //progressBar.setMarkerSize(2000f)
        val colors = intArrayOf(
            Color.parseColor("#b3cde0"), // very light blue
            Color.parseColor("#6497b1"), // light blue
            Color.parseColor("#005b96"), // blue
            Color.parseColor("#03396c"), // dark blue
            Color.parseColor("#011f4b"), // very dark blue
        )
        // progressBar.setProgressColorPalette(colors)
        progressBar.setProgressColorPalette(colors)

        progressBar.setBorderColor(Color.BLACK)
        progressBar.setBorderSize(2f)
        progressBar.setTextColor(Color.BLACK)
        progressBar.setTextSize(24f)

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
