package com.willvargas.telemetria_esp8266.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.willvargas.telemetria_esp8266.R
import java.util.*
import kotlin.concurrent.timerTask

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash)

        val timer = Timer()
        timer.schedule(timerTask {
            goToMainActivity()
        },1000
        )
    }

    private fun goToMainActivity() {
        val intent = Intent (this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}