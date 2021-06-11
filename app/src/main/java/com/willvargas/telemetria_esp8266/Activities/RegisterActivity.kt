package com.willvargas.telemetria_esp8266.Activities

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.willvargas.telemetria_esp8266.MiBaseDeDatosApp
import com.willvargas.telemetria_esp8266.R
import com.willvargas.telemetria_esp8266.data.local.dao.UserDAO
import com.willvargas.telemetria_esp8266.data.local.entities.User
import com.willvargas.telemetria_esp8266.databinding.ActivityRegisterBinding
import java.sql.Types


class RegisterActivity : AppCompatActivity() {

    private lateinit var registerBinding: ActivityRegisterBinding

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
                    //guardarDeudorEnLocal(name, phoneNumber,email, password)
                    autenticarConEmailPassword(email,password)
                    guardarFirebaseEmailID(name, phoneNumber, email)
                    goToLogin()
                } else {
                    registerBinding.repPasswordTextInputLayout.error = getString(R.string.pasword_error)
                }
            } else Toast.makeText(this, getString(R.string.data_Error), Toast.LENGTH_LONG).show()
        }



    }

    private fun autenticarConEmailPassword(email: String, password: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(){
                if (it.isSuccessful){
                    Toast.makeText(this,"Usuario autenticado correctamente", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this,"error de autenticacion", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun guardarDeudorEnLocal(name: String,phoneNumber: String, email: String, password: String) {
        val usuario = User(id= Types.NULL, nombre=name, telefono=phoneNumber, correo=email, clave=password)
        val userDAO : UserDAO = MiBaseDeDatosApp.databaseUser.UserDAO()
        userDAO.insertUser(usuario)
    }

    private fun guardarFirebaseEmailID(name: String, phoneNumber: String, email: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(email).set(
            hashMapOf(
                "name" to name,
                "phoneNumber" to phoneNumber,
                "email" to email
            )
        )
            .addOnSuccessListener {
                Toast.makeText(this,"Usuario agregado correctamente", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener{
                Toast.makeText(this,"ERROR Usuario NO agregado ", Toast.LENGTH_LONG).show()
            }
    }



    private fun goToLogin() {
        //val user =
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("email",registerBinding.emailEditText.text.toString())
        intent.putExtra("password",registerBinding.passwordEditText.text.toString())
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)  //finish() //destruye el registro en la pila para no volver a esta actividad con el boton atras
        startActivity(intent)
    }

}


