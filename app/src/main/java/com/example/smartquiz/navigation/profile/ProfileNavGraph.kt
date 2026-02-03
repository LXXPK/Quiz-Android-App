package com.example.smartquiz.navigation.profile

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.smartquiz.navigation.Routes
import com.example.smartquiz.ui.profile.ProfileScreen
import com.example.smartquiz.viewmodel.auth.AuthViewModel

fun NavGraphBuilder.profileNavGraph(
    navController: NavHostController
) {
    composable(Routes.PROFILE) {

        val authViewModel: AuthViewModel = hiltViewModel()

        ProfileScreen(
            onLogout = {
                authViewModel.logout()
                navController.navigate(Routes.AUTH) {
                    popUpTo(0) { inclusive = true }
                }
            }
        )
    }
}
