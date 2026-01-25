package com.example.smartquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.smartquiz.ui.quiz.QuizDetailsScreen
import com.example.smartquiz.ui.theme.SmartQuizTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * MainActivity
 *
 * Responsibilities:
 * - Host Compose UI
 * - Provide NavController
 * - NOTHING else
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SmartQuizTheme {
                AppEntry()
            }
        }
    }
}

/**
 * Temporary entry composable.
 * (QuizList will replace this later)
 */
@Composable
fun AppEntry() {
    val navController = rememberNavController()

    // Direct entry into QuizDetails using REAL DB + REAL ViewModel
    QuizDetailsScreen(
        quizId = "test_quiz_1",
        userId = "test_1",
        navController = navController
    )
}