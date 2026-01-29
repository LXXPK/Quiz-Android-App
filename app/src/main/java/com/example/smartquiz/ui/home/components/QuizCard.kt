
package com.example.smartquiz.ui.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.smartquiz.R
import com.example.smartquiz.data.local.entity.quiz.QuizEntity

@Composable
fun QuizCard(
    handleQuizCardClick: (QuizEntity) -> Unit,
    quiz: QuizEntity,
    containerColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { handleQuizCardClick(quiz) },
        modifier = modifier
            .fillMaxWidth()
           ,
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = dimensionResource(id = R.dimen.medium_padding),
                vertical = dimensionResource(id = R.dimen.large_padding)
            )
        ) {

            Text(
                text = quiz.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = quiz.category,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
