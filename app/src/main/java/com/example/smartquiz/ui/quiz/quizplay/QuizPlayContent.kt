
package com.example.smartquiz.ui.quiz.quizplay

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartquiz.ui.quiz.quizplay.components.*
import com.example.smartquiz.ui.quiz.quizplay.dialogs.*
import com.example.smartquiz.viewmodel.quiz.QuizPlayViewModel

@Composable
fun QuizPlayContent(
    viewModel: QuizPlayViewModel
) {
    val questions by viewModel.questions.collectAsState()
    val showPalette by viewModel.showPalette.collectAsState()
    val showSubmitDialog by viewModel.showSubmitDialog.collectAsState()
    val showTimeoutDialog by viewModel.showTimeoutDialog.collectAsState()

    if (questions.isEmpty()) return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)


    ) {
        QuizTopSection(viewModel)
        if (showPalette) {
            Spacer(Modifier.height(8.dp))
            QuestionPalette(viewModel)
        }

        Spacer(Modifier.height(16.dp))

        QuestionSection(viewModel)

        Spacer(Modifier.height(20.dp))

        NavigationSection(viewModel)

        Spacer(Modifier.height(16.dp))

        SubmitButton(onClick = viewModel::openSubmitDialog)
    }

    if (showSubmitDialog) {
        SubmitConfirmDialog(viewModel)
    }

    if (showTimeoutDialog) {
        TimeoutDialog()
    }
}
