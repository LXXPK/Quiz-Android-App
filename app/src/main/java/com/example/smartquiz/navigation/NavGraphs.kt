package com.example.smartquiz.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.smartquiz.ui.auth.AuthViewModel
import com.example.smartquiz.navigation.auth.authNavGraph
import com.example.smartquiz.navigation.home.homeNavGraph
import com.example.smartquiz.navigation.profile.profileNavGraph

@Composable
fun RootNavGraph(
    navController: NavHostController = rememberNavController()
) {

    val authViewModel: AuthViewModel = hiltViewModel()

    val startDestination =
        if (authViewModel.getUid() != null)
            Routes.HOME
        else
            Routes.AUTH

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        authNavGraph(navController)
        homeNavGraph(navController)
        profileNavGraph(navController)
    }
}
