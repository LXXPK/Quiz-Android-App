package com.example.smartquiz.ui.quiz.quizdetails.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.smartquiz.R

@Composable
fun StartQuizButton(
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.button_height_large)),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.button_corner_radius_medium)),
        onClick = onClick
    ) {
        Text(
            text = stringResource(R.string.action_start_quiz),
            style = MaterialTheme.typography.titleMedium
        )
    }
}
