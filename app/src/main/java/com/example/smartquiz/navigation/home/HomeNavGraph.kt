

package com.example.smartquiz.navigation.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smartquiz.navigation.Routes
import com.example.smartquiz.ui.home.HomeScreen
import com.example.smartquiz.viewmodel.auth.AuthViewModel

fun NavGraphBuilder.homeNavGraph(
    navController: NavHostController
) {
    composable(Routes.HOME) {


        HomeScreen(
            handleQuizCardClick = { quiz ->
                navController.navigate(
                    Routes.quizDetails(quiz.quizId)
                )
            },
            onCategoryViewAll = { category ->
                navController.navigate(Routes.quizList(category))
            },
            onSuggestedViewAll = {
                navController.navigate(
                    Routes.SUGGESTED_QUIZZES
                )
            },
            onHistoryClick = {
                navController.navigate(Routes.HISTORY)
            }
        )
    }
}
