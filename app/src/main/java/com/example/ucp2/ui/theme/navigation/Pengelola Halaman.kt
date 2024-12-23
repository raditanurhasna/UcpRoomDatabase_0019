package com.example.ucp2.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp2.ui.theme.view.dokter.DestinasiInsert
import com.example.ucp2.ui.theme.view.dokter.HomeDokterView
import com.example.ucp2.ui.theme.view.dokter.InsertDokterView
import com.example.ucp2.ui.theme.view.jadwal.DetailJadwalView
import com.example.ucp2.ui.theme.view.jadwal.HomeJadwalView
import com.example.ucp2.ui.theme.view.jadwal.InsertJadwalView
import com.example.ucp2.ui.theme.view.jadwal.UpdateJadwalView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route
    ) {
        composable(route = DestinasiHome.route) {
            HomeDokterView(
                onDetailClick = { id ->
                    navController.navigate("${DestinasiDetail.route}/$id")
                    println("PengelolaHalaman: id = $id")
                },
                onAddDokter = {
                    navController.navigate(DestinasiInsert.route)
                },
                modifier = modifier
            )
        }

        composable(route = DestinasiInsert.route) {
            InsertDokterView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        composable(route = DestinasiHome.route) {
            HomeJadwalView(
                onDetailClick = { id ->
                    navController.navigate("${DestinasiDetail.route}/$id")
                    println("PengelolaHalaman: id = $id")
                },
                onAddJadwal = {
                    navController.navigate(DestinasiInsert.route)
                },
                modifier = modifier
            )
        }

        composable(route = DestinasiInsert.route) {
            InsertJadwalView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        composable(
            DestinasiDetail.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetail.ID) {
                    type = NavType.StringType
                }
            )
        ) {
            val id = it.arguments?.getString(DestinasiDetail.ID)
            id?.let { id ->
                DetailJadwalView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdate.route}/$id")
                    },
                    onDeleteClick = {
                        navController.popBackStack()
                    },
                    modifier = modifier
                )
            }
        }

        composable(
            DestinasiUpdate.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdate.ID) {
                    type = NavType.StringType
                }
            )
        ) {
            UpdateJadwalView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
            )
        }
    }
}
