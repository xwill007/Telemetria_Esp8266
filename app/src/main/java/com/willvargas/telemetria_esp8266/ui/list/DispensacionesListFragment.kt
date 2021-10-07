package com.willvargas.telemetria_esp8266.ui.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.willvargas.telemetria_esp8266.databinding.FragmentDispensacionesListBinding

import kotlinx.android.synthetic.main.fragment_dispensaciones_list.*
import java.util.*


class DispensacionesListFragment : Fragment() {

    //private lateinit var listViewModel: ListViewModel
    private var _listDispBinding: FragmentDispensacionesListBinding? = null
    private val listDispBinding get() = _listDispBinding!!

    private lateinit var dispAdapter: DispensacionesAdapter
    private lateinit var auth: FirebaseAuth

    private var dia = Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString()
    private var mes = (Calendar.getInstance().get(Calendar.MONTH)+1).toString()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _listDispBinding = FragmentDispensacionesListBinding.inflate(inflater,container,false)

        auth= Firebase.auth
        dispAdapter = DispensacionesAdapter(onItemClicked = {onItemClicked(it)}) //onItemClicked = {onItemClicked(it)}
        listDispBinding.dispRecyclerView.apply{
            layoutManager = LinearLayoutManager(this@DispensacionesListFragment.context)
            adapter = dispAdapter
            setHasFixedSize(false)
        }

        listDispBinding.calendarView.setOnDateChangeListener{calendarView,year,month,day ->
            dia = day.toString()
            mes = (month+1).toString()
            Toast.makeText(requireContext(),"fecha: $dia/$mes/ ",Toast.LENGTH_SHORT).show()
            cargarDeFirebase()
        }
        cargarDeFirebase()
        val root:View = listDispBinding.root
        return root
    }

    private fun onItemClicked(disp:Double){
        Toast.makeText(requireContext(),disp.toString(),Toast.LENGTH_SHORT).show()
        //findNavController().navigate(ListFragmentDirections.actionNavListFragmentToDetailFragment(equipo=equipo))
    }

    private fun cargarDeFirebase() {
        val listaDisp: MutableList<Double> = arrayListOf()
        val database = FirebaseDatabase.getInstance()
        database.getReference().child(idEquipo)
            .child("dispensaciones")
            .child(mes)
            .child(dia)
            .child("tAgua")
            .get().addOnSuccessListener {result ->

                Log.d("Dato",result.toString())

                for (document in result.children){
                    Log.d("Dato",document.toString())
                    document.getValue<Double>()?.let { listaDisp.add(it) }
                }
                dispAdapter.appendItems(listaDisp)
            }

    }

}
