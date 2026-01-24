package com.example.smartquiz.data.repository

import com.example.smartquiz.data.local.dao.UserDao
import com.example.smartquiz.data.local.entity.user.UserEntity
import javax.inject.Inject
import javax.inject.Singleton

interface UserRepository {
    suspend fun insertUser(user: UserEntity)
    suspend fun getAllUsers(): List<UserEntity>
}

@Singleton
class OfflineUserRepository @Inject constructor(
    private val userDao: UserDao
) : UserRepository {
    override suspend fun insertUser(user: UserEntity) = userDao.insert(user)
    override suspend fun getAllUsers(): List<UserEntity> = userDao.getAll()
}
