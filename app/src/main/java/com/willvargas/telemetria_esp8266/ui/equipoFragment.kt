package com.willvargas.telemetria_esp8266.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.willvargas.telemetria_esp8266.Activities.userEmail
import com.willvargas.telemetria_esp8266.ESP01
import com.willvargas.telemetria_esp8266.MiBaseDeDatosApp
import com.willvargas.telemetria_esp8266.data.local.dao.EquipoDAO
import com.willvargas.telemetria_esp8266.databinding.FragmentEquipoBinding


class equipoFragment : Fragment() {

    private lateinit var equipoBinding: FragmentEquipoBinding

    val idNombre = "punto niquia"  //obtener NombreID recibido de mainActivity al seleccionar item con un intent.

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        equipoBinding = FragmentEquipoBinding.inflate(inflater, container, false)
        //buscarEquipo(idNombre)



        descargarDatosFirebase(userEmail)
        return equipoBinding.root
    }

    private fun descargarDatosFirebase(email: String) {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference().child("ESP01").child("contador")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()){
                    var value = dataSnapshot.getValue()
                    Log.d(TAG, "Value is: $value")
                    equipoBinding.textViewCount.setText(value.toString())

                }else Log.d(TAG, "no existe.")

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }


    private fun buscarEquipo(idNombre: String) {

        //descargar informacion de firebase y actualizar en base de datos local

        val equipoDAO: EquipoDAO = MiBaseDeDatosApp.databaseEquipos.EquipoDAO()
        val equipo = equipoDAO.searchEquipo(idNombre)

        with(equipoBinding) {
            textViewId.setText(equipo.idEquipo)
            textViewContact.setText(equipo.nombreContacto)
            textViewPhone.setText(equipo.telefonoContacto)
            textViewAddress.setText(equipo.direccion)
            //textViewLastMonth.setText(equipo.
            //textViewMonth.setText(equipo.
            //textViewLastDay.setText(equipo.
            //textViewDay.setText(equipo.
            textViewCount.setText(equipo.contadorBebidas.toString())
            textViewNote.setText(equipo.descripcion)

        }
    }

}