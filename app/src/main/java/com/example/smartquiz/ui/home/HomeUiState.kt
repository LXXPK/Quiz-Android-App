package com.example.smartquiz.ui.home

import com.example.smartquiz.data.local.entity.quiz.QuizEntity
import com.example.smartquiz.data.local.entity.user.UserEntity

data class HomeUiState(
    val isLoading: Boolean = false,
    val user: UserEntity? = null,
    val activeQuizzes: List<QuizEntity> = emptyList(),
    val suggestedQuizzes: List<QuizEntity> = emptyList(),
    val errorMessage: String? = null
)
