package com.example.smartquiz.data.local.entity.quiz

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quizzes")
data class QuizEntity(
    @PrimaryKey val quizId: String,
    val title: String,
    val category: String,
    val isActive: Boolean,
    val startTime: Long,
    val endTime: Long
)
