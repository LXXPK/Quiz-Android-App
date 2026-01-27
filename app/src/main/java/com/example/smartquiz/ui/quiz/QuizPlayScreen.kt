package com.example.smartquiz.ui.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smartquiz.viewmodel.quiz.QuizPlayViewModel

@Composable
fun QuizPlayScreen(
    quizId: String,
    attemptId: Int,
    onFinish: () -> Unit = {},
    viewModel: QuizPlayViewModel
) {


    LaunchedEffect(quizId) {
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

    uiState.errorMessage?.let { error ->
        Box(Modifier.fillMaxSize()) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        return
    }


    if (questions.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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


        Text(
            text = question.questionText,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(16.dp))


        options.forEach { option ->
            val isSelected = option.optionId == selectedOptionId

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
                        if (isSelected)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Text(option.optionText)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val isAnswered = selectedOptionId != null


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
                    enabled = isAnswered,
                    onClick = { viewModel.nextQuestion() }
                ) {
                    Text("Next")
                }
            }
        }
    }
}
