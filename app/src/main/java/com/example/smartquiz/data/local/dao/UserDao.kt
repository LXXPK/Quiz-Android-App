package com.example.smartquiz.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.smartquiz.data.local.entity.user.UserEntity
import com.example.smartquiz.data.local.entity.user.InterestEntity

@Dao
interface UserDao {

    /* ---------- USER ---------- */
    @Insert
    suspend fun insert(user: UserEntity)

    @Query("SELECT * FROM users")
    suspend fun getAll(): List<UserEntity>


    /* ---------- PROFILE / INTERESTS ---------- */

    @Query("SELECT * FROM interests WHERE userId = :userId")
    suspend fun getUserInterests(userId: String): List<InterestEntity>

    @Query("DELETE FROM interests WHERE userId = :userId")
    suspend fun deleteUserInterests(userId: String)

    @Insert
    suspend fun insertInterests(interests: List<InterestEntity>)
}
