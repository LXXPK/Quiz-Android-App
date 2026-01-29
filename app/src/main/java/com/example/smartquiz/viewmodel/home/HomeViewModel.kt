package com.example.smartquiz.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartquiz.data.local.entity.quiz.QuizEntity
import com.example.smartquiz.data.repository.quiz.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
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

    fun getQuizProgress(quiz: QuizEntity): Triple<Date, Date, Float> {
        val activeTime = Date(quiz.startTime)
        val expirationTime = Date(quiz.endTime)


        val progress = 0f

        return Triple(activeTime, expirationTime, progress)
    }

}