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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.aay.compose.baseComponents.model.GridOrientation
import com.aay.compose.lineChart.LineChart
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineType
import com.example.smartquiz.R
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
            label = stringResource(id = R.string.graph_score_trend),
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
            text = stringResource(id = R.string.graph_axis_description),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
//            modifier = Modifier.padding(
//                horizontal = dimensionResource(id = R.dimen.medium_padding),
//                top = dimensionResource(id = R.dimen.small_padding)
//            )
        )

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.chart_height_small))
            .padding(
                horizontal = dimensionResource(id = R.dimen.medium_padding),
                vertical = dimensionResource(id = R.dimen.small_padding)
            )
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
                    fontSize = dimensionResource(id = R.dimen.font_size_chart_axis).value.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                ),
                xAxisStyle = TextStyle(
                    fontSize = dimensionResource(id = R.dimen.font_size_chart_axis).value.sp,
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
            lastScore > averageScore -> stringResource(id = R.string.graph_hint_above_avg)
            lastScore < averageScore -> stringResource(id = R.string.graph_hint_below_avg)
            else -> stringResource(id = R.string.graph_hint_consistent)
        }

        Text(
            text = hintText,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
//            modifier = Modifier.padding(
//                horizontal = dimensionResource(id = R.dimen.medium_padding),
//                bottom = dimensionResource(id = R.dimen.small_padding)
//            )
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
