package com.rayxxv.letswatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.rayxxv.letswatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val window = this.window
        window.statusBarColor = ContextCompat.getColor(this, R.color.transparent)

        WindowInsetsControllerCompat(window, window.decorView)
            .isAppearanceLightStatusBars = true

    }
}