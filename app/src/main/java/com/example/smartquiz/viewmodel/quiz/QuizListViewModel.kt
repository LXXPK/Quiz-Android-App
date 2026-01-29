package com.example.smartquiz.viewmodel.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartquiz.data.local.entity.quiz.QuizEntity
import com.example.smartquiz.data.local.session.SessionManager
import com.example.smartquiz.data.repository.home.HomeRepository
import com.example.smartquiz.data.repository.quiz.QuizRepository
import com.example.smartquiz.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizListViewModel @Inject constructor(
    private val repository: QuizRepository,
    private val homeRepository: HomeRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _quizzes = MutableStateFlow<List<QuizEntity>>(emptyList())
    val quizzes = _quizzes.asStateFlow()
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()


    fun loadQuizzes(category: String) {
        viewModelScope.launch {
            _uiState.value = UiState(isLoading = true)
            try {
                _quizzes.value =
                    repository.getQuizzesByCategory(category)
                _uiState.value = UiState()
            } catch (e: Exception) {
                _uiState.value = UiState(
                    errorMessage = "Failed to load quizzes"
                )
            }
        }
    }

    fun loadSuggestedQuizzes() {
        viewModelScope.launch {
            _uiState.value = UiState(isLoading = true)

            try {
                val userId = sessionManager.getUid()
                    ?: throw IllegalStateException("User not logged in")

                val interests = homeRepository
                    .getUserInterests(userId)
                    .map { it.interest }

                val suggestedQuizzes = mutableListOf<QuizEntity>()

                interests.forEach { interest ->
                    suggestedQuizzes +=
                        repository.getQuizzesByCategory(interest)
                }

                _quizzes.value = suggestedQuizzes.distinctBy {
                    it.quizId
                }

                _uiState.value = UiState()

            } catch (e: Exception) {
                _uiState.value = UiState(
                    errorMessage = "Failed to load suggested quizzes"
                )
            }
        }
    }



}
