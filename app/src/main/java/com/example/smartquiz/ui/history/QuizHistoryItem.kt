package com.example.smartquiz.ui.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.smartquiz.R
import com.example.smartquiz.data.local.entity.quiz.QuizAttemptEntity
import com.example.smartquiz.ui.theme.SmartQuizTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun QuizHistoryItem(
    attempt: QuizAttemptEntity,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.medium_padding))
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(id = R.string.quiz_id_label, attempt.quizId),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = stringResource(id = R.string.score_format, attempt.score),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Text(
                text = stringResource(id = R.string.date_label, formatTimestamp(attempt.attemptedAt)),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

private fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

@Preview(showBackground = true)
@Composable
fun QuizHistoryItemPreview() {
    SmartQuizTheme {
        QuizHistoryItem(
            attempt = QuizAttemptEntity(
                attemptId = 1,
                quizId = "android_basics",
                userId = "user1",
                score = 85,
                attemptedAt = System.currentTimeMillis()
            ),
            modifier = Modifier.padding(dimensionResource(id = R.dimen.medium_padding))
        )
    }
}