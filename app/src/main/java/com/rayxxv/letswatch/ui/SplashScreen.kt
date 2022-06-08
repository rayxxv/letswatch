package com.rayxxv.letswatch.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.rayxxv.letswatch.MainActivity
import com.rayxxv.letswatch.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        this.window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(
                Intent(this,
                    MainActivity::class.java)
            )
            finish()
        }, 2500)
    }
}