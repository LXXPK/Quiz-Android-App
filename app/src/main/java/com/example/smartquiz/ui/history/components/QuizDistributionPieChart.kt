package com.example.smartquiz.ui.history.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.donutChart.PieChart
import com.aay.compose.donutChart.model.PieChartData
import com.example.smartquiz.data.local.entity.quiz.QuizAttemptEntity
import com.example.smartquiz.ui.theme.SmartQuizTheme

@Composable
fun QuizDistributionPieChart(
    attempts: List<QuizAttemptEntity>,
    modifier: Modifier = Modifier
) {
    if (attempts.isEmpty()) return

    val colors = listOf(
        Color(0xFF22A699),
        Color(0xFFF2BE22),
        Color(0xFFF29727),
        Color(0xFFF24C3D),
        Color(0xFF6200EE),
        Color(0xFF03DAC5)
    )

    val pieChartData = attempts.groupBy { it.quizId }.entries.mapIndexed { index, entry ->
        PieChartData(
            partName = entry.key,
            data = entry.value.size.toDouble(),
            color = colors[index % colors.size],
        )
    }

    Box(modifier = modifier
        .fillMaxWidth()
        .height(300.dp)
        .padding(16.dp)
    ) {
        PieChart(
            modifier = Modifier.fillMaxSize(),
            pieChartData = pieChartData,
            ratioLineColor = MaterialTheme.colorScheme.outline,
            textRatioStyle = TextStyle(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 12.sp
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuizDistributionPieChartPreview() {
    SmartQuizTheme {
        val dummyAttempts = listOf(
            QuizAttemptEntity(1, "Android", "user1", 80, 1000L),
            QuizAttemptEntity(2, "Android", "user1", 90, 2000L),
            QuizAttemptEntity(3, "Kotlin", "user1", 70, 3000L),
            QuizAttemptEntity(4, "Compose", "user1", 85, 4000L),
            QuizAttemptEntity(5, "Kotlin", "user1", 60, 5000L)
        )
        QuizDistributionPieChart(attempts = dummyAttempts)
    }
}
