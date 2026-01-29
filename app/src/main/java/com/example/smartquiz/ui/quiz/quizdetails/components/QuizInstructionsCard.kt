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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.smartquiz.R

@Composable
fun QuizInstructionsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement =
                androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
        ) {
            Row {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = null
                )
                Spacer(Modifier.width(8.dp))
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
