package com.willvargas.telemetria_esp8266.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.willvargas.telemetria_esp8266.R
import java.security.AccessController.getContext
import java.util.*
import kotlin.concurrent.timerTask

class SplashActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash)
        auth= Firebase.auth
        val timer = Timer()
        timer.schedule(timerTask {
            if (auth.currentUser != null){
                goToMainActivity() //USUARIO autenticado
            }else{goToLoginActivity()}
        },1000
        )
    }

    private fun goToLoginActivity() {
        val intent = Intent (this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToMainActivity() {
        val intent = Intent (this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}