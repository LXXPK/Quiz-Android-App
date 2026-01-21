package com.example.smartquiz.data.local.entity.quiz

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "answers")
data class AnswerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val attemptId: Int,
    val questionId: String,
    val selectedOptionId: String
)
