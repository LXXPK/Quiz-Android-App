package com.example.smartquiz.data.local.database

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.smartquiz.data.local.entity.quiz.*

fun seedDatabase(context: Context) {
    CoroutineScope(Dispatchers.IO).launch {

        val db = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "smart_quiz_db"
        )
            .fallbackToDestructiveMigration()
            .build()

        val quizDao = db.quizDao()
        val questionDao = db.questionDao()
        val optionDao = db.optionDao()


        quizDao.insertAll(
            listOf(
                QuizEntity("gen_1", "General Knowledge Basics", "General", true),
                QuizEntity("gen_2", "World Geography", "General", true),
                QuizEntity("gen_3", "Famous Personalities", "General", true),

                QuizEntity("tech_1", "Android Fundamentals", "Tech", true),
                QuizEntity("tech_2", "Programming Basics", "Tech", true),
                QuizEntity("tech_3", "Computer Networks", "Tech", true),

                QuizEntity("sci_1", "Physics Basics", "Science", true),
                QuizEntity("sci_2", "Chemistry Fundamentals", "Science", true),
                QuizEntity("sci_3", "Biology Basics", "Science", true),

                QuizEntity("sport_1", "Cricket World", "Sports", true),
                QuizEntity("sport_2", "Olympics Quiz", "Sports", true),
                QuizEntity("sport_3", "Football Legends", "Sports", true),

                QuizEntity("his_1", "Indian History", "History", true),
                QuizEntity("his_2", "World History", "History", true),
                QuizEntity("his_3", "Ancient Civilizations", "History", true),
            )
        )


        val questions = mutableListOf<QuestionEntity>()

        fun addQuestions(prefix: String, quizId: String, list: List<String>) {
            list.forEachIndexed { index, text ->
                questions.add(
                    QuestionEntity(
                        "${prefix}_q${index + 1}",
                        quizId,
                        text
                    )
                )
            }
        }


        addQuestions("g1", "gen_1", listOf(
            "Capital of India?",
            "Largest ocean in the world?",
            "National bird of India?",
            "How many continents are there?",
            "Currency of Japan?"
        ))

        addQuestions("g2", "gen_2", listOf(
            "Highest mountain in the world?",
            "Longest river in the world?",
            "Smallest country in the world?",
            "Which desert is largest?",
            "Which country has most population?"
        ))

        addQuestions("g3", "gen_3", listOf(
            "Who is known as Father of Nation?",
            "Who invented telephone?",
            "First man on the moon?",
            "Who discovered America?",
            "Who wrote Harry Potter?"
        ))


        addQuestions("t1", "tech_1", listOf(
            "Android is based on which kernel?",
            "Language mainly used for Android development?",
            "What is APK?",
            "Who develops Android?",
            "Android UI toolkit?"
        ))

        addQuestions("t2", "tech_2", listOf(
            "Full form of CPU?",
            "Binary system uses how many digits?",
            "Founder of Microsoft?",
            "What is RAM?",
            "Main language of web?"
        ))

        addQuestions("t3", "tech_3", listOf(
            "What is IP address?",
            "Protocol for web?",
            "Full form of LAN?",
            "Device to connect networks?",
            "What is firewall?"
        ))

        addQuestions("s1", "sci_1", listOf(
            "Unit of force?",
            "Speed of light?",
            "Who discovered gravity?",
            "SI unit of power?",
            "Earth revolves around?"
        ))

        addQuestions("s2", "sci_2", listOf(
            "Chemical formula of water?",
            "Atomic number of Oxygen?",
            "pH value of neutral solution?",
            "Who discovered hydrogen?",
            "Gas used in balloons?"
        ))

        addQuestions("s3", "sci_3", listOf(
            "Largest organ in human body?",
            "Blood group universal donor?",
            "Plant process to make food?",
            "Brain part for balance?",
            "Smallest bone in body?"
        ))

        addQuestions("sp1", "sport_1", listOf(
            "Players in cricket team?",
            "God of Cricket?",
            "World Cup interval?",
            "Highest format in cricket?",
            "First Cricket World Cup year?"
        ))

        addQuestions("sp2", "sport_2", listOf(
            "Olympics held every?",
            "First modern Olympics year?",
            "Symbol of Olympics?",
            "Number of rings?",
            "Host of Tokyo Olympics?"
        ))

        addQuestions("sp3", "sport_3", listOf(
            "Best footballer award?",
            "Country of Messi?",
            "FIFA World Cup interval?",
            "Most World Cups won?",
            "Football field shape?"
        ))

        addQuestions("h1", "his_1", listOf(
            "First Prime Minister of India?",
            "Independence year?",
            "Who built Taj Mahal?",
            "Capital of Mughal Empire?",
            "Who wrote Ramayana?"
        ))

        addQuestions("h2", "his_2", listOf(
            "World War II ended in?",
            "First President of USA?",
            "Who was Napoleon?",
            "Berlin Wall fell in?",
            "Roman Empire capital?"
        ))

        addQuestions("h3", "his_3", listOf(
            "Indus Valley city?",
            "Pyramids are in?",
            "Greek god of war?",
            "First civilization?",
            "Hammurabi was from?"
        ))

        questionDao.insertAll(questions)


        val options = mutableListOf<OptionEntity>()
        var optionIdCounter = 1

        fun addOptions(questionId: String, correct: String, wrong: List<String>) {
            options.add(OptionEntity("o${optionIdCounter++}", questionId, correct, true))
            wrong.forEach {
                options.add(OptionEntity("o${optionIdCounter++}", questionId, it, false))
            }
        }


        val correctAnswers = mapOf(
            "g1_q1" to "Delhi", "g1_q2" to "Pacific", "g1_q3" to "Peacock", "g1_q4" to "7", "g1_q5" to "Yen",
            "g2_q1" to "Mount Everest", "g2_q2" to "Nile", "g2_q3" to "Vatican City", "g2_q4" to "Sahara", "g2_q5" to "China",
            "g3_q1" to "Gandhi", "g3_q2" to "Bell", "g3_q3" to "Neil Armstrong", "g3_q4" to "Columbus", "g3_q5" to "J.K. Rowling",

            "t1_q1" to "Linux", "t1_q2" to "Kotlin", "t1_q3" to "Android Package", "t1_q4" to "Google", "t1_q5" to "Jetpack Compose",
            "t2_q1" to "Central Processing Unit", "t2_q2" to "2", "t2_q3" to "Bill Gates", "t2_q4" to "Memory", "t2_q5" to "HTML",
            "t3_q1" to "Internet address", "t3_q2" to "HTTP", "t3_q3" to "Local Area Network", "t3_q4" to "Router", "t3_q5" to "Security system",

            "s1_q1" to "Newton", "s1_q2" to "3x10^8 m/s", "s1_q3" to "Newton", "s1_q4" to "Watt", "s1_q5" to "Sun",
            "s2_q1" to "H2O", "s2_q2" to "8", "s2_q3" to "7", "s2_q4" to "Cavendish", "s2_q5" to "Helium",
            "s3_q1" to "Skin", "s3_q2" to "O-", "s3_q3" to "Photosynthesis", "s3_q4" to "Cerebellum", "s3_q5" to "Stapes",

            "sp1_q1" to "11", "sp1_q2" to "Sachin Tendulkar", "sp1_q3" to "4", "sp1_q4" to "Test", "sp1_q5" to "1975",
            "sp2_q1" to "4 years", "sp2_q2" to "1896", "sp2_q3" to "Rings", "sp2_q4" to "5", "sp2_q5" to "Japan",
            "sp3_q1" to "Ballon d'Or", "sp3_q2" to "Argentina", "sp3_q3" to "4", "sp3_q4" to "Brazil", "sp3_q5" to "Rectangle",

            "h1_q1" to "Nehru", "h1_q2" to "1947", "h1_q3" to "Shah Jahan", "h1_q4" to "Delhi", "h1_q5" to "Valmiki",
            "h2_q1" to "1945", "h2_q2" to "George Washington", "h2_q3" to "French leader", "h2_q4" to "1989", "h2_q5" to "Rome",
            "h3_q1" to "Harappa", "h3_q2" to "Egypt", "h3_q3" to "Ares", "h3_q4" to "Sumer", "h3_q5" to "Babylon"
        )

        correctAnswers.forEach { (qId, correct) ->
            addOptions(qId, correct, listOf("Option A", "Option B", "Option C"))
        }

        optionDao.insertAll(options)
    }
}
