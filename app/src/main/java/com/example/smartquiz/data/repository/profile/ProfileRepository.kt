package com.example.smartquiz.data.repository.profile

import com.example.smartquiz.data.local.dao.quiz.QuizDao
import com.example.smartquiz.data.local.dao.user.InterestDao
import com.example.smartquiz.data.local.dao.user.UserDao
import com.example.smartquiz.data.local.entity.user.InterestEntity
import com.example.smartquiz.data.local.entity.user.UserEntity
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val interestDao: InterestDao,
    private val userDao: UserDao,
    private val quizDao: QuizDao
) {

    suspend fun getAvailableCategories(): List<String> {
        return quizDao.getDistinctCategories()
    }

    suspend fun getUserInterests(userId: String): List<InterestEntity> {
        return interestDao.getInterestsByUser(userId)
    }

    suspend fun saveUserInterests(
        userId: String,
        interests: List<String>
    ) {
        interestDao.deleteUserInterests(userId)

        val entities = interests.map {
            InterestEntity(
                userId = userId,
                interest = it
            )
        }
        interestDao.insertInterests(entities)
    }

    suspend fun getUserProfile(userId: String): UserEntity? {
        return userDao.getUserById(userId)
    }
}