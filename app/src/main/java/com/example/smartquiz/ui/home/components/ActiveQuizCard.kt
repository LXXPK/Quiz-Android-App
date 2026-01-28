
package com.example.smartquiz.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.HourglassTop
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.smartquiz.R
import com.example.smartquiz.data.local.entity.quiz.QuizEntity
import com.example.smartquiz.ui.theme.SmartQuizTheme
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
    val isLive = Date().after(activeTime)

    Card(
        onClick = { if (isLive) handleQuizCardClick(quiz) },
        modifier = modifier,
        colors = if (!isLive) CardDefaults.cardColors(containerColor = Color.LightGray) else CardDefaults.cardColors()
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.medium_padding))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f, fill = false)) {
                    Text(
                        text = quiz.title,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = quiz.category,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    if (isLive) {
                        Text(
                            text = stringResource(id = R.string.quiz_is_live),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        RelativeTimeText(time = expirationTime, type = RelativeTimeType.EXPIRATION)
                    } else {
                        RelativeTimeText(time = activeTime, type = RelativeTimeType.ACTIVATION)
                    }
                }
            }
            if (isLive && progress > 0) {
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.small_padding)))
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun RelativeTimeText(time: Date, type: RelativeTimeType) {
    val diff = time.time - Date().time
    val days = TimeUnit.MILLISECONDS.toDays(diff)
    val hours = TimeUnit.MILLISECONDS.toHours(diff)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)

    val daysPlural: Int
    val hoursPlural: Int
    val minutesPlural: Int

    if (type == RelativeTimeType.ACTIVATION) {
        daysPlural = R.plurals.active_in_days
        hoursPlural = R.plurals.active_in_hours
        minutesPlural = R.plurals.active_in_minutes
    } else {
        daysPlural = R.plurals.expires_in_days
        hoursPlural = R.plurals.expires_in_hours
        minutesPlural = R.plurals.expires_in_minutes
    }

    val text = when {
        days > 0 -> pluralStringResource(
            id = daysPlural,
            count = days.toInt(),
            days.toInt()
        )
        hours > 0 -> pluralStringResource(
            id = hoursPlural,
            count = hours.toInt(),
            hours.toInt()
        )
        else -> {
            if (type == RelativeTimeType.ACTIVATION) {
                stringResource(id = R.string.quiz_is_live)
            } else {
                pluralStringResource(
                    id = minutesPlural,
                    count = minutes.toInt(),
                    minutes.toInt()
                )
            }
        }
    }

    val icon = if (type == RelativeTimeType.ACTIVATION) Icons.Outlined.HourglassTop else Icons.Outlined.Schedule
    val contentDescriptionRes = if (type == RelativeTimeType.ACTIVATION) R.string.active_from else R.string.time_remaining

    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = stringResource(contentDescriptionRes)
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.small_padding)))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ActiveQuizCardLivePreview() {
    SmartQuizTheme {
        ActiveQuizCard(
            quiz = QuizEntity("1", "Sample Live Quiz", "Category", true),
            handleQuizCardClick = {},
            activeTime = Date(System.currentTimeMillis() - 3600000),
            expirationTime = Date(System.currentTimeMillis() + 3600000 * 25),
            progress = 0.5f
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ActiveQuizCardExpiresInOneDayPreview() {
    SmartQuizTheme {
        ActiveQuizCard(
            quiz = QuizEntity("1", "Sample Expires in 1 day", "Category", true),
            handleQuizCardClick = {},
            activeTime = Date(System.currentTimeMillis() - 3600000),
            expirationTime = Date(System.currentTimeMillis() + 3600000 * 24),
            progress = 0.1f
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ActiveQuizCardExpiresInOneMinutePreview() {
    SmartQuizTheme {
        ActiveQuizCard(
            quiz = QuizEntity("1", "Sample Expires in 1 minute", "Category", true),
            handleQuizCardClick = {},
            activeTime = Date(System.currentTimeMillis() - 3600000),
            expirationTime = Date(System.currentTimeMillis() + 60000),
            progress = 0.9f
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ActiveQuizCardActiveInOneDayPreview() {
    SmartQuizTheme {
        ActiveQuizCard(
            quiz = QuizEntity("1", "Sample Active in 1 day", "Category", true),
            handleQuizCardClick = {},
            activeTime = Date(System.currentTimeMillis() + 3600000 * 24),
            expirationTime = Date(System.currentTimeMillis() + 3600000 * 48),
            progress = 0f
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ActiveQuizCardActiveInHoursPreview() {
    SmartQuizTheme {
        ActiveQuizCard(
            quiz = QuizEntity("1", "Sample Active in Hours", "Category", true),
            handleQuizCardClick = {},
            activeTime = Date(System.currentTimeMillis() + 3600000 * 2),
            expirationTime = Date(System.currentTimeMillis() + 3600000 * 5),
            progress = 0f
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ActiveQuizCardActiveInOneMinutePreview() {
    SmartQuizTheme {
        ActiveQuizCard(
            quiz = QuizEntity("1", "Sample Active in 1 minute", "Category", true),
            handleQuizCardClick = {},
            activeTime = Date(System.currentTimeMillis() + 60000),
            expirationTime = Date(System.currentTimeMillis() + 3600000 * 2),
            progress = 0f
        )
    }
}