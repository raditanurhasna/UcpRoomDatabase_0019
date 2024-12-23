package com.example.ucp2.ui.theme.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Jadwal
import com.example.ucp2.repository.RepositoryJadwal
import kotlinx.coroutines.launch

class JadwalViewModel (
    private val repositoryJadwal: RepositoryJadwal
) : ViewModel() {
    var uiState by mutableStateOf(JadwalUIState())

    fun updateState(jadwalEvent: JadwalEvent){
        uiState = uiState.copy(
            jadwalEvent = jadwalEvent,
        )
    }
    private  fun validateFields(): Boolean {
        val event = uiState.jadwalEvent
        val errorState = FormJadwalErrorState(
            id = if (event.id.isNotEmpty()) null else "ID tidak boleh kosong",
            namaDokter = if (event.namaDokter.isNotEmpty()) null else "Nama Dokter boleh kosong",
            namaPasien = if (event.namaPasien.isNotEmpty()) null else "Nama Pasien tidak boleh kosong",
            nohp= if (event.nohp.isNotEmpty()) null else "No Hp tidak boleh kosong",
            tanggalKonsultasi = if (event.tanggalKonsultasi.isNotEmpty()) null else "tanggal KOnsiltasi tidak boleh kosong",
            status = if (event.status.isNotEmpty()) null else "status tidak boleh kosong"

        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData(){
        val currentEvent = uiState.jadwalEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryJadwal.insertJadwal(currentEvent.toJadwalEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data Berhasil disimpan",
                        jadwalEvent = JadwalEvent(),
                        isEntryValid = FormJadwalErrorState()
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

data class JadwalEvent(
    val id : String = "",
    val namaDokter : String = "",
    val namaPasien : String = "",
    val nohp : String = "",
    val tanggalKonsultasi : String = "",
    val status : String = "",

)

fun JadwalEvent.toJadwalEntity(): Jadwal = Jadwal(
    id= id,
    namaDokter = namaDokter,
    namaPasien = namaPasien,
    nohp = nohp,
    tanggalKonsultasi = tanggalKonsultasi,
    status = status,

)
data class FormJadwalErrorState(
    val id: String? = null,
    val namaDokter: String? = null,
    val namaPasien: String? = null,
    val nohp: String? = null,
    val tanggalKonsultasi: String? = null,
    val status: String? = null,
) {
    fun isValid(): Boolean {
        return id == null
                && namaDokter == null
                && namaPasien == null
                && nohp == null
                && tanggalKonsultasi == null
                && status == null
    }
}

data class JadwalUIState(
    val jadwalEvent: JadwalEvent = JadwalEvent(),
    val isEntryValid: FormJadwalErrorState = FormJadwalErrorState(),
    val snackBarMessage: String? = null,
)







