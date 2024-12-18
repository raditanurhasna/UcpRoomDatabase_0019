package com.example.ucp2.data.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucp2.data.entity.Dokter
import kotlinx.coroutines.flow.Flow

@Dao
interface DokterDao {
    @Insert
    suspend fun insertDokter(dokter: DokterDao)

    @Query("SELECT * FROM dokter ORDER BY nama ASC")
    fun getAllDokter(): Flow<List<Dokter>>

    @Query("SELECT * FROM dokter WHERE id = :id")
    fun getDokter(Id: String): Flow<Dokter>

    @Delete
    suspend fun deleteDokter(dokter: Dokter)

    @Update
    suspend fun updateDokter(dokter: Dokter)
}