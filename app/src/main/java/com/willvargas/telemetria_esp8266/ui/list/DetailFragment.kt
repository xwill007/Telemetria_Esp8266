package com.willvargas.telemetria_esp8266.ui.list

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.willvargas.telemetria_esp8266.Activities.ui.comunicador
import com.willvargas.telemetria_esp8266.R
import com.willvargas.telemetria_esp8266.data.server.EquiposServer
import com.willvargas.telemetria_esp8266.databinding.FragmentDetailBinding
import kotlinx.android.synthetic.main.fragment_detail.*
import java.util.*


//lateinit var idEquipo: String

class DetailFragment : Fragment() {

    private lateinit var detailBinding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    private var ayer = (Calendar.getInstance().get(Calendar.DAY_OF_MONTH)-1).toString()
    private var dia = Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString()
    private var mes = (Calendar.getInstance().get(Calendar.MONTH)+1).toString()
    private var mesAnt = (Calendar.getInstance().get(Calendar.MONTH)).toString()
    private lateinit var equipo : EquiposServer
    private lateinit var IDequipo: String
    private lateinit var unidadesHoy: String

    var interfaz: comunicador? = null
    //private lateinit var textview: TextView

    companion object {
        fun newInstance() = DetailFragment()
    }

    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        detailBinding = FragmentDetailBinding.inflate(inflater,container,false)

        //textview = getActivity()?.findViewById(R.id.textViewId)

        detailBinding.buttonListaDispensaciones.setOnClickListener {
            //interfaz?.enviarDatos(IDequipo)
            findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToDispensacionesListFragment(equipo = equipo))
            //findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToMostrarListaFragment())
            //Toast.makeText(requireContext(),IDequipo,Toast.LENGTH_LONG).show()
            //interfaz?.enviarDatos(IDequipo.toString())
        }

        obtenerContadores()

        return detailBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        equipo = args.equipo
        IDequipo = equipo.idEquipo.toString()

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
            //textview.setText(equipo.idEquipo).toString()
        }
        //enviarDatoEntreFragments(equipo.idEquipo)
    }

    fun obtenerContadores(){
        equipo = args.equipo
        IDequipo = equipo.idEquipo.toString()
        val database = FirebaseDatabase.getInstance()
        val myRefTotal = database.getReference().child(IDequipo).child("contador")

        //val myRefDia = database.getReference().child(IDequipo).child("dispensaciones").child("mes").child("dia").child("totalDia")
        val myRefAyer = database.getReference().child(IDequipo).child("dispensaciones").child(mes).child(ayer).child("totalDia")
        val myRefDia = database.getReference().child(IDequipo).child("dispensaciones").child(mes).child(dia).child("totalDia")
        val myRefMes = database.getReference().child(IDequipo).child("dispensaciones").child(mes).child("totalMes")
        val myRefMesAnt = database.getReference().child(IDequipo).child("dispensaciones").child(mesAnt).child("totalMes")
        obtenerImagen(myRefTotal,detailBinding.imagenEquipo)
        obtenerUnidades(myRefTotal,detailBinding.textViewCount)
        obtenerUnidades(myRefMes,detailBinding.textViewMonth)
        obtenerUnidades(myRefDia,detailBinding.textViewDay)
        obtenerUnidades(myRefAyer,detailBinding.textViewLastDay)
        obtenerUnidades(myRefMesAnt,detailBinding.textViewLastMonth)
    }

    private fun obtenerUnidades(myRef: DatabaseReference, textViewX: TextView?) {
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()){
                    val value = dataSnapshot.getValue()
                    textViewX?.setText(value.toString())
                }else
                    Log.d("contador", "no existe.")
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("contador", "Failed to read value.", error.toException())
            }
        })
    }

    private fun obtenerImagen(myRef: DatabaseReference, imagenEquipo: ImageView) {
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()){
                    if (equipo.urlPicture != null){ Picasso.get().load(equipo.urlPicture).into(imagenEquipo)}
                }else Log.d("Imagen", "no existe.")
            }override fun onCancelled(error: DatabaseError) {
                Log.w("Imagen", "Failed to read value.", error.toException())
            }
        })
    }



    //val manager: FragmentManager = getSupportFragmentManager()
    //val transaction: FragmentTransaction = manager.beginTransaction()
    //val detailFragment =DetailFragment()
    //val args = Bundle()
    //detailFragment.arguments = args
    //transaction.add(R.id.textViewId,detailFragment).commit()

/*
 fun enviarDatoEntreFragments(idEquipo: String?) {

     val datosAEnviar = Bundle()
     datosAEnviar.putString("idEquipo",idEquipo);
     val transaction: FragmentTransaction? = fragmentManager?.beginTransaction()
     val fragment = DispensacionesListFragment()
     val args = Bundle()
     fragment.arguments = args
     if (transaction != null) {
         transaction.add(R.id.container,fragment).commit()
     }else Log.d("bundle", "bundle no existe.")


     //val datosAEnviar = Bundle()
     //datosAEnviar.putString("idEquipo", idEquipo);
     //val fragment = DispensacionesListFragment()
     //fragment.setArguments(datosAEnviar);
     //fragment.arguments = datosAEnviar
     //fragmentManager?.beginTransaction()?.replace(R.id.container,fragment)?.commit()
     //val fragmentManager: FragmentManager = getActivity()?.getSupportFragmentManager() ?:
     //val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
     //fragmentTransaction.replace(R.id.fragmentContainerListDisp, DispensacionesListFragment);
     //fragmentTransaction.addToBackStack(null);
     //fragmentTransaction.commit();
 }
*/


    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            interfaz = context as comunicador
        }catch (e: ClassCastException){}
    }

}