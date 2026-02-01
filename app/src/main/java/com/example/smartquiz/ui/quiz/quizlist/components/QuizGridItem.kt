package com.example.smartquiz.ui.quiz.quizlist.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Assignment
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.smartquiz.R
import com.example.smartquiz.data.local.entity.quiz.QuizEntity

@Composable
fun QuizGridItem(
    quiz: QuizEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.quiz_grid_item_height))
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.card_elevation_medium))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.spacing_small)),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Assignment,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(dimensionResource(id = R.dimen.icon_medium))
                )

                Spacer(Modifier.height(dimensionResource(id = R.dimen.small_padding)))

                Text(
                    text = quiz.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2
                )
            }

            Text(
                text = quiz.category,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
