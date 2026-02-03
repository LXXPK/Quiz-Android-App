package com.example.smartquiz.ui.profile

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
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
    val containerColor by animateColorAsState(
        if (selected)
            MaterialTheme.colorScheme.primaryContainer
        else
            MaterialTheme.colorScheme.surface,
        label = "chip-bg"
    )

    val textColor by animateColorAsState(
        if (selected)
            MaterialTheme.colorScheme.onPrimaryContainer
        else
            MaterialTheme.colorScheme.onSurface,
        label = "chip-text"
    )

    AssistChip(
        onClick = { onClick?.invoke() },
        enabled = !disabled,
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = textColor
            )
        },
        modifier = Modifier
            .height(42.dp)
            .alpha(if (disabled) 0.45f else 1f),
        border = BorderStroke(
            1.dp,
            if (selected)
                MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
            else
                MaterialTheme.colorScheme.outline
        ),
        colors = AssistChipDefaults.assistChipColors(
            containerColor = containerColor
        )
    )
}
