package com.example.smartquiz.navigation.profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.smartquiz.navigation.Routes
import com.example.smartquiz.ui.profile.ProfileScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smartquiz.viewmodel.auth.AuthViewModel

fun NavGraphBuilder.profileNavGraph(
    navController: NavHostController
) {

    composable(Routes.PROFILE) {

        val authViewModel: AuthViewModel = hiltViewModel()

        ProfileScreen(
            onBack = {
                navController.popBackStack()
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
