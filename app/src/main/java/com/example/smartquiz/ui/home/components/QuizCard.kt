package com.example.smartquiz.ui.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.smartquiz.R
import com.example.smartquiz.data.local.entity.quiz.QuizEntity
import com.example.smartquiz.ui.theme.SmartQuizTheme

@Composable
fun QuizCard(handleQuizCardClick: (QuizEntity) -> Unit, quiz: QuizEntity, modifier: Modifier = Modifier) {
    Card(
        onClick = { handleQuizCardClick(quiz) },
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.medium_padding))
        ) {
            Text(
                text = quiz.title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = quiz.category,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
