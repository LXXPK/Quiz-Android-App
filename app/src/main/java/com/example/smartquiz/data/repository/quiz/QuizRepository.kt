package com.example.smartquiz.data.repository.quiz

import com.example.smartquiz.data.local.dao.quiz.*
import com.example.smartquiz.data.local.entity.quiz.*
import javax.inject.Inject

/*
 * QuizRepository is the SINGLE source of truth for quiz-related data.
 *
 * Responsibilities:
 * - Decide whether data comes from Dummy mode or Room DB
 * - Hide data source details from ViewModels
 * - Persist quiz attempts, answers, and scores
 *
 * IMPORTANT:
 * Switching between Dummy and Real DB requires changing ONLY `useDummyData`.
 */
class QuizRepository @Inject constructor(
    private val quizDao: QuizDao?,
    private val questionDao: QuestionDao?,
    private val quizAttemptDao: QuizAttemptDao?,
    private val optionDao: OptionDao?,
    private val answerDao: AnswerDao?,
    private val useDummyData: Boolean = false
) {

    /* ---------------- QUIZ METADATA ---------------- */

    suspend fun getQuizById(quizId: String): QuizEntity? {
        return if (useDummyData) {
            QuizEntity(
                quizId = quizId,
                title = "Dummy Quiz",
                category = "General",
                isActive = true
            )
        } else {
            quizDao!!.getQuizById(quizId)
        }
    }

    suspend fun getQuestionCount(quizId: String): Int {
        return if (useDummyData) {
            2
        } else {
            questionDao!!.getQuestionCountForQuiz(quizId)
        }
    }

    /* ---------------- QUIZ ATTEMPT ---------------- */

    /*
     * Creates a new quiz attempt when user starts a quiz.
     *
     * timeTakenSeconds is initialized to 0
     * and updated only after submission.
     */
    suspend fun createQuizAttempt(
        quizId: String,
        userId: String
    ): Int {
        return if (useDummyData) {
            // Dummy attempt id for development/testing
            1
        } else {
            val attempt = QuizAttemptEntity(
                quizId = quizId,
                userId = userId,
                score = 0,
                attemptedAt = System.currentTimeMillis(),
                timeTakenSeconds = 0
            )
            quizAttemptDao!!.insertQuizAttempt(attempt).toInt()
        }
    }

    /*
     * Updates final score and time spent after quiz submission.
     */
    suspend fun updateQuizAttemptResult(
        attemptId: Int,
        score: Int,
        timeTakenSeconds: Int
    ) {
        if (useDummyData) return

        quizAttemptDao!!.updateScore(attemptId, score)
        quizAttemptDao.updateTimeTaken(attemptId, timeTakenSeconds)
    }

    /* ---------------- QUESTIONS ---------------- */

    suspend fun getQuestionsForQuiz(
        quizId: String
    ): List<QuestionEntity> {
        return if (useDummyData) {
            listOf(
                QuestionEntity("q1", quizId, "What is the capital of France?"),
                QuestionEntity("q2", quizId, "Which language is used for Android?")
            )
        } else {
            questionDao!!.getQuestionsForQuiz(quizId)
        }
    }

    /* ---------------- OPTIONS ---------------- */

    suspend fun getOptionsForQuestion(
        questionId: String
    ): List<OptionEntity> {
        return if (useDummyData) {
            when (questionId) {
                "q1" -> listOf(
                    OptionEntity("o1", "q1", "Paris", true),
                    OptionEntity("o2", "q1", "London", false),
                    OptionEntity("o3", "q1", "Berlin", false),
                    OptionEntity("o4", "q1", "Rome", false)
                )
                "q2" -> listOf(
                    OptionEntity("o5", "q2", "Kotlin", true),
                    OptionEntity("o6", "q2", "Python", false),
                    OptionEntity("o7", "q2", "Swift", false),
                    OptionEntity("o8", "q2", "JavaScript", false)
                )
                else -> emptyList()
            }
        } else {
            optionDao!!.getOptionsForQuestion(questionId)
        }
    }

    /* ---------------- ANSWERS + RESULT ---------------- */

    /*
     * Persists final quiz result after submission.
     *
     * This method:
     * 1. Updates score + timeTakenSeconds
     * 2. Saves user-selected answers
     *
     * IMPORTANT:
     * - Correct / wrong is NOT stored
     * - It can be derived later
     */
    suspend fun saveQuizResult(
        attemptId: Int,
        score: Int,
        timeTakenSeconds: Int,
        answers: Map<String, String>
    ) {
        if (useDummyData) return

        // Update attempt result
        updateQuizAttemptResult(
            attemptId = attemptId,
            score = score,
            timeTakenSeconds = timeTakenSeconds
        )

        // Persist answers
        val answerEntities = answers.map { (questionId, optionId) ->
            AnswerEntity(
                attemptId = attemptId,
                questionId = questionId,
                selectedOptionId = optionId
            )
        }

        answerDao!!.insertAnswers(answerEntities)
    }
}