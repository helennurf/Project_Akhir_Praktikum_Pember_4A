package com.example.projectakhir

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class TransaksiActivityEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "nama")
    var nama: String? = null,

    @ColumnInfo(name = "jenisMobil")
    var jenisMobil: String? = null,

    @ColumnInfo(name = "lamaSewa")
    var lamaSewa: String? = null,

    @ColumnInfo(name = "mulaiSewa")
    var mulaiSewa: String? = null,

    @ColumnInfo(name = "selesaiSewa")
    var selesaiSewa: String? = null,

    @ColumnInfo(name = "Total")
    var total: String? = null,

    @ColumnInfo(name = "isDone")
    var isDone: Boolean = false
)