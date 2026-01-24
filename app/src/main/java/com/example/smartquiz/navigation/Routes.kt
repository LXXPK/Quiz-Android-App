package com.example.smartquiz.navigation

sealed class Route(val route: String) {

    // Auth
    object Auth : Route("auth")

    // Home
    object Home : Route("home")

    // Quiz
    object QuizList : Route("quiz_list")
    object QuizPlay : Route("quiz_play/{quizId}") {
        fun create(quizId: String) = "quiz_play/$quizId"
    }
    object QuizResult : Route("quiz_result/{attemptId}") {
        fun create(attemptId: Int) = "quiz_result/$attemptId"
    }

    // Profile
    object Profile : Route("profile")
    object EditInterests : Route("profile/edit_interests")

    // History
    object History : Route("history")
}
