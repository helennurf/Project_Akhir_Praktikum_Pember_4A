package com.example.projectakhir

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectakhir.Pemesanan.Companion.EXTRA_ID
import com.example.projectakhir.Pemesanan.Companion.EXTRA_JENIS
import com.example.projectakhir.Pemesanan.Companion.EXTRA_LAMA_SEWA
import com.example.projectakhir.Pemesanan.Companion.EXTRA_MULAI
import com.example.projectakhir.Pemesanan.Companion.EXTRA_NAMA
import com.example.projectakhir.Pemesanan.Companion.EXTRA_SELESAI
import com.example.projectakhir.Pemesanan.Companion.EXTRA_STATUS
import com.example.projectakhir.Pemesanan.Companion.EXTRA_TOTAL
import org.w3c.dom.Text

class TransaksiActivityAdapter (
    private var list: List<TransaksiActivityEntity>,
    private val context: Context
    ) :
    RecyclerView.Adapter<TransaksiActivityAdapter.ViewHolder>() {
        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nama: TextView = itemView.findViewById(R.id.tvNilaiNama)
            val jenis: TextView = itemView.findViewById(R.id.tvNilaiJenis)
            val lama: TextView = itemView.findViewById(R.id.tvNilaiLamaSewa)
            val mulai: TextView = itemView.findViewById(R.id.tvNilaiMulai)
            val selesai: TextView = itemView.findViewById(R.id.tvNilaiSelesai)
            val total: TextView = itemView.findViewById(R.id.tvNilaiTotal)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.activity_card_transaksi, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int = list.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.nama.text = list[position].nama
            holder.jenis.text = list[position].jenisMobil
            holder.lama.text = list[position].lamaSewa
            holder.mulai.text = list[position].mulaiSewa
            holder.selesai.text = list[position].selesaiSewa
            holder.total.text = list[position].total

            holder.itemView.setOnClickListener {
                if (list[position].isDone) {
                    // Jika isDone = true, tidak membuka AddEditActivity
                    val intent = Intent(context, DetailRiwayatPemesanan::class.java)
                    intent.putExtra(EXTRA_ID, list[position].id)
                    intent.putExtra(EXTRA_NAMA, list[position].nama)
                    intent.putExtra(EXTRA_JENIS, list[position].jenisMobil)
                    intent.putExtra(EXTRA_LAMA_SEWA, list[position].lamaSewa)
                    intent.putExtra(EXTRA_MULAI, list[position].mulaiSewa)
                    intent.putExtra(EXTRA_SELESAI, list[position].selesaiSewa)
                    intent.putExtra(EXTRA_TOTAL, list[position].total)
                    intent.putExtra(EXTRA_STATUS, list[position].isDone)
                    context.startActivity(intent)
                } else {
                    val intent = Intent(context, Pemesanan::class.java)
                    intent.putExtra(EXTRA_ID, list[position].id)
                    intent.putExtra(EXTRA_NAMA, list[position].nama)
                    intent.putExtra(EXTRA_JENIS, list[position].jenisMobil)
                    intent.putExtra(EXTRA_LAMA_SEWA, list[position].lamaSewa)
                    intent.putExtra(EXTRA_MULAI, list[position].mulaiSewa)
                    intent.putExtra(EXTRA_SELESAI, list[position].selesaiSewa)
                    intent.putExtra(EXTRA_TOTAL, list[position].total)
                    intent.putExtra(EXTRA_STATUS, list[position].isDone)
                    context.startActivity(intent)
                }
            }
        }
    }