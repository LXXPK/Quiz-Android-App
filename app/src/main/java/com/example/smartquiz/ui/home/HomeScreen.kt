package com.example.smartquiz.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartquiz.R
import com.example.smartquiz.data.local.entity.quiz.QuizEntity
import com.example.smartquiz.data.local.entity.user.UserEntity
import com.example.smartquiz.ui.theme.SmartQuizTheme

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel(), handleQuizCardClick: () -> Unit) {
    val homeUiState by homeViewModel.uiState.collectAsState()

    if (homeUiState.isLoading) {
        LoadingScreen()
    } else if (homeUiState.errorMessage != null) {
        ErrorScreen(message = homeUiState.errorMessage!!)
    } else {
        QuizListScreen(handleQuizCardClick = handleQuizCardClick, uiState = homeUiState)
    }
}

@Composable
fun QuizListScreen(handleQuizCardClick: () -> Unit, uiState: HomeUiState, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.medium_padding))
    ) {
        item {
            Text(
                text = "Hello ${uiState.user?.name ?: "Guest"}",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.medium_padding))
            )
        }

        if (uiState.suggestedQuizzes.isNotEmpty()) {
            item {
                Text(
                    text = "Suggested Quizzes",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.small_padding))
                )
            }
            items(uiState.suggestedQuizzes) { quiz ->
                QuizCard(
                    quiz = quiz,
                    handleQuizCardClick = handleQuizCardClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = dimensionResource(id = R.dimen.small_padding))
                )
            }
        }

        if (uiState.activeQuizzes.isNotEmpty()) {
            item {
                Text(
                    text = "Active Quizzes",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(
                        top = dimensionResource(id = R.dimen.medium_padding),
                        bottom = dimensionResource(id = R.dimen.small_padding)
                    )
                )
            }
            items(uiState.activeQuizzes) { quiz ->
                QuizCard(
                    quiz = quiz,
                    handleQuizCardClick = handleQuizCardClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = dimensionResource(id = R.dimen.small_padding))
                )
            }
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(message: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuizListPreview() {
    val sampleQuizzes = listOf(
        QuizEntity("1", "Android Basics", "Tech", true),
        QuizEntity("2", "Kotlin Coroutines", "Tech", false)
    )
    SmartQuizTheme {
        QuizListScreen(
            uiState = HomeUiState(
                user = UserEntity("1", "John Doe", "john@example.com", null),
                suggestedQuizzes = sampleQuizzes,
                activeQuizzes = sampleQuizzes.take(1)
            ),
            handleQuizCardClick = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    SmartQuizTheme {
        LoadingScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    SmartQuizTheme {
        ErrorScreen(message = "Something went wrong!")
    }
}
