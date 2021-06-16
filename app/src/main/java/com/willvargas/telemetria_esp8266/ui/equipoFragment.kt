package com.willvargas.telemetria_esp8266.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.room.ColumnInfo
import com.google.android.material.navigation.NavigationView
import com.willvargas.telemetria_esp8266.MiBaseDeDatosApp
import com.willvargas.telemetria_esp8266.R
import com.willvargas.telemetria_esp8266.data.local.dao.EquipoDAO
import com.willvargas.telemetria_esp8266.data.local.dao.UserDAO
import com.willvargas.telemetria_esp8266.data.local.entities.Equipo
import com.willvargas.telemetria_esp8266.data.local.entities.User
import com.willvargas.telemetria_esp8266.databinding.FragmentEquipoBinding


class equipoFragment : Fragment() {

    private lateinit var equipoBinding: FragmentEquipoBinding

    val idNombre = "punto niquia"  //obtener NombreID recibido de mainActivity al seleccionar item con un intent.

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        equipoBinding = FragmentEquipoBinding.inflate(inflater,container,false)
        //buscarEquipo(idNombre)
        return equipoBinding.root
    }

    /*private fun buscarEquipo(idNombre: String) {
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
*/
}