package com.willvargas.telemetria_esp8266.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        with(agregarEquipoBinding){
            guardarEquipo.setOnClickListener {
            val nombreContacto = EditTextContact.text.toString()
            val telefonoContacto :String ? = EditTextPhone.text.toString()
            val direccion :String ? = EditTextAddress.text.toString()
            val idEquipo :String ? = textViewId.text.toString()
            val contadorBebidas :Long? = textViewCount.text.toString().toLong()
            val descripcion :String ? = textViewNote.text.toString()

            val equipo = Equipo(id=NULL, nombreContacto, telefonoContacto, direccion, idEquipo, contadorBebidas, descripcion)
            val equipoDAO : EquipoDAO = MiBaseDeDatosApp.databaseEquipos.EquipoDAO()
            equipoDAO.insertEquipo(equipo)
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

}