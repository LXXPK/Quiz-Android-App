package com.example.smartquiz.viewmodel.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartquiz.data.local.entity.quiz.QuizEntity
import com.example.smartquiz.data.repository.quiz.QuizRepository
import com.example.smartquiz.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * QuizDetailsViewModel
 *
 * This ViewModel powers the Quiz Details screen.
 *
 * Responsibilities:
 * - Load quiz metadata (title, category)
 * - Load total question count
 * - Create a quiz attempt when user clicks "Start Quiz"
 *
 * This ViewModel does NOT:
 * - Load questions
 * - Handle answers
 * - Calculate score
 */
@HiltViewModel
class QuizDetailsViewModel @Inject constructor(
    private val repository: QuizRepository
) : ViewModel() {

    /* ---------- UI STATE ---------- */

    // Quiz metadata shown on the screen
    private val _quiz = MutableStateFlow<QuizEntity?>(null)
    val quiz = _quiz.asStateFlow()

    // Total number of questions in this quiz
    private val _questionCount = MutableStateFlow(0)
    val questionCount = _questionCount.asStateFlow()

    // Emitted once when a quiz attempt is successfully created
    private val _attemptId = MutableStateFlow<Int?>(null)
    val attemptId = _attemptId.asStateFlow()

    // Loading & error state
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    /* ---------- ACTIONS ---------- */

    /**
     * Called when QuizDetailsScreen is first shown.
     * Loads quiz metadata and question count.
     */
    fun loadQuizDetails(quizId: String) {
        viewModelScope.launch {
            _uiState.value = UiState(isLoading = true)


            try {
                _quiz.value = repository.getQuizById(quizId)
                _questionCount.value =
                    repository.getQuestionCount(quizId)


                _uiState.value = UiState(isLoading = false)


            } catch (e: Exception) {
                _uiState.value = UiState(
                    isLoading = false,
                    errorMessage = "Failed to load quiz details"
                )
            }
        }
    }

    /**
     * Called when user clicks "Start Quiz".
     *
     * Creates a new quiz attempt and exposes attemptId
     * so the UI can safely navigate to QuizPlayScreen.
     */
    fun onStartQuizClicked(
        quizId: String,
        userId: String
    ) {
        viewModelScope.launch {
            _uiState.value = UiState(isLoading = true)


            try {
                val id = repository.createQuizAttempt(quizId, userId)
                _attemptId.value = id
                _uiState.value = UiState(isLoading = false)


            } catch (e: Exception) {
                _uiState.value = UiState(
                    isLoading = false,
                    errorMessage = "Unable to start quiz"
                )
            }
        }
    }
}