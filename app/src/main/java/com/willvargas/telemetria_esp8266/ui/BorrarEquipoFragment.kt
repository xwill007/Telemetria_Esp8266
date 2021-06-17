package com.willvargas.telemetria_esp8266.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.willvargas.telemetria_esp8266.MiBaseDeDatosApp
import com.willvargas.telemetria_esp8266.R
import com.willvargas.telemetria_esp8266.data.local.dao.EquipoDAO
import com.willvargas.telemetria_esp8266.data.local.entities.Equipo
import com.willvargas.telemetria_esp8266.databinding.FragmentBorrarEquipoBinding


class BorrarEquipoFragment : Fragment() {

   private lateinit var borrarEquipoBinding : FragmentBorrarEquipoBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        borrarEquipoBinding = FragmentBorrarEquipoBinding.inflate(inflater,container,false)

        borrarEquipoBinding.eliminarButton.setOnClickListener {
            val equipoID = borrarEquipoBinding.nombreIDBorrarEditText.text.toString()
            borrarEquipo(equipoID)
        }
        return borrarEquipoBinding.root
    }

    private fun borrarEquipo(equipoID: String) {
        val equipoDAO: EquipoDAO = MiBaseDeDatosApp.databaseEquipos.EquipoDAO()
        val equipo: Equipo = equipoDAO.searchEquipo(equipoID)

        if(equipo != null){
            val alertDialog: AlertDialog? = activity?.let{
                val builder = AlertDialog.Builder(it)
                builder.apply{
                    setMessage(getString(R.string.Desea_eliminar_a) + equipo.idEquipo  + "?")
                    setPositiveButton(getString(R.string.aceptar)){ dialog, id ->
                        equipoDAO.deleteEquipo(equipo)
                        borrarEquipoBinding.nombreIDBorrarEditText.setText("")
                        Toast.makeText(context,getString(R.string.Equipo_Eliminado), Toast.LENGTH_LONG).show()
                    }
                    setNegativeButton(getString(R.string.cancelar)){ dialog, id ->}
                }
                builder.create()
            }
            alertDialog?.show()

        }else{
            Toast.makeText(context,getString(R.string.No_Existe), Toast.LENGTH_LONG).show()
        }

    }

}