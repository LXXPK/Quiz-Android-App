package com.example.smartquiz.viewmodel.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartquiz.data.repository.quiz.QuizRepository


// Without hilt
class QuizPlayViewModelFactory(
    private val repository: QuizRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizPlayViewModel::class.java)) {
            return QuizPlayViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}