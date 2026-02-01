package com.example.smartquiz.ui.common

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SmartSnackbar(
    data: SnackbarData
) {
    val isError = listOf("error", "invalid", "failed", "wrong")
        .any { data.visuals.message.contains(it, ignoreCase = true) }

    val containerColor by animateColorAsState(
        if (isError)
            MaterialTheme.colorScheme.errorContainer
        else
            MaterialTheme.colorScheme.surfaceVariant,
        label = "snackbarColor"
    )

    val contentColor =
        if (isError)
            MaterialTheme.colorScheme.onErrorContainer
        else
            MaterialTheme.colorScheme.onSurfaceVariant

    val icon =
        if (isError)
            Icons.Outlined.ErrorOutline
        else
            Icons.Outlined.Info

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = containerColor,
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor
            )

            Spacer(Modifier.width(12.dp))

            Text(
                text = data.visuals.message,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyMedium,
                color = contentColor
            )

            data.visuals.actionLabel?.let {
                Spacer(Modifier.width(8.dp))
                TextButton(onClick = { data.performAction() }) {
                    Text(it)
                }
            }
        }
    }
}
