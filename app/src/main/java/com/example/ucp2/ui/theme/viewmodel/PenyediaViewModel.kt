package com.example.ucp2.ui.theme.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2.RsApp

object PenyediaViewModel {

    val Factory = viewModelFactory {
        initializer {
            DokterViewModel(
                RsApp().containerApp.repositoryDokter
            )
        }
        initializer {
            HomeDokterViewModel(
                RsApp().containerApp.repositoryDokter,
            )
        }
        initializer {
            JadwalViewModel(
                RsApp().containerApp.repositoryJadwal,
            )
        }
        initializer {
            HomeJadwalViewModel(
                RsApp().containerApp.repositoryJadwal,
            )
        }
        initializer {
            DetailJadwalViewModel(
                createSavedStateHandle(),
                RsApp().containerApp.repositoryJadwal
            )
        }
        initializer {
            UpdateJadwalViewModel(
                createSavedStateHandle(),
                RsApp().containerApp.repositoryJadwal
            )
        }


    }

    fun CreationExtras.RsApp(): RsApp {
        val app = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
        require(app is RsApp) { "APPLICATION_KEY must hold an RsApp instance" }
        return app as RsApp
    }
}