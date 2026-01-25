package com.example.smartquiz.ui.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartquiz.viewmodel.quiz.QuizPlayViewModel

@Composable
fun QuizPlayScreen(
    quizId: String,
    attemptId: Int,
    viewModel: QuizPlayViewModel
) {
    val questions by viewModel.questions.collectAsState()
    val options by viewModel.options.collectAsState()
    val index by viewModel.currentIndex.collectAsState()
    val answers by viewModel.answers.collectAsState()
    val elapsedTimeText by viewModel.elapsedTimeText.collectAsState()
    val isDummy by viewModel.isDummyMode.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadQuiz(quizId)
    }

    if (questions.isEmpty()) {
        Text(
            text = "Loading quiz questions...",
            modifier = Modifier.padding(16.dp)
        )
        return
    }

    if (index !in questions.indices) {
        Text(
            text = "Invalid question index",
            modifier = Modifier.padding(16.dp)
        )
        return
    }

    val question = questions[index]
    val selectedOptionId = answers[question.questionId]

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        // Dummy mode label
        if (isDummy) {
            Text(
                text = "Dummy Quiz Mode",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // 🔝 Header: progress + timer
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Question ${index + 1} of ${questions.size}",
                style = MaterialTheme.typography.labelMedium
            )

            // ⏱️ Elapsed time (already formatted by ViewModel)
            Text(
                text = elapsedTimeText,
                style = MaterialTheme.typography.labelMedium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Question text
        Text(
            text = question.questionText,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Options
        options.forEach { option ->
            val selected = option.optionId == selectedOptionId

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                onClick = {
                    viewModel.selectOption(
                        question.questionId,
                        option.optionId
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor =
                        if (selected)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(option.optionText)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Navigation
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Button(
                enabled = index > 0,
                onClick = { viewModel.previousQuestion() }
            ) {
                Text("Previous")
            }

            if (index == questions.lastIndex) {
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