package com.example.smartquiz.ui.history.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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
        Color(0xFF1E40AF), // Blue
        Color(0xFFF97316), // Orange
        Color(0xFF16A34A), // Green
        Color(0xFFDC2626), // Red
        Color(0xFF7C3AED), // Purple
        Color(0xFF0891B2)  // Cyan
    )

    val pieChartData = remember(attempts) {
        attempts.groupBy { it.quizId }.entries.mapIndexed { index, entry ->
            PieChartData(
                partName = entry.key,
                data = entry.value.size.toDouble(),
                color = colors[index % colors.size]
            )
        }
    }

    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + scaleIn(initialScale = 0.9f)
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(320.dp)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {


            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                                Color.Transparent
                            )
                        )
                    )
            )


            PieChart(
                modifier = Modifier.fillMaxSize(),
                pieChartData = pieChartData,
                ratioLineColor = MaterialTheme.colorScheme.outline,
                textRatioStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 12.sp
                )
            )
        }
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
