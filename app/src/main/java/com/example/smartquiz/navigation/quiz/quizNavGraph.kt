package com.example.smartquiz.navigation.quiz

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.smartquiz.navigation.Route
import com.example.smartquiz.ui.quiz.QuizListScreen
import com.example.smartquiz.ui.quiz.QuizPlayScreen
import com.example.smartquiz.ui.quiz.QuizResultScreen

/**
 * Quiz feature navigation
 * Owned by Quiz team member
 */
fun NavGraphBuilder.quizNavGraph(
    navController: NavController
) {

    composable(Route.QuizList.route) {
        QuizListScreen(
            onQuizStart = { quizId ->
                navController.navigate(Route.QuizPlay.create(quizId))
            }
        )
    }

    composable(
        route = Route.QuizPlay.route,
        arguments = listOf(
            navArgument("quizId") {
                type = NavType.StringType
            }
        )
    ) {
        QuizPlayScreen(
            onFinish = { attemptId ->
                navController.navigate(
                    Route.QuizResult.create(attemptId)
                )
            }
        )
    }

    composable(
        route = Route.QuizResult.route,
        arguments = listOf(
            navArgument("attemptId") {
                type = NavType.IntType
            }
        )
    ) {
        QuizResultScreen(
            onBackHome = {
                navController.navigate(Route.Home.route) {
                    popUpTo(Route.Home.route)
                }
            }
        )
    }
}
