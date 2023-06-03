package com.example.projectakhir

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TransaksiSewaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(transaksiActivity: TransaksiActivityEntity)

    @Update
    fun update(transaksiActivity: TransaksiActivityEntity)

    @Query("DELETE FROM TransaksiActivityEntity WHERE id = :transaksiActivityId")
    fun deleteById(transaksiActivityId: Int)

    @Query("SELECT * from TransaksiActivityEntity WHERE isDone = 0 ORDER BY mulaiSewa ASC")
    fun getAlltransaksiActivity(): LiveData<List<TransaksiActivityEntity>>

    @Query("SELECT * from TransaksiActivityEntity WHERE isDone = 1 ORDER BY mulaiSewa ASC")
    fun getAllDonetransaksiActivity(): LiveData<List<TransaksiActivityEntity>>
}