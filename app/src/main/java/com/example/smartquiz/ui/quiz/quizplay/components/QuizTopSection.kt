package com.example.smartquiz.ui.quiz.quizplay.components


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
    val showPalette by viewModel.showPalette.collectAsState()


    val timerState by viewModel.timerColorState.collectAsState()
    val isBlinking by viewModel.isBlinking.collectAsState()

    val timerColor = when (timerState) {
        QuizPlayViewModel.TimerColorState.NORMAL ->
            MaterialTheme.colorScheme.primary

        QuizPlayViewModel.TimerColorState.WARNING ->
            MaterialTheme.colorScheme.tertiary

        QuizPlayViewModel.TimerColorState.DANGER ->
            MaterialTheme.colorScheme.error
    }

    val blinkAlpha by animateFloatAsState(
        targetValue = if (isBlinking) 0.4f else 1f,
        animationSpec = tween(durationMillis = 600),
        label = "blink"
    )

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
                    contentDescription = "Timer",
                    tint = timerColor.copy(alpha = blinkAlpha)
                )

                Spacer(Modifier.width(6.dp))

                Text(
                    text = remainingTime,
                    style = MaterialTheme.typography.labelLarge,
                    color = timerColor.copy(alpha = blinkAlpha)
                )

                IconButton(onClick = viewModel::togglePalette) {
                    Icon(
                        imageVector =
                            if (showPalette)
                                Icons.Default.KeyboardArrowUp
                            else
                                Icons.Default.KeyboardArrowDown,
                        contentDescription = "Question Palette"
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
            color = timerColor,
            trackColor = timerColor.copy(alpha = 0.25f)
        )
    }
}
