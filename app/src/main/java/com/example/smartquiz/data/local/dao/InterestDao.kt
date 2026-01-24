package com.example.smartquiz.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.smartquiz.data.local.entity.user.InterestEntity

@Dao
interface InterestDao {

    @Insert
    suspend fun insertInterest(interest: InterestEntity)

    @Query("SELECT * FROM interests WHERE userId = :uid")
    suspend fun getInterestsByUser(uid: String): List<InterestEntity>
}
