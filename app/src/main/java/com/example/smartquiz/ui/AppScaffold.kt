package com.example.smartquiz.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.*
import com.example.smartquiz.navigation.RootNavGraph
import com.example.smartquiz.navigation.Routes
import com.example.smartquiz.ui.common.SmartSnackbar
import com.example.smartquiz.viewmodel.auth.AuthViewModel

@Composable
fun AppScaffold(

) {

    val authViewModel: AuthViewModel = hiltViewModel()
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val showTopBar = currentRoute != Routes.AUTH

    Scaffold(
        topBar = {
            if (showTopBar) {
                SmartQuizTopBar(
                    currentRoute = currentRoute,
                    navController = navController,
                    onLogoutClick = {
                        authViewModel.logout()
                        navController.navigate(Routes.AUTH) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { data ->
                    SmartSnackbar(data = data)
                }
            )
        }

    ) { padding ->
        RootNavGraph(
            navController = navController,
            snackbarHostState = snackbarHostState,
            modifier = Modifier.padding(padding)
        )
    }
}
