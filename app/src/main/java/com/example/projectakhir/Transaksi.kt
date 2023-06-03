package com.example.projectakhir

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Transaksi : AppCompatActivity() {

    private lateinit var transaksiActivityRepository: TransaksiActivityRepository

    // deklarasi recyclerview dan view lainnya
    private lateinit var rvTransaksi: RecyclerView
    private lateinit var tvEmptyActivity: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaksi)
        initializeViews()
        setupRecyclerView()

        // deklarasi repository
        transaksiActivityRepository = TransaksiActivityRepository(application)

        // fungsi untuk menampilkan data dari database ke recyclerview
        transaksiActivityRepository.getAllTransaksiActivity().observe(this) { TransaksiActivity ->
            if (TransaksiActivity.isEmpty()) {
                tvEmptyActivity.visibility = View.VISIBLE
            } else {
                tvEmptyActivity.visibility = View.GONE
                val adapter = TransaksiActivityAdapter(TransaksiActivity, this@Transaksi).apply {
                    setHasStableIds(true)
                }
                rvTransaksi.adapter = adapter
            }
        }

        // fungsi untuk menampilkan activity tambah ketika tombol tambah di klik
        val btnTambah = findViewById<View>(R.id.btnTambah)
        btnTambah.setOnClickListener {
            val intent = Intent(this, Pemesanan::class.java)
            startActivity(intent)
        }
    }

    // fungsi untuk menampilkan menu/ikon arsip di pojok kanan atas
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_arsip, menu)
        return true
    }

    // fungsi untuk menampilkan activity arsip ketika ikon arsip di klik
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_settings) {
            startActivity(Intent(this, RiwayatPemesanan::class.java))
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    // fungsi untuk mengubah warna action bar dan judul action bar
    private fun initializeViews() {
        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(
            ColorDrawable(ContextCompat.getColor(this, R.color.biru_tua))
        )
        actionBar?.title = "Daftar Aktivitas"
        rvTransaksi = findViewById(R.id.rvTransaksi)
        tvEmptyActivity = findViewById(R.id.tvEmptyActivity)
    }

    // fungsi untuk mengatur tampilan recyclerview dengan memberi garis pemisah antar item
    private fun setupRecyclerView() {
        rvTransaksi.layoutManager = LinearLayoutManager(this)
        rvTransaksi.addItemDecoration(
            DividerItemDecoration(
                rvTransaksi.context,
                DividerItemDecoration.VERTICAL
            )
        )
    }
}