package com.example.smartquiz.viewmodel.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartquiz.data.local.entity.quiz.QuizEntity
import com.example.smartquiz.data.repository.quiz.QuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/*
 * ViewModel responsible for Quiz Details screen.
 *
 * Responsibilities:
 * - Load quiz metadata
 * - Load question count
 * - Create quiz attempt when user clicks "Start"
 *
 * This ViewModel does NOT handle:
 * - Quiz playing
 * - Answers
 * - Scoring
 */
class QuizDetailsViewModel(
    private val repository: QuizRepository
) : ViewModel() {

    /*
     * Quiz metadata (title, category, etc.)
     * Observed by UI.
     */
    val quiz = MutableStateFlow<QuizEntity?>(null)

    /*
     * Total number of questions in the quiz.
     * Display-only information.
     */
    val questionCount = MutableStateFlow(0)

    /*
     * attemptId is emitted once quiz is started.
     * UI listens to this to trigger navigation.
     */
    private val _attemptId = MutableStateFlow<Int?>(null)
    val attemptId = _attemptId.asStateFlow()

    /*
     * Loads quiz details when screen opens.
     * Called once per quizId.
     */
    fun loadQuizDetails(quizId: String) {
        viewModelScope.launch {
            quiz.value = repository.getQuizById(quizId)
            questionCount.value =
                repository.getQuestionCount(quizId)
        }
    }

    /*
     * Triggered when user clicks "Start Quiz".
     *
     * Creates a quiz attempt and exposes attemptId
     * for the UI to react (navigate).
     */
    fun onStartQuizClicked(
        quizId: String,
        userId: String
    ) {
        viewModelScope.launch {
            val id = repository.createQuizAttempt(
                quizId = quizId,
                userId = userId
            )
            _attemptId.value = id
        }
    }
}