package com.example.ucp2.ui.theme.viewmodel

import com.example.roomlocaldb.ui.theme.viewmodel.FormJadwalErrorState
import com.example.roomlocaldb.ui.theme.viewmodel.JadwalEvent
import com.example.roomlocaldb.ui.theme.viewmodel.JadwalUIState
import com.example.roomlocaldb.ui.theme.viewmodel.toJadwalEntity
import com.example.ucp2.data.entity.Jadwal
import com.example.ucp2.repository.RepositoryJadwal
import com.example.ucp2.ui.theme.navigation.DestinasiUpdate
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateJadwalViewModel (
    savedStateHandle: SavedStateHandle,
    private val repositoryJadwal: RepositoryJadwal
) : ViewModel() {

    var updateUIState by mutableStateOf(JadwalUIState())
        private set

    private val _id: String = checkNotNull(savedStateHandle[DestinasiUpdate.ID])

    init {
        viewModelScope.launch {
            updateUIState = repositoryJadwal.getJadwal(_id)
                .filterNotNull()
                .first()
                .toUIStateJadwal()
        }
    }

    fun updateState(jadwalEvent: JadwalEvent) {
        updateUIState = updateUIState.copy(
            jadwalEvent = jadwalEvent
        )
    }

    fun validateFields(): Boolean {
        val event = updateUIState.jadwalEvent
        val errorState = FormJadwalErrorState(
            id = if (event.id.isNotEmpty()) null else "ID tidak boleh kosong",
            namaDokter = if (event.namaDokter.isNotEmpty()) null else "Nama Dokter boleh kosong",
            namaPasien = if (event.namaPasien.isNotEmpty()) null else "Nama Pasien tidak boleh kosong",
            nohp = if (event.nohp.isNotEmpty()) null else "No Hp tidak boleh kosong",
            tanggalKonsultasi = if (event.tanggalKonsultasi.isNotEmpty()) null else "Tanggal Konsultasi tidak boleh kosong",
            status = if (event.status.isNotEmpty()) null else "Status tidak boleh kosong"
        )

        updateUIState = updateUIState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateData() {
        val currentEvent = updateUIState.jadwalEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryJadwal.updateJadwal(currentEvent.toJadwalEntity())
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data berhasil di update",
                        jadwalEvent = JadwalEvent(),
                        isEntryValid = FormJadwalErrorState()
                    )
                    println("snackBarMessage diatur: ${updateUIState.snackBarMessage}")
                } catch (e: Exception) {
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data gagal diupdate"
                    )
                }
            }
        } else {
            updateUIState = updateUIState.copy(
                snackBarMessage = "Data gagal diupdate"
            )
        }
    }

    fun resetSnackBarMessage() {
        updateUIState = updateUIState.copy(snackBarMessage = null)
    }
}

fun Jadwal.toUIStateJadwal (): JadwalUIState = JadwalUIState(
    jadwalEvent = this.toDetailUiEvent()
)