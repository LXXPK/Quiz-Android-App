package com.example.smartquiz.ui.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smartquiz.viewmodel.quiz.QuizPlayViewModel

/**
 * QuizPlayScreen
 *
 * Responsibilities:
 * - Display questions and options
 * - Show progress and elapsed time
 * - Forward user actions to ViewModel
 *
 * This screen does NOT:
 * - Calculate score
 * - Store answers
 * - Handle timing logic
 */
@Composable
fun QuizPlayScreen(
    quizId: String,
    attemptId: Int,
    viewModel: QuizPlayViewModel = hiltViewModel()
) {

    // Load quiz once when screen starts
    LaunchedEffect(quizId) {
        viewModel.loadQuiz(quizId)
    }

    // Observe ViewModel state
    val questions by viewModel.questions.collectAsState()
    val options by viewModel.options.collectAsState()
    val currentIndex by viewModel.currentIndex.collectAsState()
    val answers by viewModel.answers.collectAsState()
    val elapsedTime by viewModel.elapsedTimeText.collectAsState()

    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isLoading) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
        return
    }

    uiState.errorMessage?.let {
        Box(Modifier.fillMaxSize()) {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        return
    }

    // Basic loading state
    if (questions.isEmpty()) {
        Box(modifier = Modifier.padding(16.dp)) {
            Text("Loading quiz...")
        }
        return
    }

    val question = questions[currentIndex]
    val selectedOptionId = answers[question.questionId]

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        // Header: progress + timer
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Question ${currentIndex + 1} of ${questions.size}",
                style = MaterialTheme.typography.labelMedium
            )

            Text(
                text = elapsedTime,
                style = MaterialTheme.typography.labelMedium
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Question text
        Text(
            text = question.questionText,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Options
        options.forEach { option ->
            val isSelected = option.optionId == selectedOptionId

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                onClick = {
                    viewModel.selectOption(
                        questionId = question.questionId,
                        optionId = option.optionId
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor =
                        if (isSelected)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(option.optionText)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Navigation buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Button(
                enabled = currentIndex > 0,
                onClick = { viewModel.previousQuestion() }
            ) {
                Text("Previous")
            }

            if (currentIndex == questions.lastIndex) {
                Button(
                    onClick = { viewModel.submitQuiz(attemptId) }
                ) {
                    Text("Submit")
                }
            } else {
                Button(
                    onClick = { viewModel.nextQuestion() }
                ) {
                    Text("Next")
                }
            }
        }
    }
}