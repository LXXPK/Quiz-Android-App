package com.example.smartquiz.ui.quiz.quizresult

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

    // ✅ NEW: time taken
    val timeTaken by viewModel.timeTakenText.collectAsState()

    val incorrect = attempted - correct
    val totalQuestions = correct + incorrect + unattempted
    val maxScore = totalQuestions * 10

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ResultHeader()

        Spacer(Modifier.height(20.dp))

        ResultScoreCircle(percentage)

        Spacer(Modifier.height(24.dp))

        ResultStatsRow(
            correct = correct,
            incorrect = incorrect,
            skipped = unattempted
        )

        Spacer(Modifier.height(24.dp))

        FinalScoreCard(
            score = score,
            maxScore = maxScore
        )

        Spacer(Modifier.height(24.dp))

        // Existing
        TimeInfoRow(
            label = "Time Left",
            value = remainingTime
        )

        Spacer(Modifier.height(12.dp))

        // ✅ NEW: Time taken info
        TimeInfoRow(
            label = "Time Taken",
            value = timeTaken
        )

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = onDone,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Back to Home")
        }
    }
}
