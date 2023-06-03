import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.projectakhir.DetailMobil
import com.example.projectakhir.Mobil
import com.example.projectakhir.R


class ListMobil(private val list: ArrayList<Mobil>) : RecyclerView.Adapter<ListMobil.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.activity_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nama.text = list[position].nama
        holder.kapasitas.text = list[position].kapasitas
        holder.foto.setImageResource(list[position].foto)

        // Menambahkan button intent pada recycler view agar bisa di klik
        holder.itemView.findViewById<Button>(R.id.buttonLihat)?.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailMobil::class.java)
            Toast.makeText(context, list[position].nama, Toast.LENGTH_SHORT).show()
            intent.putExtra("MOBIL_NAMA", list[position].nama)
            intent.putExtra("MOBIL_KAPASITAS", list[position].kapasitas)
            intent.putExtra("MOBIL_FOTO", list[position].foto)
            intent.putExtra("POSITION", position)
            context.startActivity(intent)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nama: TextView = itemView.findViewById(R.id.tvNamaMobil)
        val kapasitas: TextView = itemView.findViewById(R.id.tvKapasitas)
        val foto: ImageView = itemView.findViewById(R.id.ivFoto)
    }

    fun updateList(newList: List<Mobil>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}
