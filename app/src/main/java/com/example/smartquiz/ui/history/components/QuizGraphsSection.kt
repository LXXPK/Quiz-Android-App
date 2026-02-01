package com.example.smartquiz.ui.history.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smartquiz.R
import com.example.smartquiz.data.local.entity.quiz.QuizAttemptEntity
import com.example.smartquiz.ui.theme.SmartQuizTheme

@Composable
fun QuizGraphsSection(
    attempts: List<QuizAttemptEntity>,
    modifier: Modifier = Modifier
) {
    if (attempts.isEmpty()) return

    Column(
        modifier = modifier.padding(vertical = dimensionResource(id = R.dimen.medium_padding)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.large_padding))
    ) {
        Text(
            text = stringResource(id = R.string.graph_performance_insights),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.small_padding))
        )


        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.medium_padding))) {
                Text(
                    text = stringResource(id = R.string.graph_score_trend),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.small_padding))
                )
                QuizProgressLineChart(attempts = attempts)
            }
        }


        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.medium_padding))) {
                Text(
                    text = stringResource(id = R.string.graph_recent_performance),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.small_padding))
                )
                QuizRecentBarChart(attempts = attempts)
            }
        }


        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.medium_padding))) {
                Text(
                    text = stringResource(id = R.string.graph_skill_distribution),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.small_padding))
                )
                QuizRadarChart(attempts = attempts)
            }
        }


        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.medium_padding))) {
                Text(
                    text = stringResource(id = R.string.graph_quiz_participation),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.small_padding))
                )
                QuizDistributionPieChart(attempts = attempts)
            }
        }
    }
}
// TODO: Dont want this possibly
@Preview(showBackground = true)
@Composable
fun QuizGraphsSectionPreview() {
    val dummyAttempts = listOf(
        QuizAttemptEntity(1, "android_basics", "user1", 80, System.currentTimeMillis() - 86400000 * 3),
        QuizAttemptEntity(2, "kotlin_advanced", "user1", 95, System.currentTimeMillis() - 86400000 * 2),
        QuizAttemptEntity(3, "compose_ui", "user1", 70, System.currentTimeMillis() - 86400000 * 1),
        QuizAttemptEntity(4, "hilt_di", "user1", 85, System.currentTimeMillis()),
        QuizAttemptEntity(5, "room_db", "user1", 60, System.currentTimeMillis()),
        QuizAttemptEntity(6, "unit_testing", "user1", 90, System.currentTimeMillis())
    )
    SmartQuizTheme {
        QuizGraphsSection(attempts = dummyAttempts)
    }
}
