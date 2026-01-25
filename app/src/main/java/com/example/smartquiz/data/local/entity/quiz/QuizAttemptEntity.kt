package com.example.smartquiz.data.local.entity.quiz

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_attempts")
data class QuizAttemptEntity(
    @PrimaryKey(autoGenerate = true)
    val attemptId: Int = 0,
    val quizId: String,
    val userId: String,
    val score: Int,
    val attemptedAt: Long,

    // ⏱️ How long the user spent on this attempt (in seconds)
    val timeTakenSeconds: Int
)
