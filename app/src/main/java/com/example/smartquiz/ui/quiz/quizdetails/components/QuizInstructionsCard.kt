package com.example.smartquiz.ui.quiz.quizdetails.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.smartquiz.R

@Composable
fun QuizInstructionsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.button_corner_radius_medium))
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.medium_padding)),
            verticalArrangement =
                androidx.compose.foundation.layout.Arrangement.spacedBy(dimensionResource(id = R.dimen.small_padding))
        ) {
            Row {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = null
                )
                Spacer(Modifier.width(dimensionResource(id = R.dimen.small_padding)))
                Text(
                    text = stringResource(R.string.quiz_instructions_title),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Text(stringResource(R.string.quiz_instruction_equal_marks))
            Text(stringResource(R.string.quiz_instruction_navigation))
            Text(stringResource(R.string.quiz_instruction_timer_start))
            Text(stringResource(R.string.quiz_instruction_auto_submit))
            Text(stringResource(R.string.quiz_instruction_no_change))
        }
    }
}
