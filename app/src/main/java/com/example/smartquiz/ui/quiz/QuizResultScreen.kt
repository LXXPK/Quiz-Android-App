package com.example.smartquiz.ui.quiz

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.smartquiz.viewmodel.quiz.QuizPlayViewModel

@Composable
fun QuizResultScreen(
    viewModel: QuizPlayViewModel
) {
    val score by viewModel.score.collectAsState()
    val percentage by viewModel.percentage.collectAsState()
    val correct by viewModel.correctCount.collectAsState()
    val total = viewModel.questions.collectAsState().value.size

    Column {
        Text("Quiz Submitted")
        Text("Score: $score")
        Text("Correct: $correct / $total")
        Text("Percentage: $percentage%")
    }
}