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
