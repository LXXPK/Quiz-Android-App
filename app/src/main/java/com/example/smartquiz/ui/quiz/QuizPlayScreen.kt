package com.example.smartquiz.ui.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartquiz.viewmodel.quiz.QuizPlayViewModel

@Composable
fun QuizPlayScreen(
    quizId: String,
    attemptId: Int,
    onFinish: () -> Unit,
    viewModel: QuizPlayViewModel
) {


    LaunchedEffect(attemptId) {
        viewModel.loadQuiz(quizId)
    }

    val questions by viewModel.questions.collectAsState()
    val options by viewModel.options.collectAsState()
    val currentIndex by viewModel.currentIndex.collectAsState()
    val answers by viewModel.answers.collectAsState()
    val elapsedTime by viewModel.elapsedTimeText.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isLoading) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
        return
    }

    if (questions.isEmpty()) return

    val question = questions[currentIndex]
    val selectedOptionId = answers[question.questionId]

    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Text("Question ${currentIndex + 1} / ${questions.size}")
        Text(elapsedTime)

        Spacer(Modifier.height(12.dp))

        Text(
            question.questionText,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(12.dp))

        options.forEach { option ->
            val isSelected = option.optionId == selectedOptionId

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.secondaryContainer
                ),
                onClick = {
                    viewModel.selectOption(
                        question.questionId,
                        option.optionId
                    )
                }
            ) {
                Text(option.optionText)
            }
        }

        Spacer(Modifier.height(16.dp))

        val isAnswered = selectedOptionId != null

        if (currentIndex == questions.lastIndex) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = isAnswered,
                onClick = {
                    viewModel.submitQuiz(attemptId)
                    onFinish()
                }
            ) {
                Text("Submit")
            }
        } else {
            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = isAnswered,
                onClick = { viewModel.nextQuestion() }
            ) {
                Text("Next")
            }
        }
    }
}
