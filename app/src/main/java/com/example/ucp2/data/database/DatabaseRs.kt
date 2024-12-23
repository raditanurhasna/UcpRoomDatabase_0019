package com.example.ucp2.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ucp2.data.DAO.DokterDao
import com.example.ucp2.data.DAO.JadwalDao
import com.example.ucp2.data.entity.Dokter
import com.example.ucp2.data.entity.Jadwal

@Database(entities = [Dokter::class,Jadwal::class], version = 1, exportSchema = false)
abstract class DatabaseRs : RoomDatabase() {

    abstract fun DokterDao(): DokterDao
    abstract fun JadwalDao(): JadwalDao

    companion object {
        @Volatile
        private var Instance: DatabaseRs? = null

        fun getDatabase(context: Context): DatabaseRs {
            return (Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseRs::class.java,
                    "DatabaseRS"
                )
                    .build().also { Instance = it }
            })
        }
    }
}

