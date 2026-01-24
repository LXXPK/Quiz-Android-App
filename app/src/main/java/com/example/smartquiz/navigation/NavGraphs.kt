package com.example.smartquiz.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.smartquiz.navigation.auth.authNavGraph
import com.example.smartquiz.navigation.home.homeNavGraph
import com.example.smartquiz.navigation.profile.profileNavGraph
import com.example.smartquiz.navigation.quiz.quizNavGraph

/**
 * Root navigation graph
 * Owned by main integrator
 */
@Composable
fun RootNavGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Route.Auth.route
    ) {
        authNavGraph(navController)
        homeNavGraph(navController)
        quizNavGraph(navController)
        profileNavGraph(navController)
    }
}
