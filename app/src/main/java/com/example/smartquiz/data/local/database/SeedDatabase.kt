package com.example.smartquiz.data.local.database

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.smartquiz.data.local.entity.quiz.*
import com.example.smartquiz.utils.DatabaseConstants.DATABASE_NAME

fun seedDatabase(context: Context) {
    CoroutineScope(Dispatchers.IO).launch {

        val db = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

        val quizDao = db.quizDao()
        val questionDao = db.questionDao()
        val optionDao = db.optionDao()
        val now = System.currentTimeMillis()
        val oneDay = 24 * 60 * 60 * 1000L
        val oneHour = 60 * 60 * 1000L
        val oneMinute = 60 * 1000L
        val twoHour = 2 * 60 * 60 * 1000L
        val twoMinutes = 2 * 60 * 1000L
        quizDao.insertAll(
            listOf(
                // ---------- GENERAL ----------
                QuizEntity("gen_1", "General Knowledge Basics", "General", true, now, now + oneMinute),
                QuizEntity("gen_2", "World Geography", "General", true, now, now + oneHour),
                QuizEntity("gen_3", "Famous Personalities", "General", true, now, now + oneDay),
                QuizEntity("gen_4", "Indian Constitution", "General", true, now, now + oneDay),
                QuizEntity("gen_5", "Current Affairs", "General", true, now, now + oneHour),
                QuizEntity("gen_6", "World Organizations", "General", true, now, now + oneDay),

                // ---------- TECH ----------
                QuizEntity("tech_1", "Android Fundamentals", "Tech", true, now, now + oneDay),
                QuizEntity("tech_2", "Programming Basics", "Tech", true, now, now + oneMinute),
                QuizEntity("tech_3", "Computer Networks", "Tech", true, now, now + oneHour),
                QuizEntity("tech_4", "Operating Systems", "Tech", true, now, now + oneDay),
                QuizEntity("tech_5", "Database Basics", "Tech", true, now, now + oneDay),
                QuizEntity("tech_6", "Web Technologies", "Tech", true, now, now + oneHour),

                // ---------- SCIENCE ----------
                QuizEntity("sci_1", "Physics Basics", "Science", true, now, now + oneDay),
                QuizEntity("sci_2", "Chemistry Fundamentals", "Science", true, now, now + oneMinute),
                QuizEntity("sci_3", "Biology Basics", "Science", true, now, now + oneDay),
                QuizEntity("sci_4", "Human Anatomy", "Science", true, now, now + oneDay),
                QuizEntity("sci_5", "Space Science", "Science", true, now, now + oneHour),
                QuizEntity("sci_6", "Environmental Science", "Science", true, now, now + oneDay),

                // ---------- SPORTS ----------
                QuizEntity("sport_1", "Cricket World", "Sports", true, now, now + oneDay),
                QuizEntity("sport_2", "Olympics Quiz", "Sports", true, now, now + oneDay),
                QuizEntity("sport_3", "Football Legends", "Sports", true, now, now + oneDay),
                QuizEntity("sport_4", "Tennis Champions", "Sports", true, now, now + oneHour),
                QuizEntity("sport_5", "Hockey History", "Sports", true, now, now + oneDay),
                QuizEntity("sport_6", "Olympic Records", "Sports", true, now, now + oneDay),

                // ---------- HISTORY ----------
                QuizEntity("his_1", "Indian History", "History", true, now, now + twoMinutes),
                QuizEntity("his_2", "World History", "History", true, now, now + twoHour),
                QuizEntity("his_3", "Ancient Civilizations", "History", true, now, now + oneHour),
                QuizEntity("his_4", "Medieval India", "History", true, now, now + oneDay),
                QuizEntity("his_5", "Freedom Movements", "History", true, now, now + oneDay),
                QuizEntity("his_6", "Modern World History", "History", true, now, now + oneDay),
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


        addQuestions("g4", "gen_4", listOf(
            "Who is the head of Indian Constitution drafting committee?",
            "How many articles are there?",
            "Fundamental Rights are borrowed from?",
            "Supreme law of India?",
            "Constitution came into force on?"
        ))

        addQuestions("g5", "gen_5", listOf(
            "Current PM of India?",
            "Current President of India?",
            "G20 Summit 2023 host?",
            "ISRO chairman?",
            "Chief Justice of India?"
        ))

        addQuestions("g6", "gen_6", listOf(
            "Full form of UN?",
            "WHO headquarters?",
            "IMF deals with?",
            "World Bank president?",
            "UNICEF works for?"
        ))

        addQuestions("t4", "tech_4", listOf(
            "Main function of operating system?",
            "Which OS is open source?",
            "CPU scheduling is done by?",
            "What is multitasking?",
            "Which OS is developed by Apple?"
        ))

        addQuestions("t5", "tech_5", listOf(
            "DBMS stands for?",
            "Primary key is used for?",
            "Which is a relational database?",
            "SQL is used for?",
            "Which company owns MySQL?"
        ))

        addQuestions("t6", "tech_6", listOf(
            "HTML is used for?",
            "CSS controls?",
            "JavaScript runs on?",
            "Protocol used for web?",
            "Frontend framework?"
        ))
        addQuestions("s4", "sci_4", listOf(
            "Heart pumps which fluid?",
            "Normal human body temperature?",
            "Largest bone in body?",
            "Which organ filters blood?",
            "Respiration occurs in?"
        ))

        addQuestions("s5", "sci_5", listOf(
            "Nearest planet to Sun?",
            "Red planet?",
            "Largest planet?",
            "Earth is in which galaxy?",
            "First satellite of Earth?"
        ))

        addQuestions("s6", "sci_6", listOf(
            "Main cause of pollution?",
            "Gas responsible for greenhouse effect?",
            "Renewable energy source?",
            "Ozone layer protects from?",
            "Plant absorbs which gas?"
        ))



        addQuestions("sp4", "sport_4", listOf(
            "Grand Slam tennis tournaments?",
            "Wimbledon is played on?",
            "Tennis scoring starts from?",
            "Who is known as Tennis legend?",
            "Australian Open held in?"
        ))

        addQuestions("sp5", "sport_5", listOf(
            "National sport of India?",
            "Players in hockey team?",
            "Hockey World Cup interval?",
            "Hockey stick made of?",
            "First Hockey World Cup?"
        ))

        addQuestions("sp6", "sport_6", listOf(
            "Most Olympic gold medals?",
            "Fastest 100m sprinter?",
            "Olympic motto?",
            "First country to host Olympics twice?",
            "Olympic flame symbolises?"
        ))

        addQuestions("h4", "his_4", listOf(
            "Founder of Mughal Empire?",
            "Akbar ruled during?",
            "Capital of Vijayanagara?",
            "Battle of Panipat count?",
            "Who built Red Fort?"
        ))

        addQuestions("h5", "his_5", listOf(
            "Quit India Movement year?",
            "Founder of INA?",
            "Salt Satyagraha leader?",
            "Indian National Congress founded in?",
            "First woman freedom fighter?"
        ))

        addQuestions("h6", "his_6", listOf(
            "Cold War was between?",
            "UNO founded in?",
            "Leader of Russian Revolution?",
            "Industrial Revolution began in?",
            "French Revolution year?"
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
            "h3_q1" to "Harappa", "h3_q2" to "Egypt", "h3_q3" to "Ares", "h3_q4" to "Sumer", "h3_q5" to "Babylon",

            "t4_q1" to "Resource management",
            "t4_q2" to "Linux",
            "t4_q3" to "Operating system",
            "t4_q4" to "Multiple tasks execution",
            "t4_q5" to "macOS",

            "t5_q1" to "Database Management System",
            "t5_q2" to "Uniqueness",
            "t5_q3" to "MySQL",
            "t5_q4" to "Querying data",
            "t5_q5" to "Oracle",

            "t6_q1" to "Web structure",
            "t6_q2" to "Design",
            "t6_q3" to "Browser",
            "t6_q4" to "HTTP",
            "t6_q5" to "React",

            "s4_q1" to "Blood",
            "s4_q2" to "37°C",
            "s4_q3" to "Femur",
            "s4_q4" to "Kidney",
            "s4_q5" to "Lungs",

            "s5_q1" to "Mercury",
            "s5_q2" to "Mars",
            "s5_q3" to "Jupiter",
            "s5_q4" to "Milky Way",
            "s5_q5" to "Moon",

            "s6_q1" to "Human activity",
            "s6_q2" to "Carbon dioxide",
            "s6_q3" to "Solar",
            "s6_q4" to "UV rays",
            "s6_q5" to "Carbon dioxide",

            "sp4_q1" to "4",
            "sp4_q2" to "Grass",
            "sp4_q3" to "Love",
            "sp4_q4" to "Federer",
            "sp4_q5" to "Australia",

            "sp5_q1" to "Hockey",
            "sp5_q2" to "11",
            "sp5_q3" to "4 years",
            "sp5_q4" to "Wood",
            "sp5_q5" to "1971",

            "sp6_q1" to "Michael Phelps",
            "sp6_q2" to "Usain Bolt",
            "sp6_q3" to "Faster Higher Stronger",
            "sp6_q4" to "France",
            "sp6_q5" to "Peace",

            "h4_q1" to "Babur",
            "h4_q2" to "16th century",
            "h4_q3" to "Hampi",
            "h4_q4" to "3",
            "h4_q5" to "Shah Jahan",

            "h5_q1" to "1942",
            "h5_q2" to "Subhash Chandra Bose",
            "h5_q3" to "Gandhi",
            "h5_q4" to "1885",
            "h5_q5" to "Rani Lakshmibai",

            "h6_q1" to "USA and USSR",
            "h6_q2" to "1945",
            "h6_q3" to "Lenin",
            "h6_q4" to "England",
            "h6_q5" to "1789"

        )

        correctAnswers.forEach { (qId, correct) ->
            addOptions(qId, correct, listOf("Option A", "Option B", "Option C"))
        }

        optionDao.insertAll(options)
    }
}
