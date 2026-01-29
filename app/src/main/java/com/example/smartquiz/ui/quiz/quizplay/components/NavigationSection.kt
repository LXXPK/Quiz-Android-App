package com.example.smartquiz.ui.quiz.quizplay.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartquiz.viewmodel.quiz.QuizPlayViewModel

@Composable
fun NavigationSection(viewModel: QuizPlayViewModel) {
    val index by viewModel.currentIndex.collectAsState()
    val questions by viewModel.questions.collectAsState()

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedButton(
            enabled = index > 0,
            onClick = viewModel::previousQuestion
        ) {
            Text("Previous")
        }

        Button(
            enabled = index < questions.lastIndex,
            onClick = viewModel::nextQuestion
        ) {
            Text("Next")
        }
    }
}
