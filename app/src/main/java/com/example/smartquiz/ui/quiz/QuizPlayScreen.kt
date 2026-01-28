package com.example.smartquiz.ui.quiz

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smartquiz.viewmodel.quiz.QuizPlayViewModel
import com.example.smartquiz.viewmodel.quiz.QuizPlayViewModel.TimerColorState

@Composable
fun QuizPlayScreen(
    quizId: String,
    attemptId: Int,
    onFinish: () -> Unit,
    viewModel: QuizPlayViewModel = hiltViewModel()
) {

    /* ⛔ Disable system back */
    BackHandler(enabled = true) {}

    LaunchedEffect(quizId) {
        viewModel.loadQuiz(quizId, attemptId)
    }

    /* ---------- STATE ---------- */

    val questions by viewModel.questions.collectAsState()
    val options by viewModel.options.collectAsState()
    val currentIndex by viewModel.currentIndex.collectAsState()
    val answers by viewModel.answers.collectAsState()
    val visited by viewModel.visitedQuestions.collectAsState()

    val remainingTime by viewModel.remainingTimeText.collectAsState()
    val timerProgress by viewModel.timerProgress.collectAsState()
    val isBlinking by viewModel.isBlinking.collectAsState()
    val timerColorState by viewModel.timerColorState.collectAsState()

    val showPalette by viewModel.showPalette.collectAsState()
    val showSubmitDialog by viewModel.showSubmitDialog.collectAsState()
    val showTimeoutDialog by viewModel.showTimeoutDialog.collectAsState()
    val isQuizFinished by viewModel.isQuizFinished.collectAsState()

    val attempted by viewModel.attemptedCount.collectAsState()
    val unattempted by viewModel.unattemptedCount.collectAsState()

    val uiState by viewModel.uiState.collectAsState()

    /* ---------- NAVIGATION ---------- */

    LaunchedEffect(isQuizFinished) {
        if (isQuizFinished) onFinish()
    }

    /* ---------- TIMER COLOR ---------- */

    val timerColor = when (timerColorState) {
        TimerColorState.NORMAL -> MaterialTheme.colorScheme.primary
        TimerColorState.WARNING -> Color(0xFFFFA000)
        TimerColorState.DANGER -> Color.Red
    }

    /* ---------- BLINK ANIMATION ---------- */

    val alpha by rememberInfiniteTransition(label = "blink")
        .animateFloat(
            initialValue = 1f,
            targetValue = if (isBlinking) 0.3f else 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(600),
                repeatMode = RepeatMode.Reverse
            ),
            label = "alphaAnim"
        )

    /* ---------- TIMEOUT DIALOG ---------- */

    if (showTimeoutDialog) {
        AlertDialog(
            onDismissRequest = {},
            confirmButton = {
                Button(onClick = {}) { Text("OK") }
            },
            title = { Text("Time’s up!") },
            text = { Text("Your quiz has been submitted automatically.") }
        )
    }

    /* ---------- SUBMIT CONFIRMATION ---------- */

    if (showSubmitDialog) {
        AlertDialog(
            onDismissRequest = viewModel::closeSubmitDialog,
            confirmButton = {
                Button(onClick = { viewModel.submitQuiz(attemptId) }) {
                    Text("Submit")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = viewModel::closeSubmitDialog) {
                    Text("Cancel")
                }
            },
            title = { Text("Submit Quiz?") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("⏰ Time Left: $remainingTime")
                    Text("✅ Attempted: $attempted")
                    Text("❌ Unattempted: $unattempted")
                }
            }
        )
    }

    /* ---------- LOADING ---------- */

    if (uiState.isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    if (questions.isEmpty()) return

    val question = questions[currentIndex]
    val selectedOptionId = answers[question.questionId]

    /* ---------- MAIN UI ---------- */

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        /* ---------- TIMER HEADER ---------- */

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text("Question ${currentIndex + 1}/${questions.size}")

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.alpha(alpha)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Timer,
                    contentDescription = "Timer",
                    tint = timerColor,
                    modifier = Modifier.size(18.dp)
                )

                Spacer(Modifier.width(6.dp))

                Text(
                    text = remainingTime,
                    color = timerColor,
                    style = MaterialTheme.typography.labelLarge
                )

                IconButton(onClick = viewModel::togglePalette) {
                    Icon(
                        imageVector =
                            if (showPalette)
                                Icons.Default.KeyboardArrowUp
                            else
                                Icons.Default.KeyboardArrowDown,
                        contentDescription = "Palette"
                    )
                }
            }
        }

        Spacer(Modifier.height(6.dp))

        LinearProgressIndicator(
            progress = { timerProgress },
            modifier = Modifier.fillMaxWidth(),
            color = timerColor,
            trackColor = timerColor.copy(alpha = 0.2f)
        )

        Spacer(Modifier.height(16.dp))

        /* ---------- QUESTION ---------- */

        Text(
            text = question.questionText,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(16.dp))

        /* ---------- OPTIONS ---------- */

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
                            MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Text(option.optionText)
            }
        }

        Spacer(Modifier.height(24.dp))

        /* ---------- NAVIGATION ---------- */

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Button(
                enabled = currentIndex > 0,
                onClick = viewModel::previousQuestion
            ) {
                Text("Previous")
            }

            Button(
                enabled = currentIndex < questions.lastIndex,
                onClick = viewModel::nextQuestion
            ) {
                Text("Next")
            }
        }

        Spacer(Modifier.height(20.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = viewModel::openSubmitDialog
        ) {
            Text("Submit")
        }
    }

    /* ---------- QUESTION PALETTE ---------- */

    if (showPalette) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            tonalElevation = 6.dp
        ) {
            Column(Modifier.padding(12.dp)) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Question Palette", style = MaterialTheme.typography.titleSmall)
                    IconButton(onClick = viewModel::togglePalette) {
                        Icon(Icons.Default.KeyboardArrowUp, null)
                    }
                }

                Spacer(Modifier.height(8.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(5),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(questions) { index, q ->

                        val isAnswered = answers.containsKey(q.questionId)
                        val isVisited = visited.contains(index)

                        val bg = when {
                            isAnswered -> Color(0xFF2E7D32)
                            isVisited -> Color.Red
                            else -> Color.LightGray
                        }

                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(bg)
                                .clickable {
                                    viewModel.jumpToQuestion(index)
                                    viewModel.togglePalette()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("${index + 1}", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}