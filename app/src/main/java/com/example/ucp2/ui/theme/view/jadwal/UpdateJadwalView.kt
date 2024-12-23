package com.example.ucp2.ui.theme.view.jadwal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.ui.theme.customewidget.TopAppBar
import com.example.ucp2.ui.theme.viewmodel.PenyediaViewModel
import com.example.ucp2.ui.theme.viewmodel.UpdateJadwalViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun UpdateJadwalView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateJadwalViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val uiState = viewModel.updateUIState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    //observasi perubahan snackbarmessage
    LaunchedEffect(uiState,snackbarHostState) {
        println("LaunchedEffect triggered")
        uiState.snackBarMessage?.let { message ->
            println("Snackbar message received: $message")
            coroutineScope.launch {
                println("Launching corotine for snackbar")
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Long
                )
                viewModel.resetSnackBarMessage()
            }
        }
    }
    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                judul = "Edit Pasien",
                showBackButton = true,
                onBack = onBack,
            )
        }
    ){ Padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Padding)
                .padding(16.dp)
        ){

            InsertBodyJadwal(
                uiState = uiState,
                onValueChange = { updateEvent ->
                    viewModel.updateState(updateEvent)
                },
                onClick = {
                    coroutineScope.launch {
                        if (viewModel.validateFields()) {
                            viewModel.updateData()
                            delay(600)
                            withContext(Dispatchers.Main){
                                onNavigate()
                            }
                        }
                    }
                }
            )
        }

    }
}