package com.example.smartquiz.ui.quiz.quizplay.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartquiz.ui.quiz.quizplay.dialogs.SubmitConfirmDialog
import com.example.smartquiz.ui.quiz.quizplay.dialogs.TimeoutDialog
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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {


        item {
            QuizTopSection(viewModel)
        }


        if (showPalette) {
            item {
                QuestionPalette(viewModel)
            }
        }


        item {
            QuestionSection(viewModel)
        }


        item {
            NavigationSection(viewModel)
        }


        item {
            SubmitButton(onClick = viewModel::openSubmitDialog)
        }
    }


    if (showSubmitDialog) {
        SubmitConfirmDialog(viewModel)
    }

    if (showTimeoutDialog) {
        TimeoutDialog()
    }
}
