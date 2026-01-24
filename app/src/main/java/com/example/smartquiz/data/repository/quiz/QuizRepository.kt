package com.example.smartquiz.data.repository.quiz

import com.example.smartquiz.data.local.dao.quiz.AnswerDao
import com.example.smartquiz.data.local.dao.quiz.OptionDao
import com.example.smartquiz.data.local.dao.quiz.QuestionDao
import com.example.smartquiz.data.local.dao.quiz.QuizAttemptDao
import com.example.smartquiz.data.local.dao.quiz.QuizDao
import com.example.smartquiz.data.local.entity.quiz.AnswerEntity
import com.example.smartquiz.data.local.entity.quiz.OptionEntity
import com.example.smartquiz.data.local.entity.quiz.QuestionEntity
import com.example.smartquiz.data.local.entity.quiz.QuizAttemptEntity
import com.example.smartquiz.data.local.entity.quiz.QuizEntity

class QuizRepository(
    private val quizDao: QuizDao? = null,
    private val questionDao: QuestionDao? = null,
    private val quizAttemptDao: QuizAttemptDao? = null,
    private val optionDao: OptionDao? = null,
    private val answerDao: AnswerDao? = null
) {

    suspend fun getQuizById(quizId: String): QuizEntity? {
        return quizDao?.getQuizById(quizId)
    }

    suspend fun getQuestionCount(quizId: String): Int {
        return questionDao!!.getQuestionCountForQuiz(quizId)
    }

    suspend fun createQuizAttempt(
        quizId: String,
        userId: String
    ): Int {
        val attempt = QuizAttemptEntity(
            quizId = quizId,
            userId = userId,
            score = 0, // initial score
            attemptedAt = System.currentTimeMillis()
        )

        val attemptId = quizAttemptDao?.insertQuizAttempt(attempt)
        return attemptId!!.toInt()
    }

    suspend fun getQuestionsForQuiz(
        quizId: String
    ): List<QuestionEntity> {
//        return questionDao.getQuestionsForQuiz(quizId)

        // dummy questions
        return listOf(
            QuestionEntity(
                questionId = "q1",
                quizId = quizId,
                questionText = "What is the capital of France?"
            ),
            QuestionEntity(
                questionId = "q2",
                quizId = quizId,
                questionText = "Which language is used for Android?"
            )
        )
    }

    suspend fun getOptionsForQuestion(
        questionId: String
    ): List<OptionEntity> {
//        return optionDao.getOptionsForQuestion(questionId)

        // dummy options
        return when (questionId) {
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

    }


    suspend fun saveQuizResult(
        attemptId: Int,
        score: Int,
        answers: Map<String, String>
    ) {
        // 1. Update score in quiz_attempts
        quizAttemptDao?.updateScore(
            attemptId = attemptId,
            score = score
        )

        // 2. Convert answers map → AnswerEntity list
        val answerEntities = answers.map { (questionId, optionId) ->
            AnswerEntity(
                attemptId = attemptId,
                questionId = questionId,
                selectedOptionId = optionId
            )
        }

        // 3. Save answers
        answerDao?.insertAnswers(answerEntities)
    }
}