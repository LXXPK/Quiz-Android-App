package com.example.smartquiz.ui.quiz.quizresult.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ResultHeader() {
    Text(
        text = "🎉 Quiz Completed!",
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold
    )
}
