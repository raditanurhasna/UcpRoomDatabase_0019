package com.example.roomlocaldb.ui.theme.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Dokter
import com.example.ucp2.repository.LocalRepositoryDokter
import kotlinx.coroutines.launch

class DokterViewModel (
    private val repositoryDokter: LocalRepositoryDokter
) : ViewModel() {
    var uiState by mutableStateOf(DokterUIState())

    fun updateState(dokterEvent: DokterEvent){
        uiState = uiState.copy(
            dokterEvent = dokterEvent,
        )
    }
    private  fun validateFields(): Boolean {
        val event = uiState.dokterEvent
        val errorState = FormErrorState(
            id = if (event.id.isNotEmpty()) null else "ID tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            spesialis = if (event.spesialis.isNotEmpty()) null else "Spesialis tidak boleh kosong",
            klinik = if (event.klinik.isNotEmpty()) null else "Klinik tidak boleh kosong",
            nohp = if (event.nohp.isNotEmpty()) null else "No Hp tidak boleh kosong",
            jamKerja = if (event.jamKerja.isNotEmpty()) null else "Jam Kerja tidak boleh kosong"

        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }


}





