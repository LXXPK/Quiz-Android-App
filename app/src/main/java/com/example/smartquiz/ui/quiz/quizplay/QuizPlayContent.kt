
package com.example.smartquiz.ui.quiz.quizplay

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.smartquiz.R
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
            .padding(dimensionResource(id = R.dimen.medium_padding))


    ) {
        QuizTopSection(viewModel)
        if (showPalette) {
            Spacer(Modifier.height(dimensionResource(id = R.dimen.small_padding)))
            QuestionPalette(viewModel)
        }

        Spacer(Modifier.height(dimensionResource(id = R.dimen.medium_padding)))

        QuestionSection(viewModel)

        Spacer(Modifier.height(dimensionResource(id = R.dimen.spacing_large)))

        NavigationSection(viewModel)

        Spacer(Modifier.height(dimensionResource(id = R.dimen.medium_padding)))

        SubmitButton(onClick = viewModel::openSubmitDialog)
    }

    if (showSubmitDialog) {
        SubmitConfirmDialog(viewModel)
    }

    if (showTimeoutDialog) {
        TimeoutDialog()
    }
}
