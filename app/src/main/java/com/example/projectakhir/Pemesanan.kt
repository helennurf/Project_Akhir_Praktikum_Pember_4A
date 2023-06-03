package com.example.projectakhir

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageInstaller
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.properties.Delegates

class Pemesanan : AppCompatActivity() {
    // deklarasi repository
    private lateinit var mTransaksiActivityRepository: TransaksiActivityRepository

    // deklarasi id untuk menentukan apakah data akan diupdate atau ditambahkan
    private var getId by Delegates.notNull<Int>()
    private var status by Delegates.notNull<Boolean>()

    // deklarasi variabel beserta tipe view yang akan digunakan
    private var hs_mobil: Int = 0
    private var waktu_sewa: Int = 0
    private var ttl_hrg: Int = 0

    // deklarasi variabel beserta tipe view yang akan digunakan
    private lateinit var namaPenyewa: EditText
    private lateinit var jenisMobil: Spinner
    private lateinit var lamaSewa: EditText
    private lateinit var mulaiSewa: EditText
    private lateinit var selesaiSewa: EditText
    private lateinit var totalHarga: TextView

    private val p_list_mobil = arrayOf(
        "Honda Brio 2021",
        "Daihatsu Ayla 2023",
        "Kijang Innova 2021",
        "Toyota Yaris 2021",
        "Toyota Alpard 2022",
        "Honda BRV 2023"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pemesanan)
        mTransaksiActivityRepository = TransaksiActivityRepository(application)

        getId = intent.getIntExtra(EXTRA_ID, 0)
        status = intent.getBooleanExtra(EXTRA_STATUS, false)

        initializeViews()

        if (getId != 0) {
            if(status != true){
                setActionBarProperties(R.color.teal_200, "Edit Aktivitas")
                fillActivityData()
            }else{
                val intent = Intent(this@Pemesanan, DetailRiwayatPemesanan::class.java)
                startActivity(intent)
                finish()
            }
        } else {
            setActionBarProperties(R.color.teal_700, "Tambah Aktivitas")
        }

        val namaPenyewa: EditText = findViewById(R.id.nama_penyewa)
        val jenisMobil: Spinner = findViewById(R.id.list_mobil)
        val lamaSewa: EditText = findViewById(R.id.lama_sewa)
        val mulaiSewa: EditText = findViewById(R.id.mulai_sewa)
        val selesaiSewa: EditText = findViewById(R.id.selesai_sewa)
        val hargaSewa: TextView = findViewById(R.id.harga_sewa)
        val totalHarga: TextView = findViewById(R.id.total_harga)
        val cekHarga : Button = findViewById(R.id.cek_harga)
        val sewaButton: Button = findViewById(R.id.sewa)

