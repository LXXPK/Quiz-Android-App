package com.example.smartquiz.ui.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartquiz.R
import com.example.smartquiz.data.local.entity.quiz.QuizAttemptEntity
import com.example.smartquiz.ui.theme.SmartQuizTheme

@Composable
fun QuizHistoryScreen(
    modifier: Modifier = Modifier,
    userId: String,
    viewModel: QuizHistoryViewModel = viewModel()
) {
//    viewModel.loadQuizHistory("userId")
    viewModel.loadQuizHistory("test")
    val uiState by viewModel.uiState.collectAsState()
    QuizHistoryScreenContent(uiState = uiState, modifier = modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizHistoryScreenContent(
    uiState: QuizHistoryUiState,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.quiz_history_title)) }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                uiState.errorMessage != null -> {
                    Text(
                        text = uiState.errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                uiState.attempts.isEmpty() -> {
                    Text(
                        text = stringResource(id = R.string.no_history_available),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(dimensionResource(id = R.dimen.medium_padding)),
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_padding))
                    ) {
                        item {
                            StatsSection(uiState)
                        }
                        items(uiState.attempts) { attempt ->
                            QuizHistoryItem(
                                attempt = attempt
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatsSection(uiState: QuizHistoryUiState) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = dimensionResource(id = R.dimen.small_padding)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.medium_padding)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_padding))
        ) {
            Text(
                // TODO: maybe use the user name
                text = stringResource(id = R.string.statistics_title),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatItem(
                    label = stringResource(id = R.string.average_score_label),
                    value = "%.1f%%".format(uiState.averageScore)
                )
                StatItem(
                    label = stringResource(id = R.string.total_quizzes_label),
                    value = uiState.totalQuizzes.toString()
                )
                StatItem(
                    label = stringResource(id = R.string.highest_score_label),
                    value = "${uiState.highestScore}%"
                )
            }
        }
    }
}

@Composable
private fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuizHistoryScreenLoadingPreview() {
    SmartQuizTheme {
        QuizHistoryScreenContent(
            uiState = QuizHistoryUiState(isLoading = true)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuizHistoryScreenEmptyPreview() {
    SmartQuizTheme {
        QuizHistoryScreenContent(
            uiState = QuizHistoryUiState(attempts = emptyList())
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuizHistoryScreenDataPreview() {
    val dummyHistory = listOf(
        QuizAttemptEntity(1, "android_basics", "user1", 80, System.currentTimeMillis() - 86400000),
        QuizAttemptEntity(2, "kotlin_advanced", "user1", 95, System.currentTimeMillis() - 172800000),
        QuizAttemptEntity(3, "compose_ui", "user1", 70, System.currentTimeMillis() - 259200000)
    )
    SmartQuizTheme {
        QuizHistoryScreenContent(
            uiState = QuizHistoryUiState(
                attempts = dummyHistory,
                averageScore = 81.6,
                totalQuizzes = 3,
                highestScore = 95
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuizHistoryScreenErrorPreview() {
    SmartQuizTheme {
        QuizHistoryScreenContent(
            uiState = QuizHistoryUiState(errorMessage = "Failed to load history")
        )
    }
}
