package com.example.smartquiz.data.repository.quiz

import androidx.room.Transaction
import com.example.smartquiz.data.local.dao.quiz.*
import com.example.smartquiz.data.local.entity.quiz.*
import javax.inject.Inject

/**
 * QuizRepository
 *
 * This is the SINGLE source of truth for quiz data.
 *
 * Responsibilities:
 * - Fetch quizzes, questions, options
 * - Create quiz attempts
 * - Save answers, score and time
 *
 * IMPORTANT:
 * - No dummy logic
 * - No UI logic
 * - No correctness calculation
 */
class QuizRepository @Inject constructor(
    private val quizDao: QuizDao,
    private val questionDao: QuestionDao,
    private val optionDao: OptionDao,
    private val quizAttemptDao: QuizAttemptDao,
    private val answerDao: AnswerDao
) {

    /* ---------- QUIZ METADATA ---------- */

    suspend fun getQuizById(quizId: String): QuizEntity? {
        return quizDao.getQuizById(quizId)
    }

    suspend fun getQuestionCount(quizId: String): Int {
        return questionDao.getQuestionCountForQuiz(quizId)
    }

    /* ---------- QUIZ ATTEMPT ---------- */

    /**
     * Called when user clicks "Start Quiz".
     * Creates a fresh attempt with score = 0.
     */
    suspend fun createQuizAttempt(
        quizId: String,
        userId: String
    ): Int {
        val attempt = QuizAttemptEntity(
            quizId = quizId,
            userId = userId,
            score = 0,
            attemptedAt = System.currentTimeMillis(),
            timeTakenSeconds = 0
        )

        return quizAttemptDao.insertQuizAttempt(attempt).toInt()
    }

    /* ---------- QUESTIONS & OPTIONS ---------- */

    suspend fun getQuestionsForQuiz(quizId: String): List<QuestionEntity> {
        return questionDao.getQuestionsForQuiz(quizId)
    }

    suspend fun getOptionsForQuestion(questionId: String): List<OptionEntity> {
        return optionDao.getOptionsForQuestion(questionId)
    }

    /* ---------- SAVE RESULT ---------- */

    /**
     * Called once when user submits the quiz.
     * To have strict consistency
     *
     * Saves:
     * - final score
     * - time spent
     * - selected answers
     */

    @Transaction
    suspend fun saveQuizResult(
        attemptId: Int,
        score: Int,
        timeTakenSeconds: Int,
        answers: Map<String, String>
    ) {
        // Update attempt summary
        quizAttemptDao.updateScore(attemptId, score)
        quizAttemptDao.updateTimeTaken(attemptId, timeTakenSeconds)

        // Persist user answers
        val answerEntities = answers.map { (questionId, optionId) ->
            AnswerEntity(
                attemptId = attemptId,
                questionId = questionId,
                selectedOptionId = optionId
            )
        }

        answerDao.insertAnswers(answerEntities)
    }
}