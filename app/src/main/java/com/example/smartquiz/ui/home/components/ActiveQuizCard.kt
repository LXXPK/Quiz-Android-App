package com.example.smartquiz.ui.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.HourglassTop
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.smartquiz.R
import com.example.smartquiz.data.local.entity.quiz.QuizEntity
import kotlinx.coroutines.delay
import java.util.Date
import java.util.concurrent.TimeUnit

private enum class RelativeTimeType {
    ACTIVATION,
    EXPIRATION
}

@Composable
fun ActiveQuizCard(
    handleQuizCardClick: (QuizEntity) -> Unit,
    quiz: QuizEntity,
    activeTime: Date,
    expirationTime: Date,
    progress: Float,
    modifier: Modifier = Modifier
) {
    var now by remember { mutableStateOf(System.currentTimeMillis()) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(60_000)
            now = System.currentTimeMillis()
        }
    }

    val isLive = now in activeTime.time until expirationTime.time
    val isExpired = now >= expirationTime.time

    val surfaceColor = when {
        isExpired -> MaterialTheme.colorScheme.surfaceVariant
        isLive -> MaterialTheme.colorScheme.surface
        else -> MaterialTheme.colorScheme.surface
    }

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = surfaceColor),
        onClick = { if (isLive) handleQuizCardClick(quiz) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .alpha(if (isExpired) 0.65f else 1f)
        ) {


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = quiz.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = quiz.category,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                StatusChip(
                    isLive = isLive,
                    isExpired = isExpired,
                    activeTime = activeTime,
                    expirationTime = expirationTime,
                    now = now
                )
            }


            if (isLive && progress > 0f) {
                Spacer(Modifier.height(14.dp))
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor =
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                )
            }
        }
    }
}

@Composable
private fun StatusChip(
    isLive: Boolean,
    isExpired: Boolean,
    activeTime: Date,
    expirationTime: Date,
    now: Long
) {
    val (label, icon, color) = when {
        isExpired -> Triple(
            stringResource(R.string.expired),
            Icons.Outlined.Schedule,
            MaterialTheme.colorScheme.error
        )

        isLive -> Triple(
            stringResource(R.string.quiz_is_live),
            Icons.Outlined.Schedule,
            MaterialTheme.colorScheme.primary
        )

        else -> Triple(
            "",
            Icons.Outlined.HourglassTop,
            MaterialTheme.colorScheme.secondary
        )
    }

    Column(horizontalAlignment = Alignment.End) {

        if (label.isNotEmpty()) {
            AssistChip(
                onClick = {},
                enabled = false,
                label = {
                    Text(
                        text = label,
                        fontWeight = FontWeight.Medium
                    )
                },
                leadingIcon = {
                    Icon(icon, null)
                },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = color.copy(alpha = 0.15f),
                    labelColor = color,
                    leadingIconContentColor = color
                )
            )
        }

        Spacer(Modifier.height(6.dp))

        RelativeTimeText(
            time = if (isLive) expirationTime else activeTime,
            type =
                if (isLive) RelativeTimeType.EXPIRATION
                else RelativeTimeType.ACTIVATION,
            now = now
        )
    }
}

@Composable
private fun RelativeTimeText(
    time: Date,
    type: RelativeTimeType,
    now: Long
) {
    val diffMillis = (time.time - now).coerceAtLeast(0)

    val days = TimeUnit.MILLISECONDS.toDays(diffMillis)
    val hours = TimeUnit.MILLISECONDS.toHours(diffMillis)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(diffMillis)

    val (daysPlural, hoursPlural, minutesPlural) =
        if (type == RelativeTimeType.ACTIVATION) {
            Triple(
                R.plurals.active_in_days,
                R.plurals.active_in_hours,
                R.plurals.active_in_minutes
            )
        } else {
            Triple(
                R.plurals.expires_in_days,
                R.plurals.expires_in_hours,
                R.plurals.expires_in_minutes
            )
        }

    val text = when {
        days > 0 ->
            pluralStringResource(daysPlural, days.toInt(), days.toInt())
        hours > 0 ->
            pluralStringResource(hoursPlural, hours.toInt(), hours.toInt())
        else ->
            pluralStringResource(minutesPlural, minutes.toInt(), minutes.toInt())
    }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector =
                if (type == RelativeTimeType.ACTIVATION)
                    Icons.Outlined.HourglassTop
                else
                    Icons.Outlined.Schedule,
            contentDescription = null,
            modifier = Modifier.size(14.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
