package com.example.projectakhir

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TransaksiActivityEntity::class], version = 1)
abstract class TransaksiActivityRoomDatabase : RoomDatabase() {
    abstract fun TransaksiActivityDao(): TransaksiSewaDao

    companion object {
        @Volatile
        private var INSTANCE: TransaksiActivityRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): TransaksiActivityRoomDatabase {
            if (INSTANCE == null) {
                synchronized(TransaksiActivityRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        TransaksiActivityRoomDatabase::class.java, "transaksiactivity_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as TransaksiActivityRoomDatabase
        }
    }
}