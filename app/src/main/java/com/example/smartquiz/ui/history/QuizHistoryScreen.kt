package com.example.smartquiz.ui.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Score
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smartquiz.R
import com.example.smartquiz.data.local.entity.quiz.QuizAttemptEntity
import com.example.smartquiz.viewmodel.history.QuizHistoryViewModel
import java.text.SimpleDateFormat
import java.util.*
import com.example.smartquiz.ui.history.components.QuizGraphsSection


@Composable
fun QuizHistoryScreen(
    viewModel: QuizHistoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showInsights by remember { mutableStateOf(true) }


    LaunchedEffect(Unit) {
        viewModel.loadHistory()
    }

    Scaffold(

    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                uiState.errorMessage != null -> {
                    Text(
                        text = uiState.errorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                uiState.attempts.isEmpty() -> {
                    EmptyHistoryState()
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(dimensionResource(id = R.dimen.medium_padding)),
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.medium_padding))
                    ) {


                        item {
                            HistoryStatsSection(uiState)
                        }


                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                                ),
                                onClick = { showInsights = !showInsights }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(dimensionResource(id = R.dimen.medium_padding)),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.graph_performance_insights),
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.SemiBold
                                    )

                                    Icon(
                                        imageVector = Icons.Outlined.BarChart,
                                        // TODO: Check this. Toggle insight is lost
                                        contentDescription = stringResource(id = R.string.view_all),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }


                        if (showInsights) {
                            item {
                                QuizGraphsSection(
                                    attempts = uiState.attempts,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }


                        item {
                            Text(
                                text = stringResource(id = R.string.quiz_history_title),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(top = dimensionResource(id = R.dimen.small_padding))
                            )
                        }


                        items(uiState.attempts) { attempt ->
                            HistoryItemCard(attempt)
                        }
                    }

                }
            }
        }
    }
}



@Composable
private fun HistoryStatsSection(uiState: QuizHistoryUiState) {
    Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_small))) {

        Text(
            text = stringResource(id = R.string.history_title_performance),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_small))
        ) {
            StatCard(
                title = stringResource(id = R.string.history_stat_quizzes),
                value = uiState.totalQuizzes.toString(),
                icon = Icons.Outlined.History,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f)
            )

            StatCard(
                title = stringResource(id = R.string.history_stat_avg_score),
                value = "${"%.1f".format(uiState.averageScore)}%",
                icon = Icons.Outlined.BarChart,
                color = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.weight(1f)
            )

            StatCard(
                title = stringResource(id = R.string.history_stat_best),
                value = "${uiState.highestScore}%",
                icon = Icons.Outlined.EmojiEvents,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier : Modifier = Modifier

) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.12f)
        )
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.medium_padding)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.extra_small_padding))
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = color
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}



@Composable
private fun HistoryItemCard(attempt: QuizAttemptEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.card_elevation_small))
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.medium_padding)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_padding))
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = attempt.quizId,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.Score,
                        contentDescription = stringResource(id = R.string.history_stat_score),
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(dimensionResource(id = R.dimen.icon_small))
                    )
                    Spacer(Modifier.width(dimensionResource(id = R.dimen.extra_small_padding)))
                    val maxScore = 50 // or derive dynamically later
                    val percentage = (attempt.score * 100) / maxScore
                    Text(
                        text = "${percentage}%",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Text(
                text = formatDate(attempt.attemptedAt),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


@Composable
private fun EmptyHistoryState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.spacing_xlarge)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.History,
            contentDescription = null,
            modifier = Modifier.size(dimensionResource(id = R.dimen.icon_large)),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
        Text(
            text = stringResource(id = R.string.history_empty_title),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = stringResource(id = R.string.history_empty_subtitle),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}



private fun formatDate(time: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
    return sdf.format(Date(time))
}
