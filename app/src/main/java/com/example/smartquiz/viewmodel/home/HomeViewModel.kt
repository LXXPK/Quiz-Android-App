package com.example.smartquiz.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartquiz.data.local.entity.quiz.QuizEntity
import com.example.smartquiz.data.local.session.SessionManager
import com.example.smartquiz.data.repository.home.HomeRepository
import com.example.smartquiz.ui.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun loadHome() {
        val uid = sessionManager.getUid()
        if (uid == null) {
            _uiState.update {
                it.copy(errorMessage = "User not logged in")
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val user = repository.getUser(uid)


                val interests = repository.getUserInterests(uid)
                    .map { it.interest }


                val allQuizzes = repository.getAllQuizzes()


                val suggested = buildSuggestedQuizzes(
                    allQuizzes = allQuizzes,
                    interests = interests
                )


                val groupedByCategory = allQuizzes.groupBy { it.category }

                val active = repository.getActiveQuizzes()

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        user = user,
                        suggestedQuizzes = suggested,
                        quizzesByCategory = groupedByCategory,
                        activeQuizzes = active
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Something went wrong"
                    )
                }
            }
        }
    }

    private fun buildSuggestedQuizzes(
        allQuizzes: List<QuizEntity>,
        interests: List<String>
    ): List<QuizEntity> {
        if (interests.isEmpty()) return emptyList()

        return allQuizzes
            .filter { quiz ->
                interests.any { it.equals(quiz.category, ignoreCase = true) }
            }
            .shuffled()
            .take(4)
    }


    fun getQuizProgress(quiz: QuizEntity): Triple<Date, Date, Float> {
        val activeTime = Date(quiz.startTime)
        val expirationTime = Date(quiz.endTime)


        val progress = 0f

        return Triple(activeTime, expirationTime, progress)
    }
}