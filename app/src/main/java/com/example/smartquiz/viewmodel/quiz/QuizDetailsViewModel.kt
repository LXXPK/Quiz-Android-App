package com.example.smartquiz.viewmodel.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartquiz.data.local.entity.quiz.QuizEntity
import com.example.smartquiz.data.local.session.SessionManager
import com.example.smartquiz.data.repository.quiz.QuizRepository
import com.example.smartquiz.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizDetailsViewModel @Inject constructor(
    private val repository: QuizRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val userId: String =
        sessionManager.getUid()
            ?: throw IllegalStateException("User not logged in")
    private val _quiz = MutableStateFlow<QuizEntity?>(null)
    val quiz = _quiz.asStateFlow()

    private val _questionCount = MutableStateFlow(0)
    val questionCount = _questionCount.asStateFlow()

    private val _attemptId = MutableStateFlow<Int?>(null)
    val attemptId = _attemptId.asStateFlow()

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun loadQuizDetails(quizId: String) {
        viewModelScope.launch {
            _uiState.value = UiState(isLoading = true)

            try {
                _quiz.value = repository.getQuizById(quizId)
                _questionCount.value = repository.getQuestionCount(quizId)

                _uiState.value = UiState()
            } catch (e: Exception) {
                _uiState.value = UiState(
                    errorMessage = "Failed to load quiz details"
                )
            }
        }
    }

    fun onStartQuizClicked(
        quizId: String
    ) {
        viewModelScope.launch {
            _uiState.value = UiState(isLoading = true)

            try {
                _attemptId.value =
                    repository.createQuizAttempt(quizId, userId)

                _uiState.value = UiState()
            } catch (e: Exception) {
                _uiState.value = UiState(
                    errorMessage = "Unable to start quiz"
                )
            }
        }
    }


    fun consumeAttemptId() {
        _attemptId.value = null
    }
}
