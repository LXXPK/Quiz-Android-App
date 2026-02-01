package com.example.smartquiz.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color



//private val LightColorScheme = lightColorScheme(
//    primary = BluePrimary,
//    onPrimary = Color.White,
//
//    secondary = OrangeAccent,
//    onSecondary = Color.White,
//
//    tertiary = GreenSuccess,
//    onTertiary = Color.White,
//
//    error = RedError,
//    onError = Color.White,
//
//    background = LightBackground,
//    onBackground = Color(0xFF0F172A),
//
//    surface = CardSurface,
//    onSurface = Color(0xFF0F172A),
//
//    surfaceVariant = Color(0xFFE2E8F0),
//    onSurfaceVariant = Color(0xFF475569)
//)

private val LightColorScheme = lightColorScheme(
    primary = QuizBluePrimary,
    secondary = QuizOrangeAccent,
    tertiary = QuizBlueSecondary,

    background = QuizLightBackground,
    surface = QuizLightSurface,
    surfaceVariant = QuizLightSurfaceVariant,

    error = QuizError,
    onPrimary = Color.White,
    onSurface = Color(0xFF020617),
)


//private val DarkColorScheme = darkColorScheme(
//    primary = DarkBluePrimary,
//    onPrimary = Color.Black,
//
//    secondary = DarkOrangeAccent,
//    onSecondary = Color.Black,
//
//    tertiary = DarkGreenSuccess,
//    onTertiary = Color.Black,
//
//    error = DarkRedError,
//    onError = Color.Black,
//
//    background = DarkBackground,
//    onBackground = Color(0xFFE5E7EB),
//
//    surface = DarkSurface,
//    onSurface = Color(0xFFE5E7EB),
//
//    surfaceVariant = DarkSurfaceVariant,
//    onSurfaceVariant = Color(0xFFCBD5E1)
//)

private val DarkColorScheme = darkColorScheme(
    primary = QuizDarkBluePrimary,
    secondary = QuizDarkOrangeAccent,
    tertiary = QuizBlueSecondary,

    background = QuizDarkBackground,
    surface = QuizDarkSurface,
    surfaceVariant = QuizDarkSurfaceVariant,

    error = QuizDarkError,
    onPrimary = Color(0xFF020617),
    onSurface = Color(0xFFE5E7EB),
)



@Composable
fun SmartQuizTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}