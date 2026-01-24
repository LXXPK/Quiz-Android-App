package com.example.smartquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.smartquiz.data.local.dao.UserDao
import com.example.smartquiz.data.local.entity.user.UserEntity
import com.example.smartquiz.navigation.RootNavGraph
import com.example.smartquiz.ui.theme.SmartQuizTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        android.util.Log.d("DB_CHECK", "User DAO = $userDao")
        lifecycleScope.launch {
            userDao.insert(
                UserEntity(
                    userId = "test_1",
                    name = "Test User",
                    email = "test@gmail.com",
                    photoUrl = null
                )
            )

            val users = userDao.getAll()
            android.util.Log.d("DB_DATA", "Users count = ${users.size}")
        }

        setContent {
            SmartQuizTheme {
                val navController = rememberNavController()
                RootNavGraph(navController)
            }
        }
    }
}
