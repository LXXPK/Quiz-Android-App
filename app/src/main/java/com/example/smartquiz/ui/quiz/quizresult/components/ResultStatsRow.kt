package com.example.smartquiz.ui.quiz.quizresult.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ResultStatsRow(
    correct: Int,
    incorrect: Int,
    skipped: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        ResultStatCard(
            title = "Correct",
            value = correct.toString(),
            icon = Icons.Outlined.CheckCircle,
            color = Color(0xFF16A34A),
            modifier = Modifier.weight(1f)
        )

        ResultStatCard(
            title = "Wrong",
            value = incorrect.toString(),
            icon = Icons.Outlined.Close,
            color = Color(0xFFDC2626),
            modifier = Modifier.weight(1f)
        )

        ResultStatCard(
            title = "Skipped",
            value = skipped.toString(),
            icon = Icons.Outlined.HelpOutline,
            color = Color(0xFFF59E0B),
            modifier = Modifier.weight(1f)
        )
    }
}
