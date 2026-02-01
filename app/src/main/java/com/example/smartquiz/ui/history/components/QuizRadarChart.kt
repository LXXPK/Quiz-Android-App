package com.example.smartquiz.ui.history.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.radarChart.RadarChart
import com.aay.compose.radarChart.model.NetLinesStyle
import com.aay.compose.radarChart.model.Polygon
import com.aay.compose.radarChart.model.PolygonStyle
import com.example.smartquiz.data.local.entity.quiz.QuizAttemptEntity
import com.example.smartquiz.ui.theme.SmartQuizTheme

@Composable
fun QuizRadarChart(
    attempts: List<QuizAttemptEntity>,
    modifier: Modifier = Modifier
) {
    if (attempts.size < 3) return

    val radarLabels = attempts.takeLast(6).map { it.quizId.take(8) }
    val values = attempts.takeLast(6).map { it.score.toDouble() }

    val labelsStyle = TextStyle(
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp
    )

    val scalarValuesStyle = TextStyle(
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontSize = 9.sp
    )

    // -------- Entry animation trigger --------
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    // -------- Subtle pulse animation (1 cycle) --------
    val pulseAlpha by animateFloatAsState(
        targetValue = if (visible) 0.55f else 0.35f,
        animationSpec = tween(
            durationMillis = 900,
            easing = FastOutSlowInEasing
        ),
        label = "radarPulse"
    )

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(
            animationSpec = tween(400)
        ) + scaleIn(
            initialScale = 0.92f,
            animationSpec = tween(
                durationMillis = 600,
                easing = FastOutSlowInEasing
            )
        )
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(340.dp)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            RadarChart(
                modifier = Modifier.fillMaxSize(),
                radarLabels = radarLabels,
                labelsStyle = labelsStyle,
                netLinesStyle = NetLinesStyle(
                    netLineColor = MaterialTheme.colorScheme.outlineVariant,
                    netLinesStrokeWidth = 1.5f,
                    netLinesStrokeCap = StrokeCap.Round
                ),
                scalarSteps = 5,
                scalarValue = 100.0,
                scalarValuesStyle = scalarValuesStyle,
                polygons = listOf(
                    Polygon(
                        values = values,
                        unit = "%",
                        style = PolygonStyle(
                            fillColor = MaterialTheme.colorScheme.primary,
                            fillColorAlpha = pulseAlpha, // 🎯 animated focus
                            borderColor = MaterialTheme.colorScheme.primary,
                            borderColorAlpha = 0.9f,
                            borderStrokeWidth = 2f,
                            borderStrokeCap = StrokeCap.Round
                        )
                    )
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuizRadarChartPreview() {
    SmartQuizTheme {
        val dummyAttempts = listOf(
            QuizAttemptEntity(1, "Android", "user1", 80, System.currentTimeMillis()),
            QuizAttemptEntity(2, "Kotlin", "user1", 60, System.currentTimeMillis()),
            QuizAttemptEntity(3, "Compose", "user1", 90, System.currentTimeMillis()),
            QuizAttemptEntity(4, "Room", "user1", 70, System.currentTimeMillis()),
            QuizAttemptEntity(5, "Hilt", "user1", 85, System.currentTimeMillis()),
            QuizAttemptEntity(6, "Testing", "user1", 75, System.currentTimeMillis())
        )
        QuizRadarChart(attempts = dummyAttempts)
    }
}
