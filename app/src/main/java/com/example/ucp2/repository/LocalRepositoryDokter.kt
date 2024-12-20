package com.example.ucp2.repository

import com.example.ucp2.data.DAO.DokterDao
import com.example.ucp2.data.entity.Dokter
import kotlinx.coroutines.flow.Flow

class LocalRepositoryDokter (
    private val dokterDao: DokterDao
) : RepositoryDokter {

    override suspend fun insertDokter(dokter: Dokter) {
        dokterDao.insertDokter(dokter)
    }
    override fun getAllDokter(): Flow<List<Dokter>> {
        return dokterDao.getAllDokter()
    }

    override fun getDokter(id: String): Flow<Dokter> {
        return dokterDao.getDokter(id)
    }

}