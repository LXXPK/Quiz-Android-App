package com.example.smartquiz.data.local.dao.quiz

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.smartquiz.data.local.entity.quiz.QuizEntity

@Dao
interface QuizDao {

    @Query("SELECT * FROM quizzes WHERE quizId = :quizId")
    suspend fun getQuizById(quizId: String): QuizEntity?

    @Query("""
        SELECT * FROM quizzes
        WHERE category = :category
        AND isActive = 1
    """)
    suspend fun getQuizzesByCategory(category: String): List<QuizEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(quizzes: List<QuizEntity>)

    @Query("SELECT DISTINCT category FROM quizzes WHERE isActive = 1")
    suspend fun getCategories(): List<String>

    @Query("SELECT * FROM quizzes")
    suspend fun getAllQuizzes(): List<QuizEntity>

    @Query("SELECT * FROM quizzes WHERE isActive = 1")
    suspend fun getActiveQuizzes(): List<QuizEntity>



}
