package com.example.ucp2.data.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ucp2.data.entity.Dokter
import kotlinx.coroutines.flow.Flow

@Dao
interface DokterDao {
    @Insert
    suspend fun insertDokter(dokter: DokterDao)

    @Query("SELECT * FROM dokter ORDER BY nama ASC")
    fun getAllDokter(): Flow<List<Dokter>>

    @Query("SELECT * FROM dokter WHERE id = :Id")
    fun getDokter(Id: String): Flow<Dokter>
}