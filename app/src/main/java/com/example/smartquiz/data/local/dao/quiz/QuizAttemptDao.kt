package com.example.smartquiz.data.local.dao.quiz

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.smartquiz.data.local.entity.quiz.QuizAttemptEntity

@Dao
interface QuizAttemptDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizAttempt(
        quizAttempt: QuizAttemptEntity
    ): Long
}