package com.example.smartquiz.navigation.quiz

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.smartquiz.navigation.Routes
import com.example.smartquiz.ui.quiz.*
import com.example.smartquiz.viewmodel.quiz.QuizPlayViewModel

fun NavGraphBuilder.quizNavGraph(
    navController: NavController
) {

    composable(
        route = "${Routes.QUIZ_LIST}/{category}",
        arguments = listOf(
            navArgument("category") { type = NavType.StringType }
        )
    ) { backStack ->
        QuizListScreen(
            category = backStack.arguments!!.getString("category")!!,
            onQuizSelected = { quizId ->
                val userId = "TEMP_USER"
                navController.navigate(
                    Routes.quizDetails(quizId, userId)
                )
            }
        )
    }

    composable(
        route = "${Routes.QUIZ_DETAILS}/{quizId}/{userId}",
        arguments = listOf(
            navArgument("quizId") { type = NavType.StringType },
            navArgument("userId") { type = NavType.StringType }
        )
    ) { backStack ->
        QuizDetailsScreen(
            quizId = backStack.arguments!!.getString("quizId")!!,
            userId = backStack.arguments!!.getString("userId")!!,
            navController = navController
        )
    }

    composable(
        route = Routes.QUIZ_PLAY,
        arguments = listOf(
            navArgument("quizId") { type = NavType.StringType },
            navArgument("attemptId") { type = NavType.IntType }
        )
    ) { backStack ->

        val parentEntry = remember(backStack) {
            navController.getBackStackEntry(Routes.QUIZ_PLAY)
        }

        val viewModel: QuizPlayViewModel = hiltViewModel(parentEntry)

        QuizPlayScreen(
            quizId = backStack.arguments!!.getString("quizId")!!,
            attemptId = backStack.arguments!!.getInt("attemptId"),
            viewModel = viewModel,
            onFinish = {
                navController.navigate(Routes.QUIZ_RESULT)
            }
        )
    }

    composable(Routes.QUIZ_RESULT) { backStack ->

        val parentEntry = remember(backStack) {
            navController.getBackStackEntry(Routes.QUIZ_PLAY)
        }

        val viewModel: QuizPlayViewModel = hiltViewModel(parentEntry)

        QuizResultScreen(
            viewModel = viewModel,
            onDone = {
                navController.popBackStack(Routes.HOME, false)
            }
        )
    }
}
