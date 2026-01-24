//package com.example.smartquiz.navigation.profile
//
//import androidx.navigation.NavController
//import androidx.navigation.NavGraphBuilder
//import androidx.navigation.compose.composable
//import com.example.smartquiz.navigation.Route
//import com.example.smartquiz.ui.profile.EditInterestsScreen
//import com.example.smartquiz.ui.profile.ProfileScreen
//
///**
// * Profile feature navigation
// * Owned by Profile team member
// */
//fun NavGraphBuilder.profileNavGraph(
//    navController: NavController
//) {
//
//    composable(Route.Profile.route) {
//        ProfileScreen(
//            onEditInterests = {
//                navController.navigate(Route.EditInterests.route)
//            }
//        )
//    }
//
//    composable(Route.EditInterests.route) {
//        EditInterestsScreen(
//            onBack = {
//                navController.popBackStack()
//            }
//        )
//    }
//}
