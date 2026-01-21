package com.example.smartquiz.ui.home

import com.example.smartquiz.data.local.entity.quiz.QuizEntity

data class HomeUiState(
    val isLoading: Boolean = false,
    val quizzes: List<QuizEntity> = emptyList(),
    val errorMessage: String? = null
)
