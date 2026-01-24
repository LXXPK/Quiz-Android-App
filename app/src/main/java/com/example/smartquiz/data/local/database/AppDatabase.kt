package com.example.smartquiz.data.local.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.smartquiz.data.local.dao.UserDao
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
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "smart_quiz_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
