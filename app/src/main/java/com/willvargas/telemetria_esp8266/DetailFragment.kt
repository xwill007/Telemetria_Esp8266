package com.willvargas.telemetria_esp8266

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.willvargas.telemetria_esp8266.data.server.EquiposServer
import com.willvargas.telemetria_esp8266.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {

    private lateinit var detailBinding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()

    companion object {
        fun newInstance() = DetailFragment()
    }

    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        detailBinding = FragmentDetailBinding.inflate(inflater,container,false)
        return detailBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        val equipo : EquiposServer = args.equipo
        Toast.makeText(requireContext(),equipo.idEquipo,Toast.LENGTH_LONG).show()
        with(detailBinding) {
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