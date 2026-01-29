package com.example.smartquiz.data.local.entity.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val userId: String,
    val name: String,
    val email: String,
    val photoUrl: String?,
    val currentStreak: Int = 0,
    val lastActivityDate: Long = 0
)
