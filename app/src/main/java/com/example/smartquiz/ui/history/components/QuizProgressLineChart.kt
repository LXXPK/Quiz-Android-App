package com.example.smartquiz.ui.history.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.baseComponents.model.GridOrientation
import com.aay.compose.lineChart.LineChart
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineType
import com.example.smartquiz.data.local.entity.quiz.QuizAttemptEntity
import com.example.smartquiz.ui.theme.SmartQuizTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun QuizProgressLineChart(
    attempts: List<QuizAttemptEntity>,
    modifier: Modifier = Modifier
) {
    if (attempts.size < 2) return

    // Take last 10 attempts for progress view
    val lastAttempts = attempts.takeLast(10)

    val lineParameters = listOf(
        LineParameters(
            label = "Score Trend",
            data = lastAttempts.map { it.score.toDouble() },
            lineColor = MaterialTheme.colorScheme.primary,
            lineType = LineType.CURVED_LINE,
            lineShadow = true,
        )
    )

    val xAxisData = lastAttempts.map { attempt ->
        val sdf = SimpleDateFormat("MM/dd", Locale.getDefault())
        sdf.format(Date(attempt.attemptedAt))
    }

    Column(modifier = modifier.fillMaxWidth()) {
        // Axis Description
        Text(
            text = "Y-axis: Score (%) | X-axis: Date",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
//            modifier = Modifier.padding(horizontal = 16.dp, top = 8.dp)
        )

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            LineChart(
                modifier = Modifier.fillMaxSize(),
                linesParameters = lineParameters,
                isGrid = true,
                gridColor = MaterialTheme.colorScheme.outlineVariant,
                xAxisData = xAxisData,
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
                oneLineChart = true,
                gridOrientation = GridOrientation.VERTICAL
            )
        }

        // Hints/Insights based on the trend
        val scores = lastAttempts.map { it.score }
        val lastScore = scores.last()
        val averageScore = scores.average()

        val hintText = when {
            lastScore > averageScore -> "You're performing above your average! Keep it up."
            lastScore < averageScore -> "Your last score was a bit lower than usual. You've got this!"
            else -> "Consistency is key. You're maintaining a steady performance."
        }

        Text(
            text = hintText,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
//            modifier = Modifier.padding(horizontal = 16.dp, bottom = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuizProgressLineChartPreview() {
    SmartQuizTheme {
        val dummyAttempts = listOf(
            QuizAttemptEntity(1, "Quiz 1", "user1", 60, System.currentTimeMillis() - 400000000),
            QuizAttemptEntity(2, "Quiz 2", "user1", 75, System.currentTimeMillis() - 300000000),
            QuizAttemptEntity(3, "Quiz 3", "user1", 85, System.currentTimeMillis() - 200000000),
            QuizAttemptEntity(4, "Quiz 4", "user1", 80, System.currentTimeMillis() - 100000000),
            QuizAttemptEntity(5, "Quiz 5", "user1", 95, System.currentTimeMillis())
        )
        QuizProgressLineChart(attempts = dummyAttempts)
    }
}