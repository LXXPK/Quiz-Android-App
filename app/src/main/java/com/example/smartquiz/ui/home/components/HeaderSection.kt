
package com.example.smartquiz.ui.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.smartquiz.R

@Composable
fun HeaderSection(
    userName: String,
    streak: Int,
    onHistoryClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(id = R.string.welcome_emoji),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = userName,
                style = MaterialTheme.typography.titleLarge
            )
        }

        Surface(
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.padding(end = dimensionResource(id = R.dimen.small_padding))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.fire),
                    contentDescription = stringResource(id = R.string.current_streak),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(dimensionResource(id = R.dimen.icon_small))
                )
                Spacer(Modifier.width(dimensionResource(id = R.dimen.extra_small_padding)))
                Text(
                    text = streak.toString(),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        IconButton(
            onClick = onHistoryClick,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.History,
                contentDescription = stringResource(id = R.string.view_history),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
