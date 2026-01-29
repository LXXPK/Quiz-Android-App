package com.example.smartquiz.navigation

object Routes {

    const val AUTH = "auth"
    const val HOME = "home"
    const val HISTORY = "history"
    const val PROFILE = "profile"

    const val QUIZ_LIST = "quiz_list"
    const val QUIZ_DETAILS = "quiz_details"

    const val SUGGESTED_QUIZZES = "suggested_quizzes"


    const val QUIZ_PLAY = "quiz_play/{quizId}/{attemptId}"
    const val QUIZ_RESULT = "quiz_result"

    fun suggestedQuizzes() = SUGGESTED_QUIZZES

    fun quizList(category: String) =
        "$QUIZ_LIST/$category"

    fun quizDetails(quizId: String) =
        "$QUIZ_DETAILS/$quizId"

    fun quizPlay(quizId: String, attemptId: Int) =
        "quiz_play/$quizId/$attemptId"
}
