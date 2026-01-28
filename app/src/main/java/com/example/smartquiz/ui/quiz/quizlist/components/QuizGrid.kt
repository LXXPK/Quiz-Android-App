package com.example.smartquiz.ui.quiz.quizlist.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import com.example.smartquiz.data.local.entity.quiz.QuizEntity

@Composable
fun QuizGrid(
    quizzes: List<QuizEntity>,
    onQuizSelected: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(quizzes) { quiz ->
            QuizGridItem(
                quiz = quiz,
                onClick = { onQuizSelected(quiz.quizId) }
            )
        }
    }
}
