package com.willvargas.telemetria_esp8266.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.google.firebase.firestore.FirebaseFirestore
import com.willvargas.telemetria_esp8266.Activities.userEmail
import com.willvargas.telemetria_esp8266.MiBaseDeDatosApp
import com.willvargas.telemetria_esp8266.data.local.dao.EquipoDAO
import com.willvargas.telemetria_esp8266.data.local.entities.Equipo
import com.willvargas.telemetria_esp8266.databinding.FragmentAgregarEquipoBinding
import java.sql.Types.NULL

class AgregarEquipoFragment : Fragment() {

    private lateinit var agregarEquipoBinding: FragmentAgregarEquipoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        agregarEquipoBinding = FragmentAgregarEquipoBinding.inflate(inflater,container,false)

        agregarEquipoBinding.textViewUsuario.setText(userEmail).toString()

        agregarEquipoBinding.guardarEquipo.isEnabled = false
        agregarEquipoBinding.textViewCount.addTextChangedListener {
            agregarEquipoBinding.guardarEquipo.isEnabled = (agregarEquipoBinding.textViewCount.length() >= 1)and(agregarEquipoBinding.textViewId.length() >= 2)
        }

        with(agregarEquipoBinding){

            guardarEquipo.setOnClickListener {

            val nombreContacto = EditTextContact.text.toString()
            val telefonoContacto :String ? = EditTextPhone.text.toString()
            val direccion :String ? = EditTextAddress.text.toString()
            val idEquipo :String ? = textViewId.text.toString()
            val contadorBebidas :Long? = textViewCount.text.toString().toLong()
            val descripcion :String ? = textViewNote.text.toString()
            val emailusuario = userEmail.toString()
            val equipo = Equipo(id=NULL, nombreContacto= nombreContacto, telefonoContacto= telefonoContacto,direccion= direccion,idEquipo= idEquipo, contadorBebidas= contadorBebidas, descripcion= descripcion, emailUsuario= emailusuario)
            val equipoDAO : EquipoDAO = MiBaseDeDatosApp.databaseEquipos.EquipoDAO()

            equipoDAO.insertEquipo(equipo)
            guardarFirebaseEquipo(equipo)
            clearview()
            }
        }

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

    private fun guardarFirebaseEquipo(equipo: Equipo) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(userEmail).collection("equipos").document(equipo.idEquipo.toString()).set(
                hashMapOf(
                        "nombreContacto" to equipo.nombreContacto.toString(),
                        "telefonoContacto" to equipo.telefonoContacto.toString(),
                        "direccion" to equipo.direccion.toString(),
                        "cotadorBebidas" to equipo.contadorBebidas.toString(),
                        "descripcion" to equipo.descripcion.toString(),
                        "emailUsuario" to equipo.emailUsuario.toString(),
                )
        )
                .addOnSuccessListener {
                    Toast.makeText(getContext(),"Equipo agregado correctamente", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener{
                    Toast.makeText(getContext(),"ERROR Equipo NO agregado ", Toast.LENGTH_LONG).show()
                }
    }

}