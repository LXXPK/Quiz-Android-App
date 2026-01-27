package com.example.smartquiz.data.local.database

import androidx.compose.material3.Text
import com.example.smartquiz.data.local.entity.quiz.*
import com.example.smartquiz.data.local.database.AppDatabase

suspend fun seedDatabase(db: AppDatabase) {

    val quizDao = db.quizDao()
    val questionDao = db.questionDao()
    val optionDao = db.optionDao()

    // ---------- QUIZZES ----------
    quizDao.insertAll(
        listOf(
            QuizEntity("gen_1", "General Knowledge Basics", "General", true),
            QuizEntity("gen_2", "World Geography", "General", true),
            QuizEntity("gen_3", "Famous Personalities", "General", true)
        )
    )

    val questions = listOf(
        QuestionEntity("g1_q1", "gen_1", "Capital of India?"),
        QuestionEntity("g1_q2", "gen_1", "Largest ocean?")
    )

    questionDao.insertAll(questions)

    val options = listOf(
        // g1_q1
        OptionEntity("o1", "g1_q1", "Delhi", true),
        OptionEntity("o2", "g1_q1", "Mumbai", false),
        OptionEntity("o3", "g1_q1", "Chennai", false),

        // g1_q2
        OptionEntity("o4", "g1_q2", "Pacific", true),
        OptionEntity("o5", "g1_q2", "Atlantic", false),
        OptionEntity("o6", "g1_q2", "Indian", false)
    )

    optionDao.insertAll(options)
}