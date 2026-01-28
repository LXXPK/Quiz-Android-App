package com.example.smartquiz.ui.profile

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp

@Composable
fun InterestChip(
    label: String,
    selected: Boolean,
    disabled: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    val bgColor by animateColorAsState(
        if (selected)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.surfaceVariant
    )

    val textColor by animateColorAsState(
        if (selected)
            MaterialTheme.colorScheme.onPrimary
        else
            MaterialTheme.colorScheme.onSurfaceVariant
    )

    AssistChip(
        onClick = { onClick?.invoke() },
        enabled = !disabled,
        label = { Text(label, color = textColor) },
        modifier = Modifier
            .height(40.dp)
            .alpha(if (disabled) 0.4f else 1f),
        colors = AssistChipDefaults.assistChipColors(
            containerColor = bgColor
        )
    )
}
