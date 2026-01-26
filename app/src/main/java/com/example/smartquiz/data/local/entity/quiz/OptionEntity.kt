package com.example.smartquiz.data.local.entity.quiz

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "options")
data class OptionEntity(
    @PrimaryKey val optionId: String,
    val questionId: String,
    val optionText: String,
    val isCorrect: Boolean
)
