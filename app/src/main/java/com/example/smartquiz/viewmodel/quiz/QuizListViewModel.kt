package com.example.smartquiz.viewmodel.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartquiz.data.local.entity.quiz.QuizEntity
import com.example.smartquiz.data.repository.quiz.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizListViewModel @Inject constructor(
    private val repository: QuizRepository
) : ViewModel() {

    private val _quizzes = MutableStateFlow<List<QuizEntity>>(emptyList())
    val quizzes = _quizzes.asStateFlow()

    fun loadQuizzes(category: String) {
        viewModelScope.launch {
            _quizzes.value =
                repository.getQuizzesByCategory(category)
        }
    }
}
