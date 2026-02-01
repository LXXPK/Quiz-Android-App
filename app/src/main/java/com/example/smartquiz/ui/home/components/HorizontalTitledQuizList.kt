
package com.example.smartquiz.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.smartquiz.R
import com.example.smartquiz.data.local.entity.quiz.QuizEntity

@Composable
fun HorizontalTitledQuizList(
    title: String,
    quizzes: List<QuizEntity>,
    onViewAllClick: () -> Unit,
    onQuizCardClick: (QuizEntity) -> Unit,
    modifier: Modifier = Modifier,
    limit: Int = 0,
) {
    val items = if (limit > 0) quizzes.take(limit) else quizzes
    val listState = rememberLazyListState()

    val cardColors = listOf(
        MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
        MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f),
        MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f),
        MaterialTheme.colorScheme.surfaceVariant
    )

    val currentIndex by remember {
        derivedStateOf { listState.firstVisibleItemIndex }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )

            TextButton(
                onClick = onViewAllClick,
                modifier = Modifier.heightIn(min = 48.dp)
            ) {
                Text(text = stringResource(id = R.string.view_all))
            }
        }

        Spacer(Modifier.height(8.dp))


        LazyRow(
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.medium_padding)
            )
        ) {
            itemsIndexed(items) { index, quiz ->
                QuizCard(
                    quiz = quiz,
                    handleQuizCardClick = onQuizCardClick,
                    containerColor = cardColors[index % cardColors.size]
                )
            }

        }


        if (items.size > 1) {
            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEachIndexed { index, _ ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(
                                if (index == currentIndex) 8.dp else 6.dp
                            )
                            .background(
                                color =
                                    if (index == currentIndex)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}
