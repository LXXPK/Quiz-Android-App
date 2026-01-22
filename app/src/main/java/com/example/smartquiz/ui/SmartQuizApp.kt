package com.example.smartquiz.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.smartquiz.R
import com.example.smartquiz.ui.theme.SmartQuizTheme

enum class QuizScreen(@StringRes val title: Int) {
    Login(title = R.string.login_title),
    Home(title = R.string.home_title),
    Quiz(title = R.string.quiz_title),
    Profile(title = R.string.profile_title),
    History(title = R.string.history_title)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmartQuizAppBar(
    currentScreen: QuizScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun SmartQuizApp(navController: NavHostController = rememberNavController()) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = QuizScreen.valueOf(
        backStackEntry?.destination?.route ?: QuizScreen.Login.name
    )

    Scaffold(
        topBar = {
            SmartQuizAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = QuizScreen.Login.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = QuizScreen.Login.name) {
                // TODO: Add LoginScreen
            }
            composable(route = QuizScreen.Home.name) {
                // TODO: Add HomeScreen
            }
            composable(route = QuizScreen.Quiz.name) {
                // TODO: Add QuizScreen
            }
            composable(route = QuizScreen.Profile.name) {
                // TODO: Add ProfileScreen
            }
            composable(route = QuizScreen.History.name) {
                // TODO: Add HistoryScreen
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SmartQuizAppBarPreview() {
    SmartQuizTheme {
        SmartQuizAppBar(
            currentScreen = QuizScreen.Home,
            canNavigateBack = false,
            navigateUp = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SmartQuizAppBarBackPreview() {
    SmartQuizTheme {
        SmartQuizAppBar(
            currentScreen = QuizScreen.Quiz,
            canNavigateBack = true,
            navigateUp = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SmartQuizAppPreview() {
    SmartQuizTheme {
        SmartQuizApp()
    }
}
