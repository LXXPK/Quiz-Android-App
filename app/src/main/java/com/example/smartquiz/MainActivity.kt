package com.example.smartquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.smartquiz.data.local.database.AppDatabase
import com.example.smartquiz.data.local.entity.user.UserEntity
import com.example.smartquiz.ui.theme.SmartQuizTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val db = AppDatabase.getDatabase(this)
        android.util.Log.d("DB_CHECK", "DB instance created: $db")
        lifecycleScope.launch {
            val userDao = db.userDao()

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
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SmartQuizTheme {
        Greeting("Android")
    }
}