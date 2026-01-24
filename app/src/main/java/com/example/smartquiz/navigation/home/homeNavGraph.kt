//package com.example.smartquiz.navigation.home
//
//import androidx.navigation.NavController
//import androidx.navigation.NavGraphBuilder
//import androidx.navigation.compose.composable
//import com.example.smartquiz.navigation.Route
//import com.example.smartquiz.ui.history.HistoryScreen
//import com.example.smartquiz.ui.home.HomeScreen
//
///**
// * Home + History navigation
// * Owned by Home/History team member
// */
//fun NavGraphBuilder.homeNavGraph(
//    navController: NavController
//) {
//
//    composable(Route.Home.route) {
//        HomeScreen(
//            onQuizClick = { quizId ->
//                navController.navigate(Route.QuizPlay.create(quizId))
//            },
//            onProfileClick = {
//                navController.navigate(Route.Profile.route)
//            },
//            onHistoryClick = {
//                navController.navigate(Route.History.route)
//            }
//        )
//    }
//
//    composable(Route.History.route) {
//        HistoryScreen()
//    }
//}
