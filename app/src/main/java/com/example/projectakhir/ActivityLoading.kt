package com.example.projectakhir

import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.content.Intent
import android.os.Bundle
import android.os.Handler

class ActivityLoading : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_loading)
        val decorView = window.decorView
        val uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
        decorView.systemUiVisibility = uiOptions
        supportActionBar?.hide()
        Handler().postDelayed({
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }, 2000)
    }
}