package com.example.smartquiz.ui.quiz.quizplay.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartquiz.viewmodel.quiz.QuizPlayViewModel

@Composable
fun QuestionSection(viewModel: QuizPlayViewModel) {
    val questions by viewModel.questions.collectAsState()
    val index by viewModel.currentIndex.collectAsState()
    val options by viewModel.options.collectAsState()
    val answers by viewModel.answers.collectAsState()

    val question = questions[index]
    val selected = answers[question.questionId]

    Text(
        text = question.questionText,
        style = MaterialTheme.typography.titleMedium
    )

    Spacer(Modifier.height(16.dp))

    options.forEach { option ->
        OptionItem(
            text = option.optionText,
            selected = option.optionId == selected,
            onClick = {
                viewModel.selectOption(
                    question.questionId,
                    option.optionId
                )
            }
        )
    }
}
