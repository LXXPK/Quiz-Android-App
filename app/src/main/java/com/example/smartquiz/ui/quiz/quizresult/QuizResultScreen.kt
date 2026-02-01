package com.example.smartquiz.ui.quiz.quizresult

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.smartquiz.R
import com.example.smartquiz.ui.quiz.quizresult.components.*
import com.example.smartquiz.viewmodel.quiz.QuizPlayViewModel

@Composable
fun QuizResultScreen(
    onDone: () -> Unit,
    viewModel: QuizPlayViewModel
) {
    val score by viewModel.score.collectAsState()
    val correct by viewModel.correctCount.collectAsState()
    val attempted by viewModel.attemptedCount.collectAsState()
    val unattempted by viewModel.unattemptedCount.collectAsState()
    val percentage by viewModel.percentage.collectAsState()
    val remainingTime by viewModel.remainingTimeText.collectAsState()


    val timeTaken by viewModel.timeTakenText.collectAsState()

    val incorrect = attempted - correct
    val totalQuestions = correct + incorrect + unattempted
    val maxScore = totalQuestions * 10

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(dimensionResource(id = R.dimen.spacing_large)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ResultHeader()

        Spacer(Modifier.height(dimensionResource(id = R.dimen.spacing_large)))

        ResultScoreCircle(percentage)

        Spacer(Modifier.height(dimensionResource(id = R.dimen.large_padding)))

        ResultStatsRow(
            correct = correct,
            incorrect = incorrect,
            skipped = unattempted
        )

        Spacer(Modifier.height(dimensionResource(id = R.dimen.large_padding)))

        FinalScoreCard(
            score = score,
            maxScore = maxScore
        )

        Spacer(Modifier.height(dimensionResource(id = R.dimen.large_padding)))

        // Existing
        TimeInfoRow(
            label = stringResource(id = R.string.label_time_left),
            value = remainingTime
        )

        Spacer(Modifier.height(dimensionResource(id = R.dimen.spacing_small)))


        TimeInfoRow(
            label = stringResource(id = R.string.label_time_taken),
            value = timeTaken
        )

        Spacer(Modifier.height(dimensionResource(id = R.dimen.spacing_xlarge)))

        Button(
            onClick = onDone,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(stringResource(id = R.string.action_back_to_home))
        }
    }
}
