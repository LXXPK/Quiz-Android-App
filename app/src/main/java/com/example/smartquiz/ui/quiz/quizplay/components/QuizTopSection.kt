
package com.example.smartquiz.ui.quiz.quizplay.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartquiz.viewmodel.quiz.QuizPlayViewModel

@Composable
fun QuizTopSection(
    viewModel: QuizPlayViewModel
) {
    val current by viewModel.currentIndex.collectAsState()
    val questions by viewModel.questions.collectAsState()
    val remainingTime by viewModel.remainingTimeText.collectAsState()
    val progress by viewModel.timerProgress.collectAsState()
    val attempted by viewModel.attemptedCount.collectAsState()
    val unattempted by viewModel.unattemptedCount.collectAsState()

    var showStats by remember { mutableStateOf(false) }

    Column {



        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Question ${current + 1}/${questions.size}",
                style = MaterialTheme.typography.labelLarge
            )

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(
                    imageVector = Icons.Outlined.Timer,
                    contentDescription = "Timer"
                )

                Spacer(Modifier.width(6.dp))

                Text(
                    text = remainingTime,
                    style = MaterialTheme.typography.labelLarge
                )

                IconButton(onClick = { showStats = !showStats }) {
                    Icon(
                        imageVector =
                            if (showStats)
                                Icons.Default.KeyboardArrowUp
                            else
                                Icons.Default.KeyboardArrowDown,
                        contentDescription = "Show stats"
                    )
                }
            }
        }

        Spacer(Modifier.height(6.dp))



        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
        )



        if (showStats) {
            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AssistChip(
                    onClick = {},
                    label = { Text("Attempted: $attempted") }
                )

                AssistChip(
                    onClick = {},
                    label = { Text("Unattempted: $unattempted") }
                )
            }
        }
    }
}
