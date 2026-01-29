package com.example.smartquiz.ui.quiz.quizplay.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.smartquiz.R
import com.example.smartquiz.viewmodel.quiz.QuizPlayViewModel

@Composable
fun QuestionPalette(
    viewModel: QuizPlayViewModel
) {
    val questions by viewModel.questions.collectAsState()
    val answers by viewModel.answers.collectAsState()
    val visited by viewModel.visitedQuestions.collectAsState()

    Surface(
        modifier = Modifier.fillMaxWidth(),
        tonalElevation = 8.dp,
        shadowElevation = 8.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.quiz_question_palette_title),
                    style = MaterialTheme.typography.titleMedium
                )

                IconButton(onClick = viewModel::togglePalette) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = stringResource(
                            R.string.action_close
                        )
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(5),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                itemsIndexed(questions) { index, question ->

                    val isAnswered =
                        answers.containsKey(question.questionId)
                    val isVisited =
                        visited.contains(index)

                    val backgroundColor = when {
                        isAnswered -> Color(0xFF2E7D32)
                        isVisited -> Color(0xFFD32F2F)
                        else -> Color.LightGray
                    }

                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(backgroundColor)
                            .clickable {
                                viewModel.jumpToQuestion(index)
                                viewModel.togglePalette()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${index + 1}",
                            color = Color.White,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    }
}
