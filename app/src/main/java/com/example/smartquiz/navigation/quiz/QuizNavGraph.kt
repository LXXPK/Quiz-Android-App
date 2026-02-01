package com.example.smartquiz.navigation.quiz

import android.net.Uri
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.smartquiz.R
import com.example.smartquiz.navigation.Routes
import com.example.smartquiz.ui.quiz.quizdetails.QuizDetailsScreen
import com.example.smartquiz.ui.quiz.quizlist.QuizListScreen
import com.example.smartquiz.ui.quiz.quizplay.QuizPlayScreen
import com.example.smartquiz.ui.quiz.quizresult.QuizResultScreen
import com.example.smartquiz.utils.NavConstants
import com.example.smartquiz.viewmodel.quiz.QuizListViewModel
import com.example.smartquiz.viewmodel.quiz.QuizPlayViewModel

fun NavGraphBuilder.quizNavGraph(
    navController: NavController
) {


    composable(
        route = "${Routes.QUIZ_LIST}/{${NavConstants.ARG_CATEGORY}}",
        arguments = listOf(
            navArgument(NavConstants.ARG_CATEGORY) {
                type = NavType.StringType
            }
        )
    ) { backStackEntry ->


        val category = Uri.decode(
            backStackEntry.arguments!!
                .getString(NavConstants.ARG_CATEGORY)!!
        )

        QuizListScreen(
            category = category,
            onQuizSelected = { quizId ->
                navController.navigate(
                    Routes.quizDetails(quizId)
                )
            }
        )
    }


    composable(
        route = "${Routes.QUIZ_DETAILS}/{${NavConstants.ARG_QUIZ_ID}}",
        arguments = listOf(
            navArgument(NavConstants.ARG_QUIZ_ID) {
                type = NavType.StringType
            }
        )
    ) { backStackEntry ->
        QuizDetailsScreen(
            quizId = backStackEntry.arguments!!
                .getString(NavConstants.ARG_QUIZ_ID)!!,
            navController = navController
        )
    }


    composable(
        route = Routes.QUIZ_PLAY,
        arguments = listOf(
            navArgument(NavConstants.ARG_QUIZ_ID) {
                type = NavType.StringType
            },
            navArgument(NavConstants.ARG_ATTEMPT_ID) {
                type = NavType.IntType
            }
        )
    ) { backStackEntry ->

        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry(Routes.QUIZ_PLAY)
        }

        val viewModel: QuizPlayViewModel = hiltViewModel(parentEntry)

        QuizPlayScreen(
            quizId = backStackEntry.arguments!!
                .getString(NavConstants.ARG_QUIZ_ID)!!,
            attemptId = backStackEntry.arguments!!
                .getInt(NavConstants.ARG_ATTEMPT_ID),
            viewModel = viewModel,
            onFinish = {
                navController.navigate(Routes.QUIZ_RESULT)
            }
        )
    }


    composable(Routes.QUIZ_RESULT) { backStackEntry ->

        val parentEntry = remember(backStackEntry) {
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


    composable(Routes.SUGGESTED_QUIZZES) {

        val viewModel: QuizListViewModel = hiltViewModel()

        LaunchedEffect(Unit) {
            viewModel.loadSuggestedQuizzes()
        }

        QuizListScreen(
            category = stringResource(R.string.home_recommended_title),
            onQuizSelected = { quizId ->
                navController.navigate(
                    Routes.quizDetails(quizId)
                )
            }
        )
    }
}
