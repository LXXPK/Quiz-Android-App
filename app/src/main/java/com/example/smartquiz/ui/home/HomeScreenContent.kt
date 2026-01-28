

package com.example.smartquiz.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

@Composable
fun HomeScreenContent(
    homeViewModel: HomeViewModel,
    handleQuizCardClick: (QuizEntity) -> Unit,
    onCategoryViewAll: (String) -> Unit,
    onHistoryClick: () -> Unit,
    onProfileClick: () -> Unit,
    uiState: HomeUiState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.medium_padding))
    ) {

        /* ---------- HEADER ---------- */
        item {
            HeaderSection(
                userName = uiState.user?.name ?: stringResource(R.string.guest),
                streak = uiState.user?.currentStreak ?: 0,
                onHistoryClick = onHistoryClick,
                onProfileClick = onProfileClick
            )
        }

        /* ---------- BANNER ---------- */
        item {
            HomeBannerCarousel(
                banners = listOf(
                    "🔥 Daily Challenge",
                    "🏆 Weekly Top Quiz",
                    "⚡ Boost Your Streak"
                )
            )
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }

        /* ---------- SUGGESTED QUIZZES ---------- */
        if (uiState.suggestedQuizzes.isNotEmpty()) {
            item {
                HorizontalTitledQuizList(
                    title = stringResource(R.string.suggested_quizzes),
                    quizzes = uiState.suggestedQuizzes,
                    onViewAllClick = {},
                    onQuizCardClick = handleQuizCardClick,
                    limit = 5
                )
            }
        }

        /* ---------- CATEGORY SECTIONS ---------- */
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
                    modifier = Modifier.padding(top = 24.dp, bottom = 12.dp)
                )
            }

            items(uiState.activeQuizzes) { quiz ->
                val (activeTime, expirationTime, progress) =
                    homeViewModel.getQuizProgress(quiz.quizId)

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
