package com.willvargas.telemetria_esp8266.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.willvargas.telemetria_esp8266.R
import com.willvargas.telemetria_esp8266.data.server.EquiposServer
import com.willvargas.telemetria_esp8266.databinding.FragmentDetailBinding
import com.willvargas.telemetria_esp8266.databinding.FragmentMostrarListaBinding



class MostrarListaFragment : Fragment() {
    private lateinit var mostrarListaBinding: FragmentMostrarListaBinding

    //private val args: MostrarListaFragmentArgs by navArgs()
    //private var equipo : EquiposServer = args.equipo
    private var IDEquipo = "x"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mostrarListaBinding = FragmentMostrarListaBinding.inflate(inflater,container,false)
            //if (this.arguments!=null) {
                IDEquipo= requireArguments()!!.getString("IDequipo").toString()
                mostrarListaBinding.textViewIDequipo.text = IDEquipo
            //Toast.makeText(requireContext(),IDEquipo, Toast.LENGTH_LONG).show()
            //}
        return mostrarListaBinding.root
    }

}