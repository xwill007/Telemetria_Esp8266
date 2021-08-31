package com.willvargas.telemetria_esp8266.ui.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.willvargas.telemetria_esp8266.MiBaseDeDatosApp
import com.willvargas.telemetria_esp8266.data.local.dao.EquipoDAO
import com.willvargas.telemetria_esp8266.data.local.entities.Equipo
import com.willvargas.telemetria_esp8266.data.server.EquiposServer
import com.willvargas.telemetria_esp8266.databinding.FragmentListBinding


class ListFragment : Fragment() {

    private lateinit var listViewModel: ListViewModel
    private var _listBinding: FragmentListBinding? = null
    private val listBinding get() = _listBinding!!

    private lateinit var equiposAdapter: EquiposAdapter
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        _listBinding = FragmentListBinding.inflate(inflater,container,false)

        auth= Firebase.auth
        equiposAdapter = EquiposAdapter(onItemClicked = {onDebtorItemClicked(it)})
        listBinding.equiposRecyclerView.apply{
            layoutManager = LinearLayoutManager(this@ListFragment.context)
            adapter = equiposAdapter
            setHasFixedSize(false)
        }

        //cargarDeRoom()
        cargarDeFirebase()

        val root:View = listBinding.root
        return root


    }

    private fun onDebtorItemClicked(equipo: EquiposServer){
        findNavController().navigate(ListFragmentDirections.actionNavigationListFragmentToDetailFragment(equipo))
    }

    private fun cargarDeFirebase() {
        val db = Firebase.firestore
        db.collection("users").document(auth.currentUser?.email.toString()).collection("equipos").get().addOnSuccessListener{result->
            var listaEquipos: MutableList<EquiposServer> = arrayListOf()
            for (document in result){
                Log.d("nombre",document.data.toString())
                listaEquipos.add(document.toObject<EquiposServer>())
            }
            equiposAdapter.appendItems(listaEquipos)
        }
    }

    private fun cargarDeRoom(){
        val equipoDAO : EquipoDAO = MiBaseDeDatosApp.databaseEquipos.EquipoDAO()
        val listaEquipos : MutableList<Equipo> = equipoDAO.getEquipos()
       // equiposAdapter.appendItems(listaEquipos)
    }

}