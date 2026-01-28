

package com.example.smartquiz.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartquiz.data.local.session.SessionManager
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
                val suggested = repository.getSuggestedQuizzes()
                val active = repository.getActiveQuizzes()

                val groupedByCategory = suggested.groupBy { it.category }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        user = user,
                        suggestedQuizzes = suggested,
                        activeQuizzes = active,
                        quizzesByCategory = groupedByCategory
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

    // TEMP — replace with real attempt logic later
    fun getQuizProgress(quizId: String): Triple<Date, Date, Float> {
        val activeTime = Date(System.currentTimeMillis() - 3600000)
        val expirationTime = Date(System.currentTimeMillis() + 7200000)
        val progress = 0.3f
        return Triple(activeTime, expirationTime, progress)
    }
}
