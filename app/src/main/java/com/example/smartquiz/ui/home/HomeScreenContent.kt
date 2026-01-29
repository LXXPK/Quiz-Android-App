

package com.example.smartquiz.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.smartquiz.R
import com.example.smartquiz.data.local.entity.quiz.QuizEntity
import com.example.smartquiz.ui.home.components.*
import com.example.smartquiz.ui.home.HomeViewModel


@Composable
fun HomeScreenContent(
    homeViewModel: HomeViewModel,
    handleQuizCardClick: (QuizEntity) -> Unit,
    onCategoryViewAll: (String) -> Unit,
    onHistoryClick: () -> Unit,
    onSuggestedViewAll: () -> Unit,
    uiState: HomeUiState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            dimensionResource(id = R.dimen.medium_padding)
        )
    ) {

        item {
            HeaderSection(
                userName = uiState.user?.name
                    ?: stringResource(R.string.guest),
                streak = uiState.user?.currentStreak ?: 0,
                onHistoryClick = onHistoryClick
            )
        }

        item {
            HomeBannerCarousel(
                banners = listOf(
                    stringResource(R.string.home_banner_daily_challenge),
                    stringResource(R.string.home_banner_weekly_top),
                    stringResource(R.string.home_banner_boost_streak)
                )
            )
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }

        item {
            when {
                uiState.suggestedQuizzes.isNotEmpty() -> {
                    HorizontalTitledQuizList(
                        title = stringResource(
                            R.string.home_recommended_title
                        ),
                        quizzes = uiState.suggestedQuizzes,
                        onViewAllClick = {
                            onSuggestedViewAll()
                        },
                        onQuizCardClick = handleQuizCardClick,
                        limit = 4
                    )
                }

                uiState.user != null -> {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor =
                                MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(
                                text = stringResource(
                                    R.string.home_personalize_title
                                ),
                                style =
                                    MaterialTheme.typography.titleMedium
                            )
                            Spacer(Modifier.height(6.dp))
                            Text(
                                text = stringResource(
                                    R.string.home_personalize_subtitle
                                ),
                                style =
                                    MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }


        uiState.quizzesByCategory.forEach { (category, quizzes) ->
            item {
                Spacer(modifier = Modifier.height(24.dp))
                CategoryQuizRow(
                    category = category,
                    quizzes = quizzes,
                    onViewAll = { onCategoryViewAll(category) },
                    onQuizClick = handleQuizCardClick
                )
            }
        }

        /* ---------- ACTIVE QUIZZES ---------- */
        if (uiState.activeQuizzes.isNotEmpty()) {
            item {
                Text(
                    text = stringResource(R.string.active_quizzes),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(
                        top = 24.dp,
                        bottom = 12.dp
                    )
                )
            }

            items(uiState.activeQuizzes) { quiz ->
                val (activeTime, expirationTime, progress) =
                    homeViewModel.getQuizProgress(quiz)

                ActiveQuizCard(
                    quiz = quiz,
                    handleQuizCardClick = handleQuizCardClick,
                    activeTime = activeTime,
                    expirationTime = expirationTime,
                    progress = progress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                )
            }

        }
    }
}