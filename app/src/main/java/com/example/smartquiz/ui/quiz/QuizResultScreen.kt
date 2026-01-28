//package com.example.smartquiz.ui.quiz
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.outlined.AccessTime
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import com.example.smartquiz.viewmodel.quiz.QuizPlayViewModel
//
//@Composable
//fun QuizResultScreen(
//    onDone: () -> Unit,
//    viewModel: QuizPlayViewModel
//) {
//
//    /* ---------- STATE ---------- */
//
//    val score by viewModel.score.collectAsState()
//    val correct by viewModel.correctCount.collectAsState()
//    val percentage by viewModel.percentage.collectAsState()
//
//    val questions by viewModel.questions.collectAsState()
//    val attempted by viewModel.attemptedCount.collectAsState()
//    val unattempted by viewModel.unattemptedCount.collectAsState()
//
//    val remainingTime by viewModel.remainingTimeText.collectAsState()
//
//    val totalQuestions = questions.size
//    val incorrect = attempted - correct
//
//    /* ---------- TIME CALCULATION ---------- */
//
//    val totalQuizDurationSeconds = 2 * 60   // 02:00 (same as ViewModel)
//    val remainingSeconds = remember(remainingTime) {
//        val parts = remainingTime.split(":")
//        (parts[0].toInt() * 60) + parts[1].toInt()
//    }
//
//    val timeUsedSeconds = totalQuizDurationSeconds - remainingSeconds
//
//    val timeUsedText = remember(timeUsedSeconds) {
//        val m = timeUsedSeconds / 60
//        val s = timeUsedSeconds % 60
//        "%02d:%02d".format(m, s)
//    }
//
//    val totalQuizDurationText = "02:00"
//
//    /* ---------- UI ---------- */
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//            .verticalScroll(rememberScrollState())
//    ) {
//
//        /* ---------- HEADER ---------- */
//
//        Text(
//            text = "Quiz Result",
//            style = MaterialTheme.typography.titleLarge,
//            fontWeight = FontWeight.Bold
//        )
//
//        Spacer(Modifier.height(16.dp))
//
//        /* ---------- FINAL SCORE ---------- */
//
//        ResultCard(
//            title = "Final Score",
//            value = "$score / ${totalQuestions * 10}",
//            subtitle = "$correct Correct • $incorrect Incorrect",
//            bgColor = Color(0xFF1E2328)
//        )
//
//        Spacer(Modifier.height(12.dp))
//
//        /* ---------- ACCURACY ---------- */
//
//        ResultCard(
//            title = "Accuracy",
//            value = "$percentage%",
//            subtitle = "Correctness",
//            bgColor = Color(0xFF4C5BD4)
//        )
//
//        Spacer(Modifier.height(12.dp))
//
//        /* ---------- ATTEMPT SUMMARY ---------- */
//
//        ResultCard(
//            title = "Attempt Summary",
//            value = "$attempted / $totalQuestions",
//            subtitle = "Attempted Questions",
//            bgColor = Color(0xFF0AA64F)
//        )
//
//        Spacer(Modifier.height(12.dp))
//
//        ResultCard(
//            title = "Unattempted",
//            value = unattempted.toString(),
//            subtitle = "Questions",
//            bgColor = Color(0xFFFFC83D)
//        )
//
//        Spacer(Modifier.height(24.dp))
//
//        /* ---------- TIME DETAILS ---------- */
//
//        Text(
//            text = "Time Details",
//            style = MaterialTheme.typography.titleMedium,
//            fontWeight = FontWeight.Bold
//        )
//
//        Spacer(Modifier.height(12.dp))
//
//        TimeRow(
//            label = "Total Quiz Duration",
//            value = totalQuizDurationText,
//            iconColor = Color.Gray
//        )
//
//        Spacer(Modifier.height(8.dp))
//
//        TimeRow(
//            label = "Time Used",
//            value = timeUsedText,
//            iconColor = Color(0xFF0AA64F)
//        )
//
//        Spacer(Modifier.height(8.dp))
//
//        TimeRow(
//            label = "Time Left at Submission",
//            value = remainingTime,
//            iconColor = Color.Blue
//        )
//
//        Spacer(Modifier.height(32.dp))
//
//        /* ---------- ACTION ---------- */
//
//        Button(
//            onClick = onDone,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Done")
//        }
//    }
//}
//
///* ---------- REUSABLE COMPONENTS ---------- */
//
//@Composable
//private fun ResultCard(
//    title: String,
//    value: String,
//    subtitle: String,
//    bgColor: Color
//) {
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(bgColor, RoundedCornerShape(10.dp))
//            .padding(16.dp)
//    ) {
//        Column {
//            Text(
//                text = title,
//                color = Color.White.copy(alpha = 0.9f)
//            )
//            Spacer(Modifier.height(6.dp))
//            Text(
//                text = value,
//                color = Color.White,
//                fontWeight = FontWeight.Bold
//            )
//            Spacer(Modifier.height(4.dp))
//            Text(
//                text = subtitle,
//                color = Color.White.copy(alpha = 0.85f)
//            )
//        }
//    }
//}
//
//@Composable
//private fun TimeRow(
//    label: String,
//    value: String,
//    iconColor: Color
//) {
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Icon(
//            imageVector = Icons.Outlined.AccessTime,
//            contentDescription = null,
//            tint = iconColor
//        )
//        Spacer(Modifier.width(8.dp))
//        Text(
//            text = label,
//            modifier = Modifier.weight(1f)
//        )
//        Text(
//            text = value,
//            fontWeight = FontWeight.Medium
//        )
//    }
//}

package com.example.smartquiz.ui.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.smartquiz.viewmodel.quiz.QuizPlayViewModel
import kotlin.math.roundToInt

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

    val totalQuestions = correct + (attempted - correct) + unattempted
    val incorrect = attempted - correct
    val maxScore = totalQuestions * 10

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        /* ---------- HEADER ---------- */

        Text(
            text = "🎉 Quiz Completed!",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(20.dp))

        /* ---------- CIRCULAR SCORE ---------- */

        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                progress = percentage / 100f,
                strokeWidth = 10.dp,
                modifier = Modifier.size(160.dp),
                color = MaterialTheme.colorScheme.primary
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "$percentage%",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Accuracy",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        /* ---------- STATS ROW ---------- */

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ResultStatCard(
                title = "Correct",
                value = correct.toString(),
                icon = Icons.Outlined.CheckCircle,
                color = Color(0xFF2E7D32),
                modifier = Modifier.weight(1f)
            )

            ResultStatCard(
                title = "Wrong",
                value = incorrect.toString(),
                icon = Icons.Outlined.Close,
                color = Color(0xFFC62828),
                modifier = Modifier.weight(1f)
            )

            ResultStatCard(
                title = "Skipped",
                value = unattempted.toString(),
                icon = Icons.Outlined.HelpOutline,
                color = Color(0xFFF9A825),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(24.dp))

        /* ---------- FINAL SCORE ---------- */

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Final Score",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "$score / $maxScore",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        /* ---------- TIME INFO ---------- */

        TimeInfoRow("Time Left", remainingTime)

        Spacer(Modifier.height(32.dp))

        /* ---------- ACTION ---------- */

        Button(
            onClick = onDone,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Back to Home")
        }
    }
}

/* ---------- SMALL COMPONENTS ---------- */

@Composable
private fun ResultStatCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.12f)
        )
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = color
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = value,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun TimeInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Outlined.AccessTime,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
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
