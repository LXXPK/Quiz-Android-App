package com.example.smartquiz.ui.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.HourglassTop
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
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

    val cardColor = when {
        isExpired -> MaterialTheme.colorScheme.surfaceVariant
        else -> MaterialTheme.colorScheme.surface
    }

    Card(
        onClick = { if (isLive) handleQuizCardClick(quiz) },
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .alpha(if (isExpired) 0.7f else 1f)
        ) {


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = quiz.title,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = quiz.category,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                StatusBadge(
                    isLive = isLive,
                    isExpired = isExpired,
                    activeTime = activeTime,
                    expirationTime = expirationTime,
                    now = now
                )
            }

            if (isLive && progress > 0f) {
                Spacer(Modifier.height(12.dp))
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp),
                    trackColor =
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Composable
private fun StatusBadge(
    isLive: Boolean,
    isExpired: Boolean,
    activeTime: Date,
    expirationTime: Date,
    now: Long
) {
    Column(horizontalAlignment = Alignment.End) {

        when {
            isExpired -> {
                Text(
                    text = stringResource(R.string.expired),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }

            isLive -> {
                Text(
                    text = stringResource(R.string.quiz_is_live),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(2.dp))
                RelativeTimeText(
                    time = expirationTime,
                    type = RelativeTimeType.EXPIRATION,
                    now = now
                )
            }

            else -> {
                RelativeTimeText(
                    time = activeTime,
                    type = RelativeTimeType.ACTIVATION,
                    now = now
                )
            }
        }
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
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 2.dp)
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
        Spacer(Modifier.width(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall
        )
    }
}
