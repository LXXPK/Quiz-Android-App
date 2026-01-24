package com.example.smartquiz.data.repository.quiz

import com.example.smartquiz.data.local.dao.quiz.*
import com.example.smartquiz.data.local.entity.quiz.*

/*
 * QuizRepository is the SINGLE source of truth for quiz-related data.
 *
 * Responsibilities:
 * - Decide whether data comes from Dummy mode or Room DB
 * - Hide data source details from ViewModels
 * - Enforce quiz-related business rules (not UI logic)
 *
 * IMPORTANT:
 * Switching between Dummy and Real DB requires changing ONLY `useDummyData`.
 */
class QuizRepository(
    private val quizDao: QuizDao?,
    private val questionDao: QuestionDao?,
    private val quizAttemptDao: QuizAttemptDao?,
    private val optionDao: OptionDao?,
    private val answerDao: AnswerDao?,
    private val useDummyData: Boolean = false
) {

    /*
     * Fetch quiz metadata (title, category, active state).
     * Used by QuizDetails screen.
     */
    suspend fun getQuizById(quizId: String): QuizEntity? {
        return if (useDummyData) {
            // Dummy quiz definition for development/testing
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

    /*
     * Returns total number of questions in a quiz.
     * Used only for display purposes (e.g., "10 Questions").
     */
    suspend fun getQuestionCount(quizId: String): Int {
        return if (useDummyData) {
            2
        } else {
            questionDao!!.getQuestionCountForQuiz(quizId)
        }
    }

    /*
     * Creates a new quiz attempt when user starts a quiz.
     *
     * Returns:
     * - attemptId (primary key)
     *
     * NOTE:
     * Score is initialized to 0 and updated only after submission.
     */
    suspend fun createQuizAttempt(
        quizId: String,
        userId: String
    ): Int {
        return if (useDummyData) {
            // Dummy attempt id for development
            1
        } else {
            val attempt = QuizAttemptEntity(
                quizId = quizId,
                userId = userId,
                score = 0,
                attemptedAt = System.currentTimeMillis()
            )
            quizAttemptDao!!.insertQuizAttempt(attempt).toInt()
        }
    }

    /*
     * Fetches all questions belonging to a quiz.
     * Questions themselves are neutral (no correctness info here).
     */
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

    /*
     * Fetches all options for a given question.
     * Correctness information is stored ONLY here.
     */
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

    /*
     * Persists final quiz result after submission.
     *
     * This method:
     * 1. Updates score in QuizAttemptEntity
     * 2. Saves user-selected answers
     *
     * IMPORTANT DESIGN RULE:
     * - Correct / wrong is NOT stored
     * - It can be derived later using AnswerEntity + OptionEntity
     */
    suspend fun saveQuizResult(
        attemptId: Int,
        score: Int,
        answers: Map<String, String>
    ) {
        if (useDummyData) {
            // No-op in dummy mode (nothing to persist)
            return
        }

        // Update final score
        quizAttemptDao!!.updateScore(attemptId, score)

        // Convert in-memory answers to entities
        val answerEntities = answers.map { (questionId, optionId) ->
            AnswerEntity(
                attemptId = attemptId,
                questionId = questionId,
                selectedOptionId = optionId
            )
        }

        // Persist answers in a single operation
        answerDao!!.insertAnswers(answerEntities)
    }
}