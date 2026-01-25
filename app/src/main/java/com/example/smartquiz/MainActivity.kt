package com.example.smartquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.smartquiz.data.local.database.AppDatabase
import com.example.smartquiz.data.local.entity.user.UserEntity
import com.example.smartquiz.data.repository.quiz.QuizRepository
//import com.example.smartquiz.navigation.RootNavGraph
import com.example.smartquiz.ui.quiz.QuizDetailsScreen
import com.example.smartquiz.ui.quiz.QuizPlayScreen
import com.example.smartquiz.ui.quiz.QuizResultScreen
import com.example.smartquiz.ui.theme.SmartQuizTheme
import com.example.smartquiz.viewmodel.quiz.QuizDetailsViewModel
import com.example.smartquiz.viewmodel.quiz.QuizPlayViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        val db = AppDatabase.getDatabase(this)
//        android.util.Log.d("DB_CHECK", "DB instance created: $db")
//        lifecycleScope.launch {
//            val userDao = db.userDao()
//
//            userDao.insert(
//                UserEntity(
//                    userId = "test_1",
//                    name = "Test User",
//                    email = "test@gmail.com",
//                    photoUrl = null
//                )
//            )
//
//            val users = userDao.getAll()
//            android.util.Log.d("DB_DATA", "Users count = ${users.size}")
//        }
        setContent {
            SmartQuizTheme {
                val navController = rememberNavController()


                // Dummy Mode Repository
                val repository = remember {
                    QuizRepository(
                        quizDao = null,
                        questionDao = null,
                        quizAttemptDao = null,
                        optionDao = null,
                        answerDao = null,
                        useDummyData = true
                    )
                }

                /* 1. To test for details page
                 * */

                /*
                val quizDetailsViewModel = remember {
                    QuizDetailsViewModel(repository)
                }

                Surface() {
                    QuizDetailsScreen(
                        quizId = "test_quiz_1",
                        userId = "test_1",
                        viewModel = quizDetailsViewModel,
                        navController = navController
                    )
                }*/


                /*
                * 2. To test for result page
                * */

                /*
                // Create QuizPlayViewModel manually
                val quizPlayViewModel = remember {
                    QuizPlayViewModel(repository)
                }


                LaunchedEffect (Unit) {
                    quizPlayViewModel.setDummyResult(
                        totalQuestions = 10,
                        correctAnswers = 7,
                        timeTakenSeconds = 3725 // 01:02:05
                    )
                }


                // Directly show result screen
                QuizResultScreen(
                    viewModel = quizPlayViewModel,
                    onDone = {
                        // no-op for dummy
                    }
                )

                */



                /*
                * 3. To test for play page
                * */


                // Create QuizPlayViewModel
                val quizPlayViewModel = remember {
                    QuizPlayViewModel(repository)
                }


                LaunchedEffect(Unit) {
                    quizPlayViewModel.loadQuiz("test_quiz_1")
                }


                QuizPlayScreen(
                    quizId = "test_quiz_1",
                    attemptId = 1,
                    viewModel = quizPlayViewModel
                )

            }
        }

    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SmartQuizTheme {

    }
}