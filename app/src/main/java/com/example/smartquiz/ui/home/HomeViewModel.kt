

package com.example.smartquiz.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartquiz.data.local.entity.quiz.QuizEntity
import com.example.smartquiz.data.local.session.SessionManager
import com.example.smartquiz.data.repository.home.HomeRepository
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

                // ✅ LOAD INTERESTS
                val interests = repository.getUserInterests(uid)
                    .map { it.interest }

                // ✅ LOAD ALL QUIZZES
                val allQuizzes = repository.getAllQuizzes()

                // ✅ BUILD SUGGESTED (MAX 4)
                val suggested = buildSuggestedQuizzes(
                    allQuizzes = allQuizzes,
                    interests = interests
                )

                // ✅ GROUP ALL QUIZZES BY CATEGORY
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

    // TEMP
    fun getQuizProgress(quizId: String) =
        Triple(
            Date(System.currentTimeMillis() - 3600000),
            Date(System.currentTimeMillis() + 7200000),
            0.3f
        )
}
