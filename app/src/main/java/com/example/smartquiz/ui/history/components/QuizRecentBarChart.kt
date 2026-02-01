package com.example.smartquiz.ui.history.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.aay.compose.barChart.BarChart
import com.aay.compose.barChart.model.BarParameters
import com.example.smartquiz.R
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
            dataName = stringResource(id = R.string.graph_recent_scores_label),
            data = lastAttempts.map { it.score.toDouble() },
            barColor = MaterialTheme.colorScheme.secondary
        )
    )

    val xAxisData = lastAttempts.map { it.quizId.take(5) }

    val axisTextStyle = TextStyle(
        fontSize = dimensionResource(id = R.dimen.font_size_chart_axis).value.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontWeight = FontWeight.W400
    )

    Box(modifier = modifier
        .fillMaxWidth()
        .height(dimensionResource(id = R.dimen.chart_height_standard))
        .padding(dimensionResource(id = R.dimen.medium_padding))
    ) {
        BarChart(
            chartParameters = barParameters,
            gridColor = MaterialTheme.colorScheme.outlineVariant,
            xAxisData = xAxisData,
            isShowGrid = true,
            animateChart = true,
            showGridWithSpacer = true,
            yAxisStyle = axisTextStyle,
            xAxisStyle = axisTextStyle,
            yAxisRange = 10,
            barWidth = dimensionResource(id = R.dimen.chart_bar_width)
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
