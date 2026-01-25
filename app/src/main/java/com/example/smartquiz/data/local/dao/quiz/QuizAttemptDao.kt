package com.example.smartquiz.data.local.dao.quiz

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.smartquiz.data.local.entity.quiz.QuizAttemptEntity

@Dao
interface QuizAttemptDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizAttempt(
        quizAttempt: QuizAttemptEntity
    ): Long

    @Query("UPDATE quiz_attempts SET score = :score WHERE attemptId = :attemptId")
    suspend fun updateScore(attemptId: Int, score: Int)

    @Query("UPDATE quiz_attempts SET timeTakenSeconds = :timeTaken WHERE attemptId = :attemptId")
    suspend fun updateTimeTaken(
        attemptId: Int,
        timeTaken: Int
    )
}