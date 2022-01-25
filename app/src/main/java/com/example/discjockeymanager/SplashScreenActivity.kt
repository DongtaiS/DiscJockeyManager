package com.example.discjockeymanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.discjockeymanager.signin.MainActivity
/**
 * Activity for splash screen shown at app launch
 */

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
        finish()
    }
}