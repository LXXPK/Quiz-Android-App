package com.example.smartquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.smartquiz.ui.home.HomeScreen
import com.example.smartquiz.ui.auth.AuthViewModel
import com.example.smartquiz.ui.auth.AuthViewModelFactory
import com.example.smartquiz.ui.auth.LoginScreen
import com.example.smartquiz.ui.theme.SmartQuizTheme

class MainActivity : ComponentActivity() {

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        authViewModel = ViewModelProvider(
            this,
            AuthViewModelFactory(applicationContext)
        )[AuthViewModel::class.java]

        setContent {
            SmartQuizTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {



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
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SmartQuizTheme {

    }
}