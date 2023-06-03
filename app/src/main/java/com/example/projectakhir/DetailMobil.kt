package com.example.projectakhir

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
class DetailMobil : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_mobil)

        // Menerima data dari recycler view
        val nama = intent.getStringExtra("MOBIL_NAMA")
        val kapasitas = intent.getStringExtra("MOBIL_KAPASITAS")
        val foto = intent.getIntExtra("MOBIL_FOTO", 0)
        val warna = resources.getStringArray(R.array.mobil_warna)
        val deskripsi = resources.getStringArray(R.array.mobil_deskripsi)
        val position = intent.getIntExtra("POSITION", 0)

        // Membuat variabel sesuai id
        val tvNamaMobil = findViewById<TextView>(R.id.tvNama)
        val tvKapasitas = findViewById<TextView>(R.id.tvKapasitas)
        val ivFoto = findViewById<ImageView>(R.id.ivFoto)
        val tvWarna = findViewById<TextView>(R.id.tvWarna)
        val tvDeskripsi = findViewById<TextView>(R.id.tvDeskripsi)
        val buttonSewa = findViewById<Button>(R.id.button)

        tvNamaMobil.text = nama
        tvKapasitas.text = kapasitas
        ivFoto.setImageResource(foto)
        tvWarna.text = warna[position]
        tvDeskripsi.text = deskripsi[position]

        buttonSewa.setOnClickListener {
            val intent = Intent(this@DetailMobil, Pemesanan::class.java)
            startActivity(intent)
        }
    }

    // fungsi untuk menampilkan menu/ikon share di pojok kanan atas
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_share, menu)
        return true
    }

    // fungsi untuk menampilkan activity arsip ketika ikon share di klik
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> {
                shareImageAndText()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun shareImageAndText() {
        // Menerima data dari recycler view
        val nama = intent.getStringExtra("MOBIL_NAMA")
        val kapasitas = intent.getStringExtra("MOBIL_KAPASITAS")
        val foto = intent.getIntExtra("MOBIL_FOTO", 0)
        val warna = resources.getStringArray(R.array.mobil_warna)
        val deskripsi = resources.getStringArray(R.array.mobil_deskripsi)
        val position = intent.getIntExtra("POSITION", 0)

        // Mengambil gambar dari ImageView
        val ivFoto = findViewById<ImageView>(R.id.ivFoto)
        val bitmap = getBitmapFromImageView(ivFoto)

        // Membuat teks yang akan dibagikan
        val textToShare =
            "Nama: $nama\n\nKapasitas: $kapasitas\n\nWarna: ${warna[position]}\n\nDeskripsi:\n${deskripsi[position]}"

        if (bitmap != null) {
            // Membuat intent untuk berbagi
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_STREAM, getUriFromBitmap(bitmap))
            intent.putExtra(Intent.EXTRA_TEXT, textToShare)
            // Mengatur flag untuk memberikan akses pada aplikasi lain untuk mengakses file
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            // Memulai aktivitas berbagi
            startActivity(Intent.createChooser(intent, "Bagikan gambar dan teks"))
        }
    }

    private fun getBitmapFromImageView(imageView: ImageView): Bitmap? {
        val drawable = imageView.drawable
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        return null
    }

    private fun getUriFromBitmap(bitmap: Bitmap): Uri {
        val cachePath = File(cacheDir, "images")
        cachePath.mkdirs()
        val file = File(cachePath, "image.jpg")
        val fileOutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
        fileOutputStream.close()
        return FileProvider.getUriForFile(
            this,
            "com.example.projectakhir.fileprovider",
            file
        )
    }
}