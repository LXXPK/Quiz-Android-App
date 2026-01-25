package com.example.smartquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smartquiz.ui.auth.AuthViewModel
import com.example.smartquiz.ui.auth.LoginScreen
import com.example.smartquiz.ui.home.HomeScreen
import com.example.smartquiz.ui.theme.SmartQuizTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SmartQuizTheme {

                val authViewModel: AuthViewModel = hiltViewModel()

                var isLoggedIn by remember {
                    mutableStateOf(authViewModel.getUid() != null)
                }

                if (isLoggedIn) {
                    HomeScreen(
                        userEmail = null,
                        onLogout = {
                            authViewModel.logout()
                            isLoggedIn = false
                        }
                    )
                } else {
                    LoginScreen(
                        authViewModel = authViewModel,
                        onLoginSuccess = {
                            isLoggedIn = true
                        }
                    )
                }
            }
        }
    }
}




//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    SmartQuizTheme {
//
//    }
//}