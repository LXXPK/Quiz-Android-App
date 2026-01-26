package com.example.smartquiz.data.local.entity.quiz

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class QuestionEntity(
    @PrimaryKey val questionId: String,
    val quizId: String,
    val questionText: String
)
