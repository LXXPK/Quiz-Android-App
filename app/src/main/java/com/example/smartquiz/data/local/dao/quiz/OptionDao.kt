package com.example.smartquiz.data.local.dao.quiz

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.smartquiz.data.local.entity.quiz.OptionEntity

@Dao
interface OptionDao {

    @Query("SELECT * FROM options WHERE questionId = :questionId")
    suspend fun getOptionsForQuestion(questionId: String): List<OptionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(options: List<OptionEntity>)

}