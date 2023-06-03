package com.example.projectakhir

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListTransaksi (private val list: ArrayList<Payments>) : RecyclerView.Adapter<ListTransaksi.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val judul: TextView = itemView.findViewById(R.id.tvJudul)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.activity_card_sewa, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.judul.text = list[position].judul

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, Transaksi::class.java)
            holder.itemView.context.startActivity(intent)
        }

    }
}