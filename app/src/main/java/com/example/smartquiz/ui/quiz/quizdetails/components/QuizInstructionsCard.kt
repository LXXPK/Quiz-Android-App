package com.example.smartquiz.ui.quiz.quizdetails.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.smartquiz.R
import androidx.compose.ui.Alignment


@Composable
fun QuizInstructionsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(26.dp)
                )
                Text(
                    text = stringResource(R.string.quiz_instructions_title),
                    style = MaterialTheme.typography.titleLarge
                )
            }

            InstructionItem(stringResource(R.string.quiz_instruction_equal_marks))
            InstructionItem(stringResource(R.string.quiz_instruction_navigation))
            InstructionItem(stringResource(R.string.quiz_instruction_timer_start))
            InstructionItem(stringResource(R.string.quiz_instruction_auto_submit))
            InstructionItem(stringResource(R.string.quiz_instruction_no_change))
        }
    }
}

@Composable
private fun InstructionItem(text: String) {
    Text(
        text = "$text",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.2
    )
}
