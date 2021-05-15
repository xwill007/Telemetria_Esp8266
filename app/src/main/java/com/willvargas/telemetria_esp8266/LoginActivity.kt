package com.willvargas.telemetria_esp8266

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.willvargas.telemetria_esp8266.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        loginBinding.textViewRegistrarse.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }


        loginBinding.textImputPassword.addTextChangedListener {
            loginBinding.buttonLogin.isEnabled = loginBinding.textImputPassword.length() >= 6
        }

        loginBinding.buttonLogin.setOnClickListener{
            val usuario = loginBinding.textImputUser.text.toString()
            val contrasena = loginBinding.textImputPassword.text.toString()

            val data = intent.extras
            val email = data?.getString("email")
            val password = data?.getString("password")

            if ( (usuario == email) and (contrasena == password) ){
                val intent = Intent(this,MainActivity::class.java)
                intent.putExtra("email",email)
                intent.putExtra("password",password)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }else Toast.makeText(this, getString(R.string.fail_user_password), Toast.LENGTH_LONG).show()
        }

    }


}