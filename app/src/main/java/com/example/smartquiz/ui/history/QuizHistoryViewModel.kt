package com.example.smartquiz.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartquiz.data.local.session.SessionManager
import com.example.smartquiz.ui.history.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class QuizHistoryViewModel @Inject constructor(
    private val repository: HistoryRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuizHistoryUiState())
    val uiState = _uiState.asStateFlow()

    companion object {
        private const val MAX_SCORE = 50
    }

    fun loadHistory() {
        val uid = sessionManager.getUid()

        if (uid == null) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = "User not logged in"
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val attempts = repository.getUserHistory(uid)

                val averagePercentage =
                    if (attempts.isNotEmpty()) {
                        attempts
                            .map { (it.score * 100) / MAX_SCORE }
                            .average()
                    } else {
                        0.0
                    }

                val highestPercentage =
                    attempts.maxOfOrNull { (it.score * 100) / MAX_SCORE } ?: 0

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        attempts = attempts,
                        totalQuizzes = attempts.size,
                        averageScore = averagePercentage,
                        highestScore = highestPercentage
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Failed to load history"
                    )
                }
            }
        }
    }
}
