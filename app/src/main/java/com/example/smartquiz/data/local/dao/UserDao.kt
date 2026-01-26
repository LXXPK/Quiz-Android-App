package com.example.smartquiz.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.smartquiz.data.local.entity.user.UserEntity

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: UserEntity)

    @Query("SELECT * FROM users")
    suspend fun getAll(): List<UserEntity>
}
