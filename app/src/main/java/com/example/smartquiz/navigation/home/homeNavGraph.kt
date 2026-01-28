//package com.example.smartquiz.navigation.home
//
//import androidx.navigation.NavGraphBuilder
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.composable
//import androidx.hilt.navigation.compose.hiltViewModel
//import com.example.smartquiz.navigation.Routes
//import com.example.smartquiz.ui.home.HomeScreen
//import com.example.smartquiz.viewmodel.auth.AuthViewModel
//
//fun NavGraphBuilder.homeNavGraph(
//    navController: NavHostController
//) {
//
//    composable(Routes.HOME) {
//
//        // AuthViewModel already has access to SessionManager via AuthRepository
//        val authViewModel: AuthViewModel = hiltViewModel()
//
//        HomeScreen(
//            handleQuizCardClick = { quiz ->
//
//                val uid = authViewModel.getUid()
//                if (uid == null) {
//                    // safety fallback — user session missing
//                    navController.navigate(Routes.AUTH) {
//                        popUpTo(Routes.HOME) { inclusive = true }
//                    }
//                    return@HomeScreen
//                }
//
//                navController.navigate(
//                    Routes.quizDetails(quiz.quizId, uid)
//                )
//            }
//        )
//    }
//}


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

        val authViewModel: AuthViewModel = hiltViewModel()

        HomeScreen(
            handleQuizCardClick = { quiz ->
                val uid = authViewModel.getUid() ?: return@HomeScreen
                navController.navigate(
                    Routes.quizDetails(quiz.quizId, uid)
                )
            },
            onCategoryViewAll = { category ->
                navController.navigate(Routes.quizList(category))
            },
            onHistoryClick = {
                navController.navigate(Routes.HISTORY)
            }
        )
    }
}
