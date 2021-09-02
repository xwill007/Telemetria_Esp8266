package com.willvargas.telemetria_esp8266.Activities

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.willvargas.telemetria_esp8266.R
import com.willvargas.telemetria_esp8266.databinding.ActivityLoginBinding

public lateinit var userEmail: String

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        auth= Firebase.auth
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
            //rememberUser(sp,usuario,contrasena)

            //val userDAO: UserDAO= MiBaseDeDatosApp.databaseUser.UserDAO()
            //val user: User= userDAO.searchUser(usuario)
            //val nombre = user.nombre

            auth.signInWithEmailAndPassword(usuario,contrasena)
                .addOnCompleteListener{
                    if (it.isSuccessful){
                            val user = auth.currentUser
                            Toast.makeText(this,getString(R.string.user_auth_firebase_ok), Toast.LENGTH_LONG).show()
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("email",usuario)
                            //intent.putExtra("nombre",nombre)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
                            startActivity(intent)
                    }else{
                            var msg =""
                            if(it.exception?.localizedMessage == "The email address is badly formatted."){ //There is no user record corresponding to this identifier. The user may have been deleted.
                                msg = getString(R.string.bad_write_Email)

                            }else if(it.exception?.localizedMessage == "There is no user record corresponding to this identifier. The user may have been deleted."){
                                msg = getString(R.string.user_not_exist)

                            }else if(it.exception?.localizedMessage == "The password is invalid or the user does not have a password."){
                                msg = getString(R.string.fail_user_password)
                            }

                            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
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