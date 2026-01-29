package com.example.smartquiz.navigation.history

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.smartquiz.navigation.Routes
import com.example.smartquiz.ui.history.QuizHistoryScreen

fun NavGraphBuilder.historyNavGraph() {
    composable(Routes.HISTORY) {
        QuizHistoryScreen()
    }
}
