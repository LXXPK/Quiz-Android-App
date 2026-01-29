package com.example.smartquiz.ui.quiz.quizresult.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ResultScoreCircle(percentage: Int) {
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
}
