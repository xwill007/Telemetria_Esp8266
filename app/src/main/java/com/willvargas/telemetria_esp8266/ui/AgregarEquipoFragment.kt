package com.willvargas.telemetria_esp8266.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.willvargas.telemetria_esp8266.Activities.userEmail
import com.willvargas.telemetria_esp8266.MiBaseDeDatosApp
import com.willvargas.telemetria_esp8266.R
import com.willvargas.telemetria_esp8266.data.local.dao.EquipoDAO
import com.willvargas.telemetria_esp8266.data.local.entities.Equipo
import com.willvargas.telemetria_esp8266.data.server.EquiposServer
import com.willvargas.telemetria_esp8266.databinding.FragmentAgregarEquipoBinding
import java.sql.Types.NULL

class AgregarEquipoFragment : Fragment() {

    private lateinit var agregarEquipoBinding: FragmentAgregarEquipoBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        agregarEquipoBinding = FragmentAgregarEquipoBinding.inflate(inflater,container,false)


        auth= Firebase.auth
        val idF = auth.currentUser?.uid
        val emailusuario = auth.currentUser?.email
        agregarEquipoBinding.textViewUsuario.setText(emailusuario).toString()

        agregarEquipoBinding.guardarEquipo.isEnabled = false

        agregarEquipoBinding.textViewCount.addTextChangedListener {
            agregarEquipoBinding.guardarEquipo.isEnabled = (agregarEquipoBinding.textViewCount.length() >= 1)and(agregarEquipoBinding.textViewId.length() >= 2)
        }

        guardarFirebaseEquipo()

        return agregarEquipoBinding.root
    }

    private fun clearview() {
        with(agregarEquipoBinding){
            EditTextContact.setText(" ")
            EditTextPhone.setText(" ")
            EditTextAddress.setText(" ")
            textViewId.setText(" ")
            textViewCount.setText(" ")
            textViewNote.setText(" ")
        }
    }

    private fun guardarFirebaseEquipo() {

        with(agregarEquipoBinding){
            agregarEquipoBinding.guardarEquipo.setOnClickListener {

                val nombreContacto = EditTextContact.text.toString()
                val telefonoContacto :String ? = EditTextPhone.text.toString()
                val direccion :String ? = EditTextAddress.text.toString()
                val idEquipo :String ? = textViewId.text.toString()
                val contadorBebidas :Long? = textViewCount.text.toString().toLong()
                val descripcion :String ? = textViewNote.text.toString()
                val emailUsuario = userEmail.toString()

                val equipo = Equipo(id=NULL, nombreContacto= nombreContacto, telefonoContacto= telefonoContacto,direccion= direccion,idEquipo= idEquipo, contadorBebidas= contadorBebidas, descripcion= descripcion, emailUsuario= emailUsuario)
                val equipoDAO : EquipoDAO = MiBaseDeDatosApp.databaseEquipos.EquipoDAO()
                equipoDAO.insertEquipo(equipo)

                val db = Firebase.firestore
                val document = db.collection("equipos").document()
                val id = document.id
                val equipoServer = EquiposServer(
                    id=id,
                    nombreContacto=nombreContacto,
                    telefonoContacto=telefonoContacto,
                    direccion=direccion,
                    idEquipo=idEquipo,
                    contadorBebidas=contadorBebidas,
                    descripcion=descripcion,
                    emailUsuario=emailUsuario
                )
                db.collection("users").document(auth.currentUser?.email.toString()).collection("equipos").document(id).set(equipoServer)
                    .addOnSuccessListener {
                        Toast.makeText(getContext(),getString(R.string.equipment_added_successfully), Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener{
                        Toast.makeText(getContext(),getString(R.string.equipment_not_added), Toast.LENGTH_LONG).show()
                    }


                clearview()
            }
        }
        //val db = FirebaseFirestore.getInstance()
        /*db.collection("users").document(auth.currentUser?.email.toString()).collection("equipos").document(equipo.idEquipo.toString()).set(
                hashMapOf(
                        "nombreContacto" to equipo.nombreContacto.toString(),
                        "telefonoContacto" to equipo.telefonoContacto.toString(),
                        "direccion" to equipo.direccion.toString(),
                        "cotadorBebidas" to equipo.contadorBebidas.toString(),
                        "descripcion" to equipo.descripcion.toString(),
                        "emailUsuario" to equipo.emailUsuario.toString(),
                )
        )

        */

    }

}