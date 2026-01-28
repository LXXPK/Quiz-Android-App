package com.example.smartquiz.ui.quiz

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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

        /* ---------- HEADER ---------- */

        Text(
            text = "Category: $category",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(Modifier.height(12.dp))

        /* ---------- EMPTY STATE ---------- */

        if (quizzes.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No quizzes available",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {

            /* ---------- GRID LIST ---------- */

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),   // 2 column grid
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(quizzes) { quiz ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .clickable { onQuizSelected(quiz.quizId) },
                        shape = MaterialTheme.shapes.medium,
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {

                            /* ---------- ICON + TITLE ---------- */

                            Column {
                                Icon(
                                    imageVector = Icons.Outlined.Assignment,
                                    contentDescription = "Quiz",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(28.dp)
                                )

                                Spacer(Modifier.height(8.dp))

                                Text(
                                    text = quiz.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    maxLines = 2
                                )
                            }

                            /* ---------- FOOTER ---------- */

                            Text(
                                text = quiz.category,
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}
