package com.example.smartquiz.navigation.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.smartquiz.navigation.Routes
import com.example.smartquiz.ui.home.HomeScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smartquiz.ui.auth.AuthViewModel

fun NavGraphBuilder.homeNavGraph(
    navController: NavHostController
) {

    composable(Routes.HOME) {

        val authViewModel: AuthViewModel = hiltViewModel()

        HomeScreen(
            userEmail = null,
            onProfile = {
                navController.navigate(Routes.PROFILE)
            },
            onLogout = {
                authViewModel.logout()
                navController.navigate(Routes.AUTH) {
                    popUpTo(Routes.HOME) { inclusive = true }
                }
            }
        )
    }
}
