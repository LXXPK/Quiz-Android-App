package com.example.smartquiz.ui.quiz.quizdetails.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.smartquiz.R

@Composable
fun QuizHeaderCard(
    title: String,
    category: String,
    questionCount: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.button_corner_radius_medium)),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.card_elevation_medium))
    ) {
        Column(Modifier.padding(dimensionResource(id = R.dimen.medium_padding))) {
            Text(text = title, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(dimensionResource(id = R.dimen.spacing_result_stat_internal)))
            Text(text = stringResource(id = R.string.label_category_format, category))
            Spacer(Modifier.height(dimensionResource(id = R.dimen.extra_small_padding)))
            Text(text = stringResource(id = R.string.label_total_questions_format, questionCount))
        }
    }
}
