package com.example.rickandmortyappcologviu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import com.example.rickandmortyappcologviu.util.LocalModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //cerinta e: numar de accesari ale paginii
        LocalModel.mainPageCount++
        val accessCounterTextView: TextView = findViewById(R.id.accessCounterTextView)
        accessCounterTextView.text = "Page views: ${LocalModel.mainPageCount}"

        val mainImage: ImageView = findViewById(R.id.mainImage)

        //cerinta a: animatie
        val rotateAnimation = RotateAnimation(
            0f, 720f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotateAnimation.duration = 3000
        rotateAnimation.fillAfter = true
        mainImage.startAnimation(rotateAnimation)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 3000)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}
