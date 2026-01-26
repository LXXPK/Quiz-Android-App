package com.example.smartquiz.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartquiz.data.local.entity.quiz.QuizAttemptEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuizHistoryViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(QuizHistoryUiState())
    val uiState: StateFlow<QuizHistoryUiState> = _uiState.asStateFlow()

    fun loadQuizHistory(userId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                // TODO: Fetch quiz history for specific userId from repository
                // val history = repository.getQuizHistoryForUser(userId)
                
                // Hardcoded dummy data for now
                val dummyHistory = listOf(
                    QuizAttemptEntity(1, "android_basics", userId, 80, System.currentTimeMillis() - 86400000),
                    QuizAttemptEntity(2, "kotlin_advanced", userId, 95, System.currentTimeMillis() - 172800000),
                    QuizAttemptEntity(3, "compose_ui", userId, 70, System.currentTimeMillis() - 259200000)
                )
                
                val totalQuizzes = dummyHistory.size
                val averageScore = dummyHistory.map { it.score }.average()
                val highestScore = dummyHistory.maxOf { it.score }
                
                _uiState.update { it.copy(
                    isLoading = false, 
                    attempts = dummyHistory,
                    totalQuizzes = totalQuizzes,
                    averageScore = averageScore,
                    highestScore = highestScore
                ) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }
}
