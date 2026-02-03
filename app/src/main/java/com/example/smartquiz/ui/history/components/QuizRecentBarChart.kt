package com.example.smartquiz.ui.history.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.barChart.BarChart
import com.aay.compose.barChart.model.BarParameters
import com.example.smartquiz.data.local.entity.quiz.QuizAttemptEntity
import com.example.smartquiz.ui.theme.SmartQuizTheme

@Composable
fun QuizRecentBarChart(
    attempts: List<QuizAttemptEntity>,
    modifier: Modifier = Modifier
) {
    if (attempts.isEmpty()) return


    val lastAttempts = attempts.takeLast(7)

    val barParameters = listOf(
        BarParameters(
            dataName = "Scores",
            data = lastAttempts.map { it.score.toDouble() },
            barColor = MaterialTheme.colorScheme.secondary
        )
    )

    val xAxisData = lastAttempts.map { it.quizId.take(5) }

    Box(modifier = modifier
        .fillMaxWidth()
        .height(400.dp)
        .padding(16.dp)
    ) {
        BarChart(
            chartParameters = barParameters,
            gridColor = MaterialTheme.colorScheme.outlineVariant,
            xAxisData = xAxisData,
            isShowGrid = true,
            animateChart = true,
            showGridWithSpacer = true,
            yAxisStyle = TextStyle(
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            ),
            xAxisStyle = TextStyle(
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.W400
            ),
            yAxisRange = 10,
            barWidth = 20.dp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuizRecentBarChartPreview() {
    SmartQuizTheme {
        val dummyAttempts = listOf(
            QuizAttemptEntity(1, "Quiz 1", "user1", 80, System.currentTimeMillis()),
            QuizAttemptEntity(2, "Quiz 2", "user1", 60, System.currentTimeMillis()),
            QuizAttemptEntity(3, "Quiz 3", "user1", 90, System.currentTimeMillis()),
            QuizAttemptEntity(4, "Quiz 4", "user1", 70, System.currentTimeMillis()),
            QuizAttemptEntity(5, "Quiz 5", "user1", 85, System.currentTimeMillis())
        )
        QuizRecentBarChart(attempts = dummyAttempts)
    }
}