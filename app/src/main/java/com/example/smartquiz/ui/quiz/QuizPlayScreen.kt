package com.example.smartquiz.ui.quiz

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartquiz.data.repository.quiz.QuizRepository
import com.example.smartquiz.viewmodel.quiz.QuizPlayViewModel
import com.example.smartquiz.viewmodel.quiz.QuizPlayViewModelFactory


@Composable
fun QuizPlayScreen(
    quizId: String
) {
    val repository = remember { QuizRepository() }
    var showResult by remember { mutableStateOf(false) }

    val viewModel: QuizPlayViewModel = viewModel(
        factory = QuizPlayViewModelFactory(repository)
    )

    val questions by viewModel.questions.collectAsState()
    val options by viewModel.options.collectAsState()
    val index by viewModel.currentIndex.collectAsState()
    val answers by viewModel.answers.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadQuiz(quizId)
    }

    if (questions.isEmpty()) {
        Text("Loading...")
        return
    }

    val question = questions[index]
    val selectedOptionId = answers[question.questionId]

    Column {
        Text("Question ${index + 1}")
        Text(question.questionText)

        options.forEach { option ->
            val isSelected = option.optionId == selectedOptionId

            Button(
                onClick = {
                    viewModel.selectOption(
                        question.questionId,
                        option.optionId
                    )
                }
            ) {
                Text(
                    text = if (isSelected)
                        "✓ ${option.optionText}"
                    else
                        option.optionText
                )
            }
        }

        Button(onClick = { viewModel.previousQuestion() }) {
            Text("Previous")
        }

        Button(onClick = { viewModel.nextQuestion() }) {
            Text("Next")
        }
    }
}