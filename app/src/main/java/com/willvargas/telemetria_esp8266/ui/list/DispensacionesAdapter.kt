package com.willvargas.telemetria_esp8266.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.willvargas.telemetria_esp8266.R
import com.willvargas.telemetria_esp8266.databinding.CardViewDispensacionesBinding


class DispensacionesAdapter (
    private val onItemClicked: (String) -> Unit,
): RecyclerView.Adapter<DispensacionesAdapter.ViewHolder>() {

    private var listaDisp: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DispensacionesAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_dispensaciones, parent, false)
        return DispensacionesAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: DispensacionesAdapter.ViewHolder, position: Int) {
        holder.bind(listaDisp[position])
        holder.itemView.setOnClickListener { onItemClicked(listaDisp[position]) }
    }

    override fun getItemCount(): Int {
        return listaDisp.size
    }

    fun appendItems(newItems: MutableList<String>) {
        listaDisp.clear()
        listaDisp.addAll(newItems)
        notifyDataSetChanged()
    }

    class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        private val binding = CardViewDispensacionesBinding.bind(view)
        fun bind(dispensacion:String){
            with(binding) {
                textViewDato.text = dispensacion.toString()
                //if (disp.urlPicture != null){
                //    Picasso.get().load(equipo.urlPicture).into(pictureImageView);
                //}
                //textViewTitulo.text = disp.id.toString()
            }
        }
    }
}
