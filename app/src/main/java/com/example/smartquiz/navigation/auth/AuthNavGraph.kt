package com.example.smartquiz.navigation.auth

import androidx.compose.material3.SnackbarHostState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.smartquiz.navigation.Routes
import com.example.smartquiz.ui.auth.LoginScreen
import com.example.smartquiz.viewmodel.auth.AuthViewModel

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    composable(Routes.AUTH) {

        val authViewModel: AuthViewModel = hiltViewModel()

        LoginScreen(
            authViewModel = authViewModel,
            snackbarHostState = snackbarHostState,
            onLoginSuccess = {
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.AUTH) { inclusive = true }
                }
            }
        )
    }
}
