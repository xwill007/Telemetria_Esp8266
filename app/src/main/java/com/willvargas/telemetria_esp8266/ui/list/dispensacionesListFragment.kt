package com.willvargas.telemetria_esp8266.ui.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue

import com.google.firebase.ktx.Firebase
import com.willvargas.telemetria_esp8266.data.server.DispensacionesServer
import com.willvargas.telemetria_esp8266.databinding.FragmentDispensacionesListBinding


class dispensacionesListFragment : Fragment() {

    //private lateinit var listViewModel: ListViewModel
    private var _listDispBinding: FragmentDispensacionesListBinding? = null
    private val listDispBinding get() = _listDispBinding!!

    private lateinit var dispAdapter: DispensacionesAdapter
    private lateinit var auth: FirebaseAuth

    private lateinit var idDisp: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _listDispBinding = FragmentDispensacionesListBinding.inflate(inflater,container,false)

        auth= Firebase.auth
        dispAdapter = DispensacionesAdapter(onItemClicked = {onItemClicked(it)}) //onItemClicked = {onItemClicked(it)}
        listDispBinding.dispRecyclerView.apply{
            layoutManager = LinearLayoutManager(this@dispensacionesListFragment.context)
            adapter = dispAdapter
            setHasFixedSize(false)
        }

        //cargarDeRoom()
        cargarDeFirebase()

        val root:View = listDispBinding.root
        return root
    }

    private fun onItemClicked(equipo: Double){
        //findNavController().navigate(ListFragmentDirections.actionNavListFragmentToDetailFragment(equipo=equipo))
    }

    private fun cargarDeFirebase() {
        //idDisp = Disp.idEquipo.toString()
        var listaDisp: MutableList<Double> = arrayListOf()
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference().child(idEquipo)
            .child("dispensaciones")
            .child("10_Octubre")
            .child("4")
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