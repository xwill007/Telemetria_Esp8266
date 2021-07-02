package com.willvargas.telemetria_esp8266.Activities

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.google.firebase.auth.FirebaseAuth
import com.willvargas.telemetria_esp8266.databinding.ActivityLoginBinding

public lateinit var userEmail: String

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        val data = intent.extras
        val email = data?.getString("email")
        val password = data?.getString("password")
        loginBinding.textImputUser.setText(email).toString()
        loginBinding.textImputPassword.setText(password).toString()

        val sp = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        userEmail = sp.getString("email","").toString()
        checkLogin(sp)

        if (loginBinding.textImputPassword.length() >= 6) loginBinding.buttonLogin.isEnabled = true

        loginBinding.textViewRegistrarse.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        loginBinding.textImputPassword.addTextChangedListener {
            loginBinding.buttonLogin.isEnabled = loginBinding.textImputPassword.length() >= 6
        }

        loginBinding.buttonLogin.setOnClickListener{
            val usuario = loginBinding.textImputUser.text.toString()
            val contrasena = loginBinding.textImputPassword.text.toString()
            rememberUser(sp,usuario,contrasena)

            //val userDAO: UserDAO= MiBaseDeDatosApp.databaseUser.UserDAO()
            //val user: User= userDAO.searchUser(usuario)
            //val nombre = user.nombre

                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(usuario,contrasena)
                    .addOnCompleteListener{
                        if (it.isSuccessful){
                            Toast.makeText(this,"Usuario autenticado correctamente", Toast.LENGTH_LONG).show()
                            val intent = Intent(this, MainActivity::class.java)
                            //intent.putExtra("email",usuario)
                            //intent.putExtra("nombre",nombre)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this,"autenticacion invalida", Toast.LENGTH_LONG).show()
                        }
                    }

        }

    }

    private fun checkLogin(sp: SharedPreferences?) {
        if(sp!!.getString("active","")=="true"){
            Toast.makeText(this,"inicio directo por Preferencias",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }else{
            if(sp!!.getString("remember","")=="true"){
                loginBinding.textImputUser.setText(sp.getString("email",""))
                loginBinding.textImputPassword.setText(sp.getString("password",""))
            }
        }
    }

    private fun rememberUser(sp: SharedPreferences?, email: String, password: String) {
        if(email.isNotEmpty()&& password.isNotEmpty()) {
            if (loginBinding.checkBoxRecordar.isChecked) {
                with(sp!!.edit()) {
                    putString("email", email)
                    putString("password", password)
                    putString("active", "true")
                    putString("remember", "true")
                    apply()
                }
            } else {
                with(sp!!.edit()) {
                    putString("active", "true")
                    putString("remember", "false")
                    apply()
                }
            }
            Toast.makeText(this,"inicio por Preferencias",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }else{
            Toast.makeText(this,"Fallo al guardar Preferencias",Toast.LENGTH_SHORT).show()
        }
    }


}