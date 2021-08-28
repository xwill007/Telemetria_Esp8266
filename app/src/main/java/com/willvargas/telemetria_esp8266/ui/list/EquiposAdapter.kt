package com.willvargas.telemetria_esp8266.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.willvargas.telemetria_esp8266.R
import com.willvargas.telemetria_esp8266.data.local.entities.Equipo
import com.willvargas.telemetria_esp8266.databinding.CardViewEquiposItemBinding

class EquiposAdapter: RecyclerView.Adapter<EquiposAdapter.ViewHolder>(){

    private var listaEquipos: MutableList<Equipo> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_equipos_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listaEquipos[position])
    }

    override fun getItemCount(): Int {
        return listaEquipos.size
    }

    fun appendItems(newItems: MutableList<Equipo>){
        listaEquipos.clear()
        listaEquipos.addAll(newItems)
        notifyDataSetChanged()
    }

    class ViewHolder(view : View):RecyclerView.ViewHolder(view){
        private val binding = CardViewEquiposItemBinding.bind(view)
        fun bind(equipo:Equipo){
            with(binding) {
                idEquipoTextView.text = equipo.idEquipo
                contadorTextView.text = equipo.contadorBebidas.toString()
            }
        }
    }
}