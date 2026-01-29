package com.example.smartquiz.data.repository.home

import com.example.smartquiz.data.local.dao.quiz.QuizDao
import com.example.smartquiz.data.local.dao.user.InterestDao
import com.example.smartquiz.data.local.dao.user.UserDao
import com.example.smartquiz.data.local.entity.quiz.QuizEntity
import com.example.smartquiz.data.local.entity.user.InterestEntity
import com.example.smartquiz.data.local.entity.user.UserEntity
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val userDao: UserDao,
    private val quizDao: QuizDao,
    private val userInterestDao: InterestDao
) {

    suspend fun getUser(uid: String): UserEntity? {
        return userDao.getUserById(uid)
    }

    suspend fun getUserInterests(uid: String): List<InterestEntity> {
        return userInterestDao.getInterestsByUserId(uid)
    }

    suspend fun getAllQuizzes(): List<QuizEntity> {
        return quizDao.getAllQuizzes()
    }

    suspend fun getActiveQuizzes(): List<QuizEntity> {
        return quizDao.getActiveQuizzes()
    }

    suspend fun updateStreak(uid: String, streak: Int, lastDate: Long) {
        userDao.updateStreak(uid, streak, lastDate)
    }
}