package com.example.smartquiz.ui.quiz.quizlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.smartquiz.R
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
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.medium_padding))
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            text = stringResource(id = R.string.label_category_format, category),
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(Modifier.height(dimensionResource(id = R.dimen.spacing_small)))

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
