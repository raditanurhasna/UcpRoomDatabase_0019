package com.example.roomlocaldb.ui.theme.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Dokter
import com.example.ucp2.repository.RepositoryDokter
import kotlinx.coroutines.launch

class DokterViewModel (
    private val repositoryDokter: RepositoryDokter
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
            jamkerja = if (event.jamKerja.isNotEmpty()) null else "Jam Kerja tidak boleh kosong"

        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData(){
        val currentEvent = uiState.dokterEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryDokter.insertDokter(currentEvent.toDokterEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data Berhasil disimpan",
                        dokterEvent = DokterEvent(),
                        isEntryValid = FormErrorState()
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        snackBarMessage = "Data Gagal disimpan"
                    )
                }
            }
        } else {
            uiState = uiState.copy(
                snackBarMessage = "Input tidak valid. Periksa kembali data Anda."
            )
        }
    }
    fun resetSnackBarMessage(){
        uiState = uiState.copy(snackBarMessage = null)
    }

}

data class DokterEvent(
    val id : String = "",
    val nama : String = "",
    val spesialis : String = "",
    val klinik : String = "",
    val nohp : String = "",
    val jamKerja : String = ""

)

fun DokterEvent.toDokterEntity(): Dokter = Dokter(
    id= id,
    nama = nama,
    spesialis = spesialis,
    klinik = klinik,
    nohp = nohp,
    jamKerja = jamKerja,
)
data class FormErrorState(
    val id: String? = null,
    val nama: String? = null,
    val spesialis: String? = null,
    val klinik: String? = null,
    val nohp: String? = null,
    val jamkerja: String? = null,
) {
    fun isValid(): Boolean {
        return id == null
                && nama == null
                && spesialis == null
                && klinik == null
                && nohp == null
                && jamkerja == null
    }
}

data class DokterUIState(
    val dokterEvent: DokterEvent = DokterEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null,
)






