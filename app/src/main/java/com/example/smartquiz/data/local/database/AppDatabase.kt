package com.example.smartquiz.data.local.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.smartquiz.data.local.dao.user.InterestDao
import com.example.smartquiz.data.local.dao.user.UserDao
import com.example.smartquiz.data.local.dao.quiz.AnswerDao
import com.example.smartquiz.data.local.dao.quiz.OptionDao
import com.example.smartquiz.data.local.dao.quiz.QuestionDao
import com.example.smartquiz.data.local.dao.quiz.QuizAttemptDao
import com.example.smartquiz.data.local.dao.quiz.QuizDao
import com.example.smartquiz.data.local.entity.user.UserEntity
import com.example.smartquiz.data.local.entity.user.InterestEntity
import com.example.smartquiz.data.local.entity.quiz.QuizEntity
import com.example.smartquiz.data.local.entity.quiz.AnswerEntity
import com.example.smartquiz.data.local.entity.quiz.OptionEntity
import com.example.smartquiz.data.local.entity.quiz.QuestionEntity
import com.example.smartquiz.data.local.entity.quiz.QuizAttemptEntity

@Database(
    entities = [
        UserEntity::class,
        InterestEntity::class,
        QuizEntity::class,
        QuestionEntity::class,
        OptionEntity::class,
        QuizAttemptEntity::class,
        AnswerEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun interestDao(): InterestDao

    abstract fun quizDao(): QuizDao
    abstract fun questionDao(): QuestionDao
    abstract fun optionDao(): OptionDao
    abstract fun quizAttemptDao(): QuizAttemptDao
    abstract fun answerDao(): AnswerDao

}

