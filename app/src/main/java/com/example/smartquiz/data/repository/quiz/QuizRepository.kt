package com.example.smartquiz.data.repository.quiz

import androidx.room.Transaction
import com.example.smartquiz.data.local.dao.quiz.*
import com.example.smartquiz.data.local.dao.user.UserDao
import com.example.smartquiz.data.local.entity.quiz.*
import javax.inject.Inject

class QuizRepository @Inject constructor(
    private val quizDao: QuizDao,
    private  val userDao: UserDao,
    private val questionDao: QuestionDao,
    private val optionDao: OptionDao,
    private val quizAttemptDao: QuizAttemptDao,
    private val answerDao: AnswerDao
) {

    suspend fun getQuizById(quizId: String): QuizEntity? =
        quizDao.getQuizById(quizId)

    suspend fun getQuizzesByCategory(category: String): List<QuizEntity> =
        quizDao.getQuizzesByCategory(category)

    suspend fun getQuestionCount(quizId: String): Int =
        questionDao.getQuestionCountForQuiz(quizId)

    suspend fun getCategories(): List<String> =
        quizDao.getCategories()

    suspend fun updateUserStreak(userId: String) {
        val now = System.currentTimeMillis()

        val startOfToday = java.time.LocalDate
            .now()
            .atStartOfDay(java.time.ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        userDao.updateUserStreak(
            userId = userId,
            startOfToday = startOfToday,
            now = now
        )
    }



    suspend fun createQuizAttempt(
        quizId: String,
        userId: String
    ): Int {
        return quizAttemptDao.insertQuizAttempt(
            QuizAttemptEntity(
                quizId = quizId,
                userId = userId,
                score = 0,
                attemptedAt = System.currentTimeMillis()
            )
        ).toInt()
    }

    suspend fun getQuestionsForQuiz(quizId: String) =
        questionDao.getQuestionsForQuiz(quizId)

    suspend fun getOptionsForQuestion(questionId: String) =
        optionDao.getOptionsForQuestion(questionId)

    @Transaction
    suspend fun saveQuizResult(
        attemptId: Int,
        score: Int,
        timeTakenSeconds: Int,
        answers: Map<String, String>
    ) {
        quizAttemptDao.updateScore(attemptId, score)
        quizAttemptDao.updateTimeTaken(attemptId, timeTakenSeconds)

        answerDao.insertAnswers(
            answers.map {
                AnswerEntity(
                    attemptId = attemptId,
                    questionId = it.key,
                    selectedOptionId = it.value
                )
            }
        )
    }
}
