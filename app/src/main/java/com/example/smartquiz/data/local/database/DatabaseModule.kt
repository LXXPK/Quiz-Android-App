package com.example.smartquiz.data.local.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.smartquiz.data.local.dao.UserDao
import com.example.smartquiz.data.local.dao.quiz.AnswerDao
import com.example.smartquiz.data.local.dao.quiz.OptionDao
import com.example.smartquiz.data.local.dao.quiz.QuestionDao
import com.example.smartquiz.data.local.dao.quiz.QuizAttemptDao
import com.example.smartquiz.data.local.dao.quiz.QuizDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "smart_quiz_db"
        )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)

                    // TEMP: seed one quiz for verification
                    db.execSQL("""
                        INSERT INTO quizzes (quizId, title, category, isActive)
                        VALUES ('test_quiz_1', 'Sample Quiz', 'General', 1)
                    """)
                }
            })
            .build()
    }
    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }
    @Provides fun provideQuizDao(db: AppDatabase): QuizDao = db.quizDao()
    @Provides fun provideQuestionDao(db: AppDatabase): QuestionDao = db.questionDao()
    @Provides fun provideOptionDao(db: AppDatabase): OptionDao = db.optionDao()
    @Provides fun provideQuizAttemptDao(db: AppDatabase): QuizAttemptDao = db.quizAttemptDao()
    @Provides fun provideAnswerDao(db: AppDatabase): AnswerDao = db.answerDao()
}
