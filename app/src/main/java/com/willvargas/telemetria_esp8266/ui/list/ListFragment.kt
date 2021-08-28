package com.willvargas.telemetria_esp8266.ui.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.willvargas.telemetria_esp8266.MiBaseDeDatosApp
import com.willvargas.telemetria_esp8266.data.local.dao.EquipoDAO
import com.willvargas.telemetria_esp8266.data.local.entities.Equipo
import com.willvargas.telemetria_esp8266.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private lateinit var listViewModel: ListViewModel
    private var _listBinding: FragmentListBinding? = null
    private val listBinding get() = _listBinding!!

    private lateinit var equiposAdapter: EquiposAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        _listBinding = FragmentListBinding.inflate(inflater,container,false)

        equiposAdapter = EquiposAdapter()
        listBinding.equiposRecyclerView.apply{
            layoutManager = LinearLayoutManager(this@ListFragment.context)
            adapter = equiposAdapter
            setHasFixedSize(false)
        }
        val equipoDAO : EquipoDAO = MiBaseDeDatosApp.databaseEquipos.EquipoDAO()
        val listaEquipos : MutableList<Equipo> = equipoDAO.getEquipos()
        equiposAdapter.appendItems(listaEquipos)

        val root:View = listBinding.root
        return root
    }




}