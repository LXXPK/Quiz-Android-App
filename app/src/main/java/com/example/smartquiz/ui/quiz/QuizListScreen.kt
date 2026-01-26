package com.example.smartquiz.ui.quiz

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smartquiz.viewmodel.quiz.QuizListViewModel

@Composable
fun QuizListScreen(
    category: String,
    viewModel: QuizListViewModel = hiltViewModel(),
    onQuizSelected: (String) -> Unit
) {
    val quizzes by viewModel.quizzes.collectAsState()

    LaunchedEffect(category) {
        viewModel.loadQuizzes(category)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Category: $category",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(Modifier.height(12.dp))

        if (quizzes.isEmpty()) {
            Text("No quizzes available")
        } else {
            quizzes.forEach { quiz ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { onQuizSelected(quiz.quizId) }
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            text = quiz.title,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(quiz.category)
                    }
                }
            }
        }
    }
}
