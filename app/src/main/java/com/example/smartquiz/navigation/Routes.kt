package com.example.smartquiz.navigation

object Routes {

    const val AUTH = "auth"
    const val HOME = "home"
    const val PROFILE = "profile"

    const val QUIZ_LIST = "quiz_list"
    const val QUIZ_DETAILS = "quiz_details"


    const val QUIZ_PLAY = "quiz_play/{quizId}/{attemptId}"
    const val QUIZ_RESULT = "quiz_result"

    fun quizList(category: String) =
        "$QUIZ_LIST/$category"

    fun quizDetails(quizId: String, userId: String) =
        "$QUIZ_DETAILS/$quizId/$userId"

    fun quizPlay(quizId: String, attemptId: Int) =
        "quiz_play/$quizId/$attemptId"
}
