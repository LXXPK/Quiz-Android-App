package com.example.smartquiz.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartquiz.data.repository.quiz.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val quizRepository: QuizRepository
) : ViewModel() {

    private val _categories =
        MutableStateFlow<List<String>>(emptyList())
    val categories = _categories.asStateFlow()

    fun loadCategories() {
        viewModelScope.launch {
            _categories.value = quizRepository.getCategories()
        }
    }
}