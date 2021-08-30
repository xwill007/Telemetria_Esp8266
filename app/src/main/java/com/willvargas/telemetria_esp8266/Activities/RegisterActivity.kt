package com.willvargas.telemetria_esp8266.Activities

import android.content.ComponentName
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.willvargas.telemetria_esp8266.MiBaseDeDatosApp
import com.willvargas.telemetria_esp8266.R
import com.willvargas.telemetria_esp8266.data.local.dao.UserDAO
import com.willvargas.telemetria_esp8266.data.local.entities.User
import com.willvargas.telemetria_esp8266.databinding.ActivityRegisterBinding
import java.sql.Types
import java.sql.Types.NULL


class RegisterActivity : AppCompatActivity() {

    private lateinit var registerBinding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var name:String
    private lateinit var phoneNumber:String
    private lateinit var email:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        auth= Firebase.auth

        registerBinding.saveButton.isEnabled = false

        registerBinding.repPasswordEditText.addTextChangedListener {
                registerBinding.saveButton.isEnabled = registerBinding.repPasswordEditText.length() >= 6
        }

        registerBinding.saveButton.setOnClickListener {
            name = registerBinding.nameTextInputEditText.text.toString()
            phoneNumber = registerBinding.phoneTextInputEditText.text.toString()
            email = registerBinding.emailEditText.text.toString()
            val password = registerBinding.passwordEditText.text.toString()
            val repPassword = registerBinding.repPasswordEditText.text.toString()

            if (email.isNotEmpty() and name.isNotEmpty() and phoneNumber.isNotEmpty() ) {
                if (password == repPassword) {
                    registerBinding.repPasswordTextInputLayout.error = null
                    autenticarFirebaseConEmailPassword(email,password)
                    //guardarDeudorEnLocal(name,phoneNumber,email)
                    goToLogin()
                } else {
                    registerBinding.repPasswordTextInputLayout.error = getString(R.string.pasword_error)
                }
            } else Toast.makeText(this, getString(R.string.data_Error), Toast.LENGTH_LONG).show()
        }



    }

    private fun autenticarFirebaseConEmailPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                var msg=""
                if (it.isSuccessful){
                    Toast.makeText(this,getString(R.string.user_register_ok), Toast.LENGTH_LONG).show()
                    //guardarUsuarioEnFirebaseAutoId()
                    guardarFirebaseEmailID(name, phoneNumber, email)
                }else{
                    Log.w ("register","createUserWithEmail:failure",it.exception)

                    if (it.exception?.localizedMessage == "The email address is already in use by another account."){
                        msg = getString(R.string.user_already_exists)
                    }else if(it.exception?.localizedMessage == "The given password is invalid. [ Password should be at least 6 characters ]"){
                        msg = getString(R.string.password6digit)
                    }else if(it.exception?.localizedMessage == "The email address is badly formatted.") {
                        msg = getString(R.string.bad_write_Email)
                    }
                    Toast.makeText(this,msg, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun guardarUsuarioEnFirebaseAutoId() {
        val idF = auth.currentUser?.uid
        idF?.let{idF->
            val user = User(id=NULL, nombre=name, telefono=phoneNumber, correo=email, idF=idF )
            val db = Firebase.firestore
            db.collection("users").document(idF).set(user)
                .addOnSuccessListener{documentReference ->
                    Toast.makeText(this,"Usuario agregado a firebase correctamente", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener{e ->
                    Toast.makeText(this,"ERROR Usuario NO agregado a firebase DataBase", Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun guardarFirebaseEmailID(name: String, phoneNumber: String, email: String) {
        val idF = auth.currentUser?.uid
        idF?.let { idF ->
            val db = FirebaseFirestore.getInstance()
            val user = User(id=NULL, nombre=name, telefono=phoneNumber, correo=email, idF=idF)
            db.collection("users").document(email).set(user)
                .addOnSuccessListener {
                    Toast.makeText(this,"Usuario email agregado a firebase correctamente",Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this,"ERROR Usuario NO agregado a firebase DataBase",Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun guardarDeudorEnLocal(name: String,phoneNumber: String, email: String) {
        val idF = auth.currentUser?.uid
        idF?.let { idF ->
            val usuario = User(
                id = NULL,
                nombre = name,
                telefono = phoneNumber,
                correo = email,
                idF = idF
            )
            val userDAO: UserDAO = MiBaseDeDatosApp.databaseUser.UserDAO()
            userDAO.insertUser(usuario)

        }

    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("email",registerBinding.emailEditText.text.toString())
        intent.putExtra("password",registerBinding.passwordEditText.text.toString())
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)  //finish() //destruye el registro en la pila para no volver a esta actividad con el boton atras
        startActivity(intent)
    }

}


