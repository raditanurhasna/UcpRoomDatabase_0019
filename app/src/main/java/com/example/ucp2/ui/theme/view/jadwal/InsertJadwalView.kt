package com.example.ucp2.ui.theme.view.jadwal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.ui.theme.customewidget.TopAppBar
import com.example.ucp2.ui.theme.navigation.AlamatNavigasi
import com.example.ucp2.ui.theme.viewmodel.FormJadwalErrorState
import com.example.ucp2.ui.theme.viewmodel.JadwalEvent
import com.example.ucp2.ui.theme.viewmodel.JadwalUIState
import com.example.ucp2.ui.theme.viewmodel.JadwalViewModel
import com.example.ucp2.ui.theme.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiInsert : AlamatNavigasi {
    override val route: String = "insert_Jadwal"
}


@Composable
fun InsertJadwalView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: JadwalViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }
    Scaffold(
        modifier = modifier,
        snackbarHost =  { SnackbarHost(hostState = snackbarHostState) }
    ){ padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ){
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Pasien"

            )
            InsertBodyJadwal(
                uiState = uiState,
                onValueChange = { updatedEvent->
                    viewModel.updateState(updatedEvent)
                },
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveData()
                    }
                    onNavigate()
                }
            )
        }

    }
}




@Composable
fun FormJadwal(
    jadwalEvent: JadwalEvent = JadwalEvent(),
    onValueChange: (JadwalEvent) -> Unit = {},
    FormJadwalErrorState: FormJadwalErrorState = FormJadwalErrorState(),
    modifier: Modifier = Modifier
){

    Column(
        modifier = modifier.fillMaxWidth()
    ){
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = jadwalEvent.id,
            onValueChange = {
                onValueChange(jadwalEvent.copy(id = it))
            },
            label = { Text("id") },
            isError = FormJadwalErrorState.id != null,
            placeholder = { Text("Masukan ID") },
        )
        Text(
            text = FormJadwalErrorState.id?: "",
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = jadwalEvent.namaDokter,
            onValueChange = {
                onValueChange(jadwalEvent.copy(namaDokter = it))
            },
            label = { Text("Nama Dokter") },
            isError = FormJadwalErrorState.namaDokter != null,
            placeholder = { Text("Masukan Nama Dokter") },
        )
        Text(
            text = FormJadwalErrorState.namaDokter?: "",
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = jadwalEvent.namaPasien,
            onValueChange = {
                onValueChange(jadwalEvent.copy(namaPasien =  it))
            },
            label = { Text("Nama Pasien") },
            isError = FormJadwalErrorState.namaPasien != null,
            placeholder = { Text("Masukan Pasien") },
        )
        Text(
            text = FormJadwalErrorState.namaPasien?: "",
            color = Color.Red

        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = jadwalEvent.nohp,
            onValueChange = {
                onValueChange(jadwalEvent.copy(nohp = it))
            },
            label = { Text("No Hp") },
            isError = FormJadwalErrorState.nohp != null,
            placeholder = { Text("Masukan Nohp") },
        )
        Text(
            text = FormJadwalErrorState.nohp?: "",
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = jadwalEvent.tanggalKonsultasi,
            onValueChange = {
                onValueChange(jadwalEvent.copy(tanggalKonsultasi = it))
            },
            label = { Text("Tanggal Konsultasi") },
            isError = FormJadwalErrorState.tanggalKonsultasi != null,
            placeholder = { Text("Tanggal Konsultasi") },
        )
        Text(
            text = FormJadwalErrorState.tanggalKonsultasi?: "",
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = jadwalEvent.status,
            onValueChange = {
                onValueChange(jadwalEvent.copy(status = it))
            },
            label = { Text("Status") },
            isError = FormJadwalErrorState.status != null,
            placeholder = { Text("Status") },
        )
        Text(
            text = FormJadwalErrorState.status?: "",
            color = Color.Red
        )



    }

}

@Composable
fun InsertBodyJadwal(
    modifier: Modifier = Modifier,
    onValueChange: (JadwalEvent) -> Unit,
    uiState: JadwalUIState,
    onClick:() -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        FormJadwal(
            jadwalEvent = uiState.jadwalEvent,
            onValueChange = onValueChange,
            FormJadwalErrorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Simpan")
        }

    }
}