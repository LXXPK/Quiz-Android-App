package com.example.smartquiz.data.local.dao.quiz

import androidx.room.Dao
import androidx.room.Query
import com.example.smartquiz.data.local.entity.quiz.QuizEntity

@Dao
interface QuizDao {

    @Query("SELECT * FROM quizzes WHERE quizId = :quizId")
    suspend fun getQuizById(quizId: String): QuizEntity?
}