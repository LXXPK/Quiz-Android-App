package com.example.smartquiz.data.local.dao.quiz

import androidx.room.Dao
import androidx.room.Query
import com.example.smartquiz.data.local.entity.quiz.QuestionEntity

@Dao
interface QuestionDao {

    @Query("SELECT COUNT(*) FROM questions WHERE quizId = :quizId")
    suspend fun getQuestionCountForQuiz(quizId: String): Int

    @Query("SELECT * FROM questions WHERE quizId = :quizId")
    suspend fun getQuestionsForQuiz(quizId: String): List<QuestionEntity>
}