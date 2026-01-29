package com.example.smartquiz.ui.quiz.quizlist

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartquiz.data.local.entity.quiz.QuizEntity
import com.example.smartquiz.ui.quiz.quizlist.components.*

@Composable
fun QuizListContent(
    category: String,
    quizzes: List<QuizEntity>,
    onQuizSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {

        Text(
            text = "Category: $category",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(Modifier.height(12.dp))

        if (quizzes.isEmpty()) {
            EmptyQuizState()
        } else {
            QuizGrid(
                quizzes = quizzes,
                onQuizSelected = onQuizSelected
            )
        }
    }
}
