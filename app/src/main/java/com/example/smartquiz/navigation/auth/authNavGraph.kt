package com.example.smartquiz.navigation.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.smartquiz.navigation.Routes
import com.example.smartquiz.ui.auth.LoginScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smartquiz.viewmodel.auth.AuthViewModel

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {

    composable(Routes.AUTH) {

        val authViewModel: AuthViewModel = hiltViewModel()

        LoginScreen(
            authViewModel = authViewModel,
            onLoginSuccess = {
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.AUTH) { inclusive = true }
                }
            }
        )
    }
}
