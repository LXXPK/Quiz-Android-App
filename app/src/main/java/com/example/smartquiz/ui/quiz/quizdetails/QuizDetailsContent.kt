package com.example.smartquiz.ui.quiz.quizdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.smartquiz.R
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
            .padding(dimensionResource(id = R.dimen.medium_padding)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.medium_padding))
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.medium_padding))
        ) {
            QuizHeaderCard(
                title = quiz?.title ?: stringResource(id = R.string.label_quiz_default),
                category = quiz?.category ?: stringResource(id = R.string.label_not_available),
                questionCount = questionCount
            )
            QuizInfoCard()
            QuizInstructionsCard()
        }

        StartQuizButton(onClick = onStartQuiz)
    }
}
