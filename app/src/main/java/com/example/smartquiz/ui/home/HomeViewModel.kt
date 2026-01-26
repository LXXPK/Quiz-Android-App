package com.example.smartquiz.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadQuizzes()
    }

    fun loadQuizzes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                // TODO: Fetch quizzes from repository
                // val activeQuizzes = repository.getActiveQuizzes()
                // val suggestedQuizzes = repository.getSuggestedQuizzes()
                // _uiState.update { it.copy(isLoading = false, activeQuizzes = activeQuizzes, suggestedQuizzes = suggestedQuizzes) }
                _uiState.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun getQuizProgress(quizId: String): Triple<Date, Date, Float> {
        // TODO: Fetch quiz progress from repository based on quizId, also change return type
        val activeTime = Date(System.currentTimeMillis() + 3600000) // Dummy Data: active in 1 hour
        val expirationTime = Date(System.currentTimeMillis() + 7200000) // Dummy Data: expires in 2 hours
        val progress = 0.5f
        return Triple(activeTime, expirationTime, progress)
    }
}
