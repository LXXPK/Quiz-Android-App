package com.example.smartquiz.ui.quiz.quizlist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smartquiz.viewmodel.quiz.QuizListViewModel

@Composable
fun QuizListScreen(
    category: String,
    onQuizSelected: (String) -> Unit,
    viewModel: QuizListViewModel = hiltViewModel()
) {
    val quizzes by viewModel.quizzes.collectAsState()

    LaunchedEffect(category) {
        viewModel.loadQuizzes(category)
    }

    QuizListContent(
        category = category,
        quizzes = quizzes,
        onQuizSelected = onQuizSelected,
        modifier = Modifier.fillMaxSize()
    )
}
