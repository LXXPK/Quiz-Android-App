package com.example.smartquiz.ui.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.smartquiz.viewmodel.quiz.QuizPlayViewModel

@Composable
fun QuizResultScreen(
    onDone: () -> Unit,
    viewModel: QuizPlayViewModel
) {

    /* ---------- STATE ---------- */

    val score by viewModel.score.collectAsState()
    val correct by viewModel.correctCount.collectAsState()
    val percentage by viewModel.percentage.collectAsState()

    val questions by viewModel.questions.collectAsState()
    val attempted by viewModel.attemptedCount.collectAsState()
    val unattempted by viewModel.unattemptedCount.collectAsState()

    val remainingTime by viewModel.remainingTimeText.collectAsState()

    val totalQuestions = questions.size
    val incorrect = attempted - correct

    /* ---------- TIME CALCULATION ---------- */

    val totalQuizDurationSeconds = 2 * 60   // 02:00 (same as ViewModel)
    val remainingSeconds = remember(remainingTime) {
        val parts = remainingTime.split(":")
        (parts[0].toInt() * 60) + parts[1].toInt()
    }

    val timeUsedSeconds = totalQuizDurationSeconds - remainingSeconds

    val timeUsedText = remember(timeUsedSeconds) {
        val m = timeUsedSeconds / 60
        val s = timeUsedSeconds % 60
        "%02d:%02d".format(m, s)
    }

    val totalQuizDurationText = "02:00"

    /* ---------- UI ---------- */

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        /* ---------- HEADER ---------- */

        Text(
            text = "Quiz Result",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(16.dp))

        /* ---------- FINAL SCORE ---------- */

        ResultCard(
            title = "Final Score",
            value = "$score / ${totalQuestions * 10}",
            subtitle = "$correct Correct • $incorrect Incorrect",
            bgColor = Color(0xFF1E2328)
        )

        Spacer(Modifier.height(12.dp))

        /* ---------- ACCURACY ---------- */

        ResultCard(
            title = "Accuracy",
            value = "$percentage%",
            subtitle = "Correctness",
            bgColor = Color(0xFF4C5BD4)
        )

        Spacer(Modifier.height(12.dp))

        /* ---------- ATTEMPT SUMMARY ---------- */

        ResultCard(
            title = "Attempt Summary",
            value = "$attempted / $totalQuestions",
            subtitle = "Attempted Questions",
            bgColor = Color(0xFF0AA64F)
        )

        Spacer(Modifier.height(12.dp))

        ResultCard(
            title = "Unattempted",
            value = unattempted.toString(),
            subtitle = "Questions",
            bgColor = Color(0xFFFFC83D)
        )

        Spacer(Modifier.height(24.dp))

        /* ---------- TIME DETAILS ---------- */

        Text(
            text = "Time Details",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(12.dp))

        TimeRow(
            label = "Total Quiz Duration",
            value = totalQuizDurationText,
            iconColor = Color.Gray
        )

        Spacer(Modifier.height(8.dp))

        TimeRow(
            label = "Time Used",
            value = timeUsedText,
            iconColor = Color(0xFF0AA64F)
        )

        Spacer(Modifier.height(8.dp))

        TimeRow(
            label = "Time Left at Submission",
            value = remainingTime,
            iconColor = Color.Blue
        )

        Spacer(Modifier.height(32.dp))

        /* ---------- ACTION ---------- */

        Button(
            onClick = onDone,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Done")
        }
    }
}

/* ---------- REUSABLE COMPONENTS ---------- */

@Composable
private fun ResultCard(
    title: String,
    value: String,
    subtitle: String,
    bgColor: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(bgColor, RoundedCornerShape(10.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = title,
                color = Color.White.copy(alpha = 0.9f)
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = value,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = subtitle,
                color = Color.White.copy(alpha = 0.85f)
            )
        }
    }
}

@Composable
private fun TimeRow(
    label: String,
    value: String,
    iconColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Outlined.AccessTime,
            contentDescription = null,
            tint = iconColor
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = label,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            fontWeight = FontWeight.Medium
        )
    }
}