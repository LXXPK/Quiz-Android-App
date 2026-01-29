package com.example.smartquiz.data.local.dao.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.smartquiz.data.local.entity.user.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE userId = :uid")
    suspend fun getUserById(uid: String): UserEntity?

    @Query("UPDATE users SET currentStreak = :streak, lastActivityDate = :lastDate WHERE userId = :uid")
    suspend fun updateStreak(uid: String, streak: Int, lastDate: Long)
}
