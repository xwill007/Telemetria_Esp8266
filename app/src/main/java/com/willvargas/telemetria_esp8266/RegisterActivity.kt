package com.willvargas.telemetria_esp8266

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.willvargas.telemetria_esp8266.data.local.entities.User
import com.willvargas.telemetria_esp8266.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity() {

    private lateinit var registerBinding: ActivityRegisterBinding
    private var users: MutableList<User> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        registerBinding.saveButton.isEnabled = false

        registerBinding.repPasswordEditText.addTextChangedListener {
                registerBinding.saveButton.isEnabled = registerBinding.repPasswordEditText.length() >= 6
        }

        registerBinding.saveButton.setOnClickListener {
            Log.d("Click", "true")
            val name = registerBinding.nameTextInputEditText.text.toString()
            val phoneNumber = registerBinding.phoneTextInputEditText.text.toString()
            val email = registerBinding.emailEditText.text.toString()
            val password = registerBinding.passwordEditText.text.toString()
            val repPassword = registerBinding.repPasswordEditText.text.toString()

            if (email.isNotEmpty() and name.isNotEmpty() and phoneNumber.isNotEmpty() ) {
                if (password == repPassword) {
                    registerBinding.repPasswordTextInputLayout.error = null
                    saveUser( name, phoneNumber,email, password)

                } else {
                    registerBinding.repPasswordTextInputLayout.error = getString(R.string.pasword_error)
                }
            } else Toast.makeText(this, getString(R.string.data_Error), Toast.LENGTH_LONG).show()
        }



    }


    private fun saveUser(name: String,phoneNumber: String, email: String, password: String ) {
        val newUser = User(name,phoneNumber, email, password)
        users.add(newUser)
        goToLogin()
    }

    private fun goToLogin() {
        //val user =
        val intent = Intent(this,LoginActivity::class.java)
        intent.putExtra("email",registerBinding.emailEditText.text.toString())
        intent.putExtra("password",registerBinding.passwordEditText.text.toString())
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)  //finish() //destruye el registro en la pila para no volver a esta actividad con el boton atras
        startActivity(intent)
    }

}


