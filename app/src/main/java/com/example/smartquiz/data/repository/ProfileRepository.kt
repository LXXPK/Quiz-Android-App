package com.example.smartquiz.data.repository

import com.example.smartquiz.data.local.dao.UserDao
import com.example.smartquiz.data.local.entity.user.InterestEntity

class ProfileRepository(
    private val userDao: UserDao
) {

    suspend fun getUserInterests(userId: String): List<InterestEntity> {
        return userDao.getUserInterests(userId)
    }

    suspend fun saveUserInterests(
        userId: String,
        interests: List<String>
    ) {
        // clear old interests
        userDao.deleteUserInterests(userId)

        // insert new interests
        val entities = interests.map {
            InterestEntity(
                userId = userId,
                interest = it
            )
        }
        userDao.insertInterests(entities)
    }
}
