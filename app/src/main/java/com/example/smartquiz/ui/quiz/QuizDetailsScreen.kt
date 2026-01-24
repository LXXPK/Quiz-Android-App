package com.example.smartquiz.ui.quiz

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.smartquiz.viewmodel.quiz.QuizDetailsViewModel

/*
@Composable
fun QuizDetailsScreen(
    quizId: String,
    userId: String,
    viewModel: QuizDetailsViewModel,
    navController: NavController
) {
    val attemptId by viewModel.attemptId.collectAsState()

    Column() {
        Button (
            onClick = {
                viewModel.onStartQuizClicked(
                    quizId = quizId,
                    userId = userId
                )
            }
        ) {
            Text("Start Now")
        }
    }

    // 👇 THIS is where your code goes
    LaunchedEffect(attemptId) {
        if (attemptId != null) {
            navController.navigate(
                "quiz_play/${quizId}/${attemptId}"
            )
        }
    }
}


/*
Button(onClick = {
    viewModel.onStartQuizClicked(
        quizId = quizId,
        userId = userId
    )
}) {
    Text("Start Now")
}

LaunchedEffect(attemptId) {
    if (attemptId != null) {
        // navigate
    }
}
*/


 */

@Composable
fun QuizDetailsScreen(
    quizId: String,
    userId: String
) {
    val context = LocalContext.current

    Column {
        Text("Quiz Details")
        Text("Quiz ID: $quizId")
        Text("User ID: $userId")

        Button(onClick = {
            // TEMP navigation without NavController
            (context as ComponentActivity).setContent {
                QuizPlayScreen(
                    quizId = quizId
                )
            }
        }) {
            Text("Start Now")
        }
    }
}