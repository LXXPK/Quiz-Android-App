package com.example.smartquiz.data.local.dao.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.smartquiz.data.local.entity.user.InterestEntity

@Dao
interface InterestDao {

    @Query("SELECT * FROM interests WHERE userId = :uid")
    suspend fun getInterestsByUser(uid: String): List<InterestEntity>

    @Query("DELETE FROM interests WHERE userId = :uid")
    suspend fun deleteUserInterests(uid: String)

    @Insert
    suspend fun insertInterests(interests: List<InterestEntity>)
    @Query("SELECT * FROM interests WHERE userId = :userId")
    suspend fun getInterestsByUserId(userId: String): List<InterestEntity>
}
