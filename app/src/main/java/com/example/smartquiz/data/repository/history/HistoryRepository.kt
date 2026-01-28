package com.example.smartquiz.data.repository.history

import com.example.smartquiz.data.local.dao.quiz.QuizAttemptDao
import com.example.smartquiz.data.local.entity.quiz.QuizAttemptEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HistoryRepository @Inject constructor(
    private val quizAttemptDao: QuizAttemptDao
) {

    suspend fun getUserHistory(userId: String): List<QuizAttemptEntity> {
        return quizAttemptDao.getAttemptsForUser(userId)
    }
}