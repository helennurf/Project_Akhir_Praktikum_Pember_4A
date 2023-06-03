package com.example.projectakhir

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.properties.Delegates

class DetailRiwayatPemesanan : AppCompatActivity() {

    private lateinit var mTransaksiActivityRepository: TransaksiActivityRepository
    private var getId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_riwayat_pemesanan)
        mTransaksiActivityRepository = TransaksiActivityRepository(application)
        getId = intent.getIntExtra(Pemesanan.EXTRA_ID, 0)
        setActionBarProperties(R.color.purple_700, "Detail Riwayat Pemesanan")
        fillActivityData()
    }

    override fun onSupportNavigateUp(): Boolean {
        recreate();
        finish()
        return true
    }

    // fungsi untuk menampilkan menu/ikon hapus di pojok kanan atas
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_hapus, menu)
        return true
    }

    // fungsi untuk menghapus data dari database
    private fun deleteDataFromDatabase(id: Int) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                mTransaksiActivityRepository.deletetransaksiActivityById(id)
            }
            showToast("Aktivitas berhasil dihapus")
            navigateToMainActivity()
        }
    }

    // fungsi untuk memilih menu hapus arsip
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_delete) {
            deleteDataFromDatabase(getId)
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    // fungsi untuk menampilkan judul dan warna pada action bar
    private fun setActionBarProperties(colorId: Int, title: String) {
        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(
            ColorDrawable(ContextCompat.getColor(this, colorId))
        )
        actionBar?.title = title
    }

    // fungsi untuk memasukkan data ke dalam view berdasarkan data yang dikirimkan dari adapter
    private fun fillActivityData() {
        val tampilID = findViewById<TextView>(R.id.tvNilaiID)
        val tampilNama = findViewById<TextView>(R.id.tvNilaiNama)
        val tampilJenis = findViewById<TextView>(R.id.tvNilaiJenis)
        val tampilLamaSewa = findViewById<TextView>(R.id.tvNilaiLamaSewa)
        val tampilMulai = findViewById<TextView>(R.id.tvNilaiMulai)
        val tampilSelesai = findViewById<TextView>(R.id.tvNilaiSelesai)
        val tampilTotal = findViewById<TextView>(R.id.tvNilaiTotal)

        tampilID.setText(intent.getIntExtra(Pemesanan.EXTRA_ID, 0).toString())
        tampilNama.setText(intent.getStringExtra(Pemesanan.EXTRA_NAMA))
        tampilJenis.setText(intent.getStringExtra(Pemesanan.EXTRA_JENIS))
        tampilLamaSewa.setText(intent.getStringExtra(Pemesanan.EXTRA_LAMA_SEWA))
        tampilMulai.setText(intent.getStringExtra(Pemesanan.EXTRA_MULAI))
        tampilSelesai.setText(intent.getStringExtra(Pemesanan.EXTRA_SELESAI))
        tampilTotal.setText(intent.getStringExtra(Pemesanan.EXTRA_TOTAL))
    }

    // fungsi untuk menampilkan toast
    private fun showToast(message: String) {
        Toast.makeText(this@DetailRiwayatPemesanan, message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToMainActivity() {
        finish()
    }
}