package com.example.smartquiz.ui.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartquiz.data.local.entity.quiz.QuizEntity

@Composable
fun CategoryQuizRow(
    category: String,
    quizzes: List<QuizEntity>,
    onViewAll: () -> Unit,
    onQuizClick: (QuizEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = category,
                style = MaterialTheme.typography.titleLarge
            )
            TextButton(onClick = onViewAll) {
                Text("View All")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(quizzes.take(6)) { quiz ->
                QuizCard(
                    quiz = quiz,
                    handleQuizCardClick = onQuizClick
                )
            }
        }
    }
}
