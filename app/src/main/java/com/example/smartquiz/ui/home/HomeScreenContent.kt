package com.example.smartquiz.ui.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartquiz.R
import com.example.smartquiz.data.local.entity.quiz.QuizEntity
import com.example.smartquiz.data.local.entity.user.UserEntity
import com.example.smartquiz.ui.home.components.ActiveQuizCard
import com.example.smartquiz.ui.home.components.HeaderSection
import com.example.smartquiz.ui.home.components.HorizontalTitledQuizList
import com.example.smartquiz.ui.theme.SmartQuizTheme

@Composable
fun HomeScreenContent(
    homeViewModel: HomeViewModel,
    handleQuizCardClick: (QuizEntity) -> Unit,
    uiState: HomeUiState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.medium_padding))
    ) {
        item {
            HeaderSection(
                userName = uiState.user?.name ?: stringResource(R.string.guest),
                // TODO: use view model to get streak
                streak = uiState.user?.currentStreak ?: 0
            )
        }

        item {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.medium_padding)))
        }

        if (uiState.suggestedQuizzes.isNotEmpty()) {
            item {
                HorizontalTitledQuizList(
                    title = stringResource(R.string.suggested_quizzes),
                    quizzes = uiState.suggestedQuizzes,
                    onViewAllClick = { /*TODO*/ },
                    onQuizCardClick = handleQuizCardClick,
                    limit = 5
                )
            }
        }

        if (uiState.activeQuizzes.isNotEmpty()) {
            item {
                Text(
                    text = stringResource(R.string.active_quizzes),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(
                        top = dimensionResource(id = R.dimen.medium_padding),
                        bottom = dimensionResource(id = R.dimen.small_padding)
                    )
                )
            }
            items(uiState.activeQuizzes) { quiz ->
                val (activeTime, expirationTime, progress) = homeViewModel.getQuizProgress(quiz.quizId)

                ActiveQuizCard(
                    quiz = quiz,
                    handleQuizCardClick = handleQuizCardClick,
                    activeTime = activeTime,
                    expirationTime = expirationTime,
                    progress = progress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = dimensionResource(id = R.dimen.small_padding))
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenContentPreview() {
    val suggestedQuizzes = listOf(
        QuizEntity("1", "Android Basics", "Technology", true),
        QuizEntity("2", "Kotlin Coroutines", "Technology", true),
        QuizEntity("3", "Jetpack Compose UI", "Design", true),
        QuizEntity("4", "Material 3 Design", "Design", true),
        QuizEntity("8", "Advanced Networking", "Tech", true),
        QuizEntity("9", "Unit Testing", "Tech", true),
        QuizEntity("10", "Dagger Hilt Basics", "Tech", true),
        QuizEntity("13", "KMM Introduction", "Mobile", true),
        QuizEntity("14", "Deep Learning basics", "AI", true)
    )
    val activeQuizzes = listOf(
        QuizEntity("5", "General Knowledge", "General", true),
        QuizEntity("6", "History of Art", "History", true),
        QuizEntity("7", "Android Performance", "Tech", true),
        QuizEntity("11", "SQL Fundamentals", "Database", true),
        QuizEntity("12", "World Geography", "General", true),
        QuizEntity("15", "Space Exploration", "Science", true),
        QuizEntity("16", "Ancient Civilizations", "History", true)
    )
    SmartQuizTheme {
        HomeScreenContent(
            homeViewModel = viewModel(),
            uiState = HomeUiState(
                user = UserEntity("1", "John Doe", "john@example.com", null, 5, 0),
                suggestedQuizzes = suggestedQuizzes,
                activeQuizzes = activeQuizzes
            ),
            handleQuizCardClick = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}
