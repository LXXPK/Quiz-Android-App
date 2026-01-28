package com.example.smartquiz.ui.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.smartquiz.navigation.Routes
import com.example.smartquiz.viewmodel.quiz.QuizDetailsViewModel

@Composable
fun QuizDetailsScreen(
    quizId: String,
    userId: String,
    navController: NavController,
    viewModel: QuizDetailsViewModel = hiltViewModel()
) {

    LaunchedEffect(quizId) {
        viewModel.loadQuizDetails(quizId)
    }

    val quiz by viewModel.quiz.collectAsState()
    val questionCount by viewModel.questionCount.collectAsState()
    val attemptId by viewModel.attemptId.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(attemptId) {
        attemptId?.let {
            navController.navigate(
                Routes.quizPlay(quizId, it)
            )
            viewModel.consumeAttemptId()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

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

            else -> {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    /* ---------- QUIZ HEADER CARD ---------- */

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        Column(Modifier.padding(16.dp)) {

                            Text(
                                text = quiz?.title ?: "Quiz",
                                style = MaterialTheme.typography.titleLarge
                            )

                            Spacer(Modifier.height(6.dp))

                            Text(
                                text = "Category: ${quiz?.category ?: "-"}",
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Spacer(Modifier.height(4.dp))

                            Text(
                                text = "Total Questions: $questionCount",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    /* ---------- TIME & SCORE INFO ---------- */

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector = Icons.Outlined.Timer,
                                contentDescription = "Time",
                                tint = MaterialTheme.colorScheme.primary
                            )

                            Spacer(Modifier.width(12.dp))

                            Column {
                                Text("Time Limit", style = MaterialTheme.typography.titleSmall)
                                Text("2 Minutes (Auto submit on timeout)")
                            }
                        }
                    }

                    /* ---------- RULES & INSTRUCTIONS ---------- */

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Outlined.Info,
                                    contentDescription = "Rules",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    text = "Instructions",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }

                            Text("• Each question carries equal marks.")
                            Text("• You can navigate between questions freely.")
                            Text("• Attempt as many questions as possible.")
                            Text("• Timer will start immediately after clicking Start.")
                            Text("• Quiz will be auto-submitted when time ends.")
                            Text("• Once submitted, answers cannot be changed.")
                        }
                    }

                    Spacer(Modifier.weight(1f))

                    /* ---------- START BUTTON ---------- */

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        shape = RoundedCornerShape(12.dp),
                        onClick = {
                            viewModel.onStartQuizClicked(
                                quizId = quizId,
                                userId = userId
                            )
                        }
                    ) {
                        Text(
                            text = "Start Quiz",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}
