package com.example.smartquiz.navigation.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.smartquiz.navigation.Route
import com.example.smartquiz.ui.auth.AuthScreen

/**
 * Auth feature navigation
 * Owned by Auth team member
 */
fun NavGraphBuilder.authNavGraph(
    navController: NavController
) {

    composable(Route.Auth.route) {
        AuthScreen(
            onLoginSuccess = {
                navController.navigate(Route.Home.route) {
                    popUpTo(Route.Auth.route) {
                        inclusive = true
                    }
                }
            }
        )
    }
}
