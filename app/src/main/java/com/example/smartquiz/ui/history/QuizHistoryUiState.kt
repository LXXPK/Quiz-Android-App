package com.example.smartquiz.ui.history

import com.example.smartquiz.data.local.entity.quiz.QuizAttemptEntity

data class QuizHistoryUiState(
    val isLoading: Boolean = false,
    val attempts: List<QuizAttemptEntity> = emptyList(),
    val averageScore: Double = 0.0,
    val totalQuizzes: Int = 0,
    val highestScore: Int = 0,
    val errorMessage: String? = null
)