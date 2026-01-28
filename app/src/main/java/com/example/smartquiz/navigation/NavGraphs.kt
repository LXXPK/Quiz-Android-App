package com.example.smartquiz.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.smartquiz.navigation.auth.authNavGraph
import com.example.smartquiz.navigation.history.historyNavGraph
import com.example.smartquiz.navigation.home.homeNavGraph
import com.example.smartquiz.navigation.profile.profileNavGraph
import com.example.smartquiz.navigation.quiz.quizNavGraph
import com.example.smartquiz.viewmodel.auth.AuthViewModel
import androidx.compose.material3.SnackbarHostState

@Composable
fun RootNavGraph(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    val authViewModel: AuthViewModel = hiltViewModel()

    val startDestination =
        if (authViewModel.getUid() != null) Routes.HOME else Routes.AUTH

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        authNavGraph(navController, snackbarHostState)
        homeNavGraph(navController)
        historyNavGraph()
        profileNavGraph(navController)
        quizNavGraph(navController)
    }
}
