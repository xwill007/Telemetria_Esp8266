package com.willvargas.telemetria_esp8266.ui.list

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.dynamic.SupportFragmentWrapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.willvargas.telemetria_esp8266.Activities.ui.comunicador
import com.willvargas.telemetria_esp8266.data.server.EquiposServer
import com.willvargas.telemetria_esp8266.databinding.FragmentDispensacionesListBinding

import kotlinx.android.synthetic.main.fragment_dispensaciones_list.*
import java.lang.ClassCastException
import java.nio.file.Files.exists
import java.util.*


class DispensacionesListFragment : Fragment() {

    //private lateinit var listViewModel: ListViewModel
    private var _listDispBinding: FragmentDispensacionesListBinding? = null
    private val listDispBinding get() = _listDispBinding!!

    //private val args: DispensacionesListFragmentArgs by navArgs()
    //val equipo : EquiposServer = args.equipo
    //val idEquipo = equipo.idEquipo.toString()

    private lateinit var dispAdapter: DispensacionesAdapter
    private lateinit var auth: FirebaseAuth

    private var dia = Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString()
    private var mes = (Calendar.getInstance().get(Calendar.MONTH)+1).toString()

    //private lateinit var idEquipo: String
    var interfaz: comunicador? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        //val args = this.arguments
        //val inputData = args?.get("idEquipo")
        //listDispBinding.textViewID.text = inputData.toString()

        //val textView = getActivity().findViewById(R.id.textViewId)
        //idEquipo = textView.getText().toString()
        //cargarDeFirebase()
        //listDispBinding.textViewID.setText(arguments?.getString("IDequipo")).toString()

        val root:View = listDispBinding.root
        return root
    }

    private fun onItemClicked(disp:String){
        Toast.makeText(requireContext(),disp.toString(),Toast.LENGTH_SHORT).show()
        //findNavController().navigate(ListFragmentDirections.actionNavListFragmentToDetailFragment(equipo=equipo))
    }

    private fun cargarDeFirebase() {

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

}