        val ad_mbl = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, p_list_mobil)
        jenisMobil.adapter = ad_mbl

        mulaiSewa.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val datePickerDialog =
                DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                    val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                        val dateTime =
                            "$selectedYear-${selectedMonth + 1}-$selectedDay $selectedHour:$selectedMinute"
                        mulaiSewa.setText(dateTime)
                    }, hour, minute, true)
                    timePickerDialog.show()
                }, year, month, day)
            datePickerDialog.show()
        }

        selesaiSewa.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val datePickerDialog =
                DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                    val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                        val dateTime =
                            "$selectedYear-${selectedMonth + 1}-$selectedDay $selectedHour:$selectedMinute"
                        selesaiSewa.setText(dateTime)
                    }, hour, minute, true)
                    timePickerDialog.show()
                }, year, month, day)
            datePickerDialog.show()
        }

        cekHarga.setOnClickListener {
            waktu_sewa = lamaSewa.text.toString().toInt()

            when (jenisMobil.selectedItem.toString()) {
                "Honda Brio 2021", "Daihatsu Ayla 2023" -> {
                    hs_mobil = 500000
                    ttl_hrg = waktu_sewa * hs_mobil
                    hargaSewa.setText(hs_mobil.toString())
                    totalHarga.setText(ttl_hrg.toString())
                }
                "Kijang Innova 2021" -> {
                    hs_mobil = 700000
                    ttl_hrg = waktu_sewa * hs_mobil
                    hargaSewa.setText(hs_mobil.toString())
                    totalHarga.setText(ttl_hrg.toString())
                }
                "Toyota Yaris 2021" -> {
                    hs_mobil = 600000
                    ttl_hrg = waktu_sewa * hs_mobil
                    hargaSewa.setText(hs_mobil.toString())
                    totalHarga.setText(ttl_hrg.toString())
                }
                "Toyota Alpard 2022" -> {
                    hs_mobil = 2000000
                    ttl_hrg = waktu_sewa * hs_mobil
                    hargaSewa.setText(hs_mobil.toString())
                    totalHarga.setText(ttl_hrg.toString())
                }
                "Honda BRV 2023" -> {
                    hs_mobil = 800000
                    ttl_hrg = waktu_sewa * hs_mobil
                    hargaSewa.setText(hs_mobil.toString())
                    totalHarga.setText(ttl_hrg.toString())
                }
            }
        }

        sewaButton.setOnClickListener {
            val nama = namaPenyewa.text.toString().trim()
            val jenis = jenisMobil.selectedItem.toString().trim()
            val lama = lamaSewa.text.toString().trim()
            val mulai = mulaiSewa.text.toString().trim()
            val selesai = selesaiSewa.text.toString().trim()
            val total = totalHarga.text.toString().trim()

            if (nama.isEmpty()) {
                showError(namaPenyewa, "Nama tidak boleh kosong")
            } else if (lama.isEmpty()) {
                showError(lamaSewa, "Lama Sewa tidak boleh kosong")
            } else if (mulai.isEmpty()) {
                showError(mulaiSewa, "Mulai Sewa tidak boleh kosong")
            } else if (selesai.isEmpty()) {
                showError(selesaiSewa, "Selesai Sewa tidak boleh kosong")
            } else {
                if (getId != 0) {
                    updateDataToDatabase(getId, nama, jenis, lama, mulai, selesai, total)
                } else {
                    insertDataToDatabase(nama, jenis, lama, mulai, selesai, total)
                }
            }
            val intent = Intent(this, Transaksi::class.java)
            startActivity(intent)
        }
    }

    // membuat tombol kembali ke activity sebelumnya
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    // membuat tombol more (3 titik) pada action bar jika id != 0
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (getId != 0) menuInflater.inflate(R.menu.menu_edit, menu)
        return true
    }

    // membuat fungsi untuk menentukan action dari tombol more (3 titik) pada action bar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_archive -> {
                val nama = namaPenyewa.text.toString().trim()
                val jenis = jenisMobil.selectedItem.toString().trim()
                val lama = lamaSewa.text.toString().trim()
                val mulai = mulaiSewa.text.toString().trim()
                val selesai = selesaiSewa.text.toString().trim()
                val total = totalHarga.text.toString().trim()

                updateDataToDatabase(getId, nama, jenis, lama, mulai, selesai, total, true)
            }
            R.id.action_delete -> {
                deleteDataFromDatabase(getId)
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    // fungsi untuk mengindentifikasi id view yang digunakan
    private fun initializeViews() {
        namaPenyewa = findViewById(R.id.nama_penyewa)
        jenisMobil = findViewById(R.id.list_mobil)
        lamaSewa = findViewById(R.id.lama_sewa)
        mulaiSewa = findViewById(R.id.mulai_sewa)
        selesaiSewa = findViewById(R.id.selesai_sewa)
        totalHarga = findViewById(R.id.total_harga)
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
        namaPenyewa.setText(intent.getStringExtra(EXTRA_NAMA))
        jenisMobil.setSelection(p_list_mobil.indexOf(intent.getStringExtra(EXTRA_JENIS)))
        lamaSewa.setText(intent.getStringExtra(EXTRA_LAMA_SEWA))
        mulaiSewa.setText(intent.getStringExtra(EXTRA_MULAI))
        selesaiSewa.setText(intent.getStringExtra(EXTRA_SELESAI))
        totalHarga.setText(intent.getStringExtra(EXTRA_TOTAL))
    }

    // fungsi untuk menampikan error jika ada data yang kosong
    private fun showError(editText: EditText, errorMessage: String) {
        editText.error = errorMessage
    }

    // fungsi untuk update data ke dalam database
    private fun updateDataToDatabase(
        id: Int,
        namaPenyewa: String,
        jenisMobil: String,
        lamaSewa: String,
        mulaiSewa: String,
        selesaiSewa: String,
        totalHarga: String,
        isDone: Boolean = false
    ) {
        val transaksiActivity = TransaksiActivityEntity(id, namaPenyewa, jenisMobil, lamaSewa, mulaiSewa, selesaiSewa, totalHarga, isDone)
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                mTransaksiActivityRepository.update(transaksiActivity)
            }
            showToast("Data berhasil diubah")
            navigateToMainActivity()
        }
    }

    // fungsi untuk memasukkan data ke dalam database
    private fun insertDataToDatabase(
        namaPenyewa: String,
        jenisMobil: String,
        lamaSewa: String,
        mulaiSewa: String,
        selesaiSewa: String,
        totalHarga: String
    ) {
        val transaksiActivity = TransaksiActivityEntity(
            nama = namaPenyewa, jenisMobil = jenisMobil, lamaSewa = lamaSewa, mulaiSewa = mulaiSewa, selesaiSewa = selesaiSewa, total = totalHarga
        )
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                mTransaksiActivityRepository.insert(transaksiActivity)
            }
            showToast("Data berhasil dimasukkan")
            navigateToMainActivity()
        }
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

    // fungsi untuk menampilkan toast
    private fun showToast(message: String) {
        Toast.makeText(this@Pemesanan, message, Toast.LENGTH_SHORT).show()
    }

    // fungsi untuk berpindah ke MainActivity
    private fun navigateToMainActivity() {
        val intent = Intent(this@Pemesanan, Transaksi::class.java)
        startActivity(intent)
    }



    // fungsi untuk mengambil data dari intent
    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_NAMA = "extra_nama"
        const val EXTRA_JENIS = "extra_jenis"
        const val EXTRA_LAMA_SEWA = "extra_lama_sewa"
        const val EXTRA_MULAI = "extra_mulai"
        const val EXTRA_SELESAI = "extra_selesai"
        const val EXTRA_TOTAL = "extra_total"
        const val EXTRA_STATUS = "status"
    }
}