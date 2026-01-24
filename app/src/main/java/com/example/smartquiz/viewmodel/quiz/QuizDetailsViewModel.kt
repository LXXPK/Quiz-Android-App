package com.example.smartquiz.viewmodel.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartquiz.data.local.entity.quiz.QuizEntity
import com.example.smartquiz.data.repository.quiz.QuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuizDetailsViewModel(
    private val repository: QuizRepository
) : ViewModel() {

    val quiz = MutableStateFlow<QuizEntity?>(null)
    val questionCount = MutableStateFlow(0)

    private val _attemptId = MutableStateFlow<Int?>(null)
    val attemptId = _attemptId.asStateFlow()

    fun loadQuizDetails(quizId: String) {
        viewModelScope.launch {
            quiz.value = repository.getQuizById(quizId)
            questionCount.value = repository.getQuestionCount(quizId)
        }
    }

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

