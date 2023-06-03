package com.example.projectakhir

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TransaksiActivityRepository(application: Application) {
    private val mTransaksiActivtyDao: TransaksiSewaDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = TransaksiActivityRoomDatabase.getDatabase(application)
        mTransaksiActivtyDao = db.TransaksiActivityDao()
    }

    fun insert(transaksiActivity: TransaksiActivityEntity) {
        executorService.execute { mTransaksiActivtyDao.insert(transaksiActivity) }
    }

    fun update(transaksiActivity: TransaksiActivityEntity) {
        executorService.execute { mTransaksiActivtyDao.update(transaksiActivity) }
    }

    fun deletetransaksiActivityById(Id: Int) {
        mTransaksiActivtyDao.deleteById(Id)
    }

    fun getAllTransaksiActivity(): LiveData<List<TransaksiActivityEntity>> =
        mTransaksiActivtyDao.getAlltransaksiActivity()

    fun getAllArchiveTransaksiActivity(): LiveData<List<TransaksiActivityEntity>> =
        mTransaksiActivtyDao.getAllDonetransaksiActivity()
}