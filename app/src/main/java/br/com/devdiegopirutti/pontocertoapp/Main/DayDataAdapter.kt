package br.com.devdiegopirutti.pontocertoapp.Main

import android.text.format.DateFormat.format
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.devdiegopirutti.pontocertoapp.Model.Ponto
import br.com.devdiegopirutti.pontocertoapp.R
import java.util.*
import kotlin.collections.ArrayList

class DayDataAdapter : RecyclerView.Adapter<DayDataAdapter.DataViewHolder>() {

    val ponto = ArrayList<Ponto>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(LayoutInflater
            .from(parent.context).inflate(R.layout.recycler_day_register, parent, false))

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(ponto[position])
    }

    override fun getItemCount() = ponto.size

    fun clear() {
        ponto.clear()
        notifyDataSetChanged()
    }

    fun addAllRegisters(list: List<Ponto>) {
        ponto.addAll(list)
        notifyDataSetChanged()
    }

    class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var dataView: TextView? = view.findViewById(R.id.horario)
        var registerView: TextView? = view.findViewById(R.id.txt_tipo_de_ponto)

        fun bind(itemPonto: Ponto) {
            dataView?.text = itemPonto.data?.let { timeStampConverter(it) }
            itemPonto.entrada?.let {
                if (it) {
                    registerView?.text = "entrada"
                }
            }
        }

        private fun timeStampConverter(long: Long): String {
            return format("hh:mm:ss", Date(long)).toString()
        }
    }
}