package com.example.smartquiz.ui.quiz.quizresult.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color

@Composable
fun ResultStatsRow(
    correct: Int,
    incorrect: Int,
    skipped: Int
) {
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
            value = skipped.toString(),
            icon = Icons.Outlined.HelpOutline,
            color = Color(0xFFF9A825),
            modifier = Modifier.weight(1f)
        )
    }
}
