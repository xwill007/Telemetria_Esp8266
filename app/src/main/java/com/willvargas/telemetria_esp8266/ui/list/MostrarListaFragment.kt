package com.willvargas.telemetria_esp8266.ui.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.willvargas.telemetria_esp8266.R
import com.willvargas.telemetria_esp8266.data.server.EquiposServer
import com.willvargas.telemetria_esp8266.databinding.FragmentDetailBinding
import com.willvargas.telemetria_esp8266.databinding.FragmentMostrarListaBinding
import java.util.*


class MostrarListaFragment : Fragment() {
    private lateinit var mostrarListaBinding: FragmentMostrarListaBinding
    private lateinit var dispAdapter: DispensacionesAdapter
    private var dia = Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString()
    private var mes = (Calendar.getInstance().get(Calendar.MONTH)+1).toString()
/*
    private val args: MostrarListaFragmentArgs by navArgs()
    private var equipo : EquiposServer = args.equipo
    private var IDEquipo = equipo.idEquipo.toString()
*/
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mostrarListaBinding = FragmentMostrarListaBinding.inflate(inflater,container,false)
            //if (this.arguments!=null) {
                val IDEquipo= requireArguments()!!.getString("IDequipo").toString()
                mostrarListaBinding.textViewIDequipo.text = IDEquipo
            //Toast.makeText(requireContext(),IDEquipo, Toast.LENGTH_LONG).show()
            //}

        dispAdapter = DispensacionesAdapter(onItemClicked = {onItemClicked(it)}) //onItemClicked = {onItemClicked(it)}
        mostrarListaBinding.recyclerView.apply{
        layoutManager = LinearLayoutManager(this@MostrarListaFragment.context)
        adapter = dispAdapter
        setHasFixedSize(false)
        }

        mostrarListaBinding.calendarView.setOnDateChangeListener{calendarView,year,month,day ->
        dia = day.toString()
        mes = (month+1).toString()
        Toast.makeText(requireContext(),"fecha: $dia/$mes/ ",Toast.LENGTH_SHORT).show()
        CargarListaDeFirebaseRealTime()
    }

        return mostrarListaBinding.root
    }

    private fun CargarListaDeFirebaseRealTime() {
        val listaDisp: MutableList<String> = arrayListOf()
        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference.child("ESP01")
            .child("dispensaciones")
            .child(mes)
            .child(dia)
            .child("hora")
            //.get().addOnSuccessListener {result ->
            .get().addOnSuccessListener {result ->
                Log.d("Dato",result.toString())
                for (DataSnapShot in result.children){
                    Log.d("dato",DataSnapShot.toString())
                    DataSnapShot.getValue<String>()?.let{listaDisp.add(it)}
                }
                dispAdapter.appendItems(listaDisp)
            }
    }

    private fun onItemClicked(it: String) {
        //Toast.makeText(requireContext(),IDEquipo, Toast.LENGTH_LONG).show()
    }




}