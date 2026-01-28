package com.example.smartquiz.ui.quiz.quizdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartquiz.data.local.entity.quiz.QuizEntity
import com.example.smartquiz.ui.quiz.quizdetails.components.*

@Composable
fun QuizDetailsContent(
    quiz: QuizEntity?,
    questionCount: Int,
    onStartQuiz: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
    ) {




        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            QuizHeaderCard(
                title = quiz?.title ?: "Quiz",
                category = quiz?.category ?: "-",
                questionCount = questionCount
            )
            QuizInfoCard()
            QuizInstructionsCard()
        }



        StartQuizButton(onClick = onStartQuiz)
    }
}
