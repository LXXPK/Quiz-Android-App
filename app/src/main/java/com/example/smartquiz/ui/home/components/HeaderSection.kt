package com.example.smartquiz.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.smartquiz.R
import com.example.smartquiz.ui.theme.SmartQuizTheme

@Composable
fun HeaderSection(userName: String, streak: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = dimensionResource(id = R.dimen.medium_padding)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.user_greeting, userName),
            style = MaterialTheme.typography.headlineMedium
        )
        StreakBadge(streak = streak)
    }
}

@Composable
fun StreakBadge(streak: Int, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.extra_small_padding)),
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.fire),
            contentDescription = stringResource(R.string.streak_description),
            tint = Color(0xFFFF9800), // Orange color
            modifier = Modifier.size(dimensionResource(id = R.dimen.streak_icon_size))
        )
        Text(
            text = "$streak",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderSectionPreview() {
    SmartQuizTheme {
        HeaderSection(userName = "John Doe", streak = 5)
    }
}