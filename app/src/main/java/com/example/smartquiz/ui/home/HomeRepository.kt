package com.example.smartquiz.ui.home

import com.example.smartquiz.data.local.dao.quiz.QuizDao
import com.example.smartquiz.data.local.dao.user.UserDao
import com.example.smartquiz.data.local.entity.quiz.QuizEntity
import com.example.smartquiz.data.local.entity.user.UserEntity
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val userDao: UserDao,
    private val quizDao: QuizDao
) {

    suspend fun getUser(uid: String): UserEntity? {
        return userDao.getUserById(uid)
    }

    suspend fun getSuggestedQuizzes(): List<QuizEntity> {
        // you can refine later (difficulty, interests, etc.)
        return quizDao.getAllQuizzes()
    }

    suspend fun getActiveQuizzes(): List<QuizEntity> {
        return quizDao.getActiveQuizzes()
    }
}
