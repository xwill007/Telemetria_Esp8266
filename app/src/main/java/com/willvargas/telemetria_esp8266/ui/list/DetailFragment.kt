package com.willvargas.telemetria_esp8266.ui.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.willvargas.telemetria_esp8266.data.server.EquiposServer
import com.willvargas.telemetria_esp8266.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {

    private lateinit var detailBinding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var idEquipo: String

    companion object {
        fun newInstance() = DetailFragment()
    }

    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        detailBinding = FragmentDetailBinding.inflate(inflater,container,false)

        obtenerContadores(idEquipo)

        return detailBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        val equipo : EquiposServer = args.equipo
        idEquipo = equipo.idEquipo.toString()
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
            textViewNote.setText(equipo.descripcion)
        }

    }

    fun obtenerContadores(idEquipo: String){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference().child(idEquipo).child("contador")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()){
                    var value = dataSnapshot.getValue()
                    Log.d("contador", "Value is: $value")
                    detailBinding.textViewDay.setText(value.toString())

                }else Log.d("contador", "no existe.")

            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("contador", "Failed to read value.", error.toException())
            }

        })

    }

}