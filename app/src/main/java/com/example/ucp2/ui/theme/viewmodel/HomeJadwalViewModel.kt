package com.example.ucp2.ui.theme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Jadwal
import com.example.ucp2.repository.RepositoryJadwal
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeJadwalViewModel (
    private val repositoryJadwal: RepositoryJadwal
) : ViewModel() {

    val homeJadwalUIState: StateFlow<HomeJadwalUiState> = repositoryJadwal.getAllJadwal()
        .filterNotNull()
        .map {
            HomeJadwalUiState (
                listJadwal = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            emit(HomeJadwalUiState(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeJadwalUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeJadwalUiState (
                isLoading = true,
            )
        )
}

data class HomeJadwalUiState(
    val listJadwal: List<Jadwal> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)