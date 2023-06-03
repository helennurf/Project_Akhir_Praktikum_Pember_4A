package com.example.projectakhir

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RiwayatPemesanan : AppCompatActivity() {

    // deklarasi repository
    private lateinit var transaksiActivityRepository: TransaksiActivityRepository

    // deklarasi recyclerview dan view lainnya
    private lateinit var rvRiwayatPemesanan: RecyclerView
    private lateinit var tvRiwayatPemesanan: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat_pemesanan)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initializeViews()
        setupRecyclerView()

        // deklarasi repository
        transaksiActivityRepository = TransaksiActivityRepository(application)

        // fungsi untuk menampilkan data dari database ke recyclerview
        transaksiActivityRepository.getAllArchiveTransaksiActivity().observe(this) { transaksiActivity ->
            if (transaksiActivity.isEmpty()) {
                tvRiwayatPemesanan.visibility = View.VISIBLE
                rvRiwayatPemesanan.visibility = View.INVISIBLE
            } else {
                tvRiwayatPemesanan.visibility = View.GONE
                val adapter = TransaksiActivityAdapter(transaksiActivity, this@RiwayatPemesanan).apply {
                    setHasStableIds(true)
                }
                rvRiwayatPemesanan.adapter = adapter
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        recreate()
        finish()
        return false
    }

    // fungsi untuk mengubah warna action bar dan judul action bar
    private fun initializeViews() {
        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(
            ColorDrawable(ContextCompat.getColor(this, R.color.merah))
        )
        actionBar?.title = "Arsip Aktivitas"
        rvRiwayatPemesanan = findViewById(R.id.rvRiwayatPemesanan)
        tvRiwayatPemesanan = findViewById(R.id.tvRiwayatPemesanan)
    }
    // fungsi untuk mengatur tampilan recyclerview dengan memberi garis pemisah antar item
    private fun setupRecyclerView() {
        rvRiwayatPemesanan.layoutManager = LinearLayoutManager(this)
        rvRiwayatPemesanan.addItemDecoration(
            DividerItemDecoration(
                rvRiwayatPemesanan.context,
                DividerItemDecoration.VERTICAL
            )
        )
    }
}