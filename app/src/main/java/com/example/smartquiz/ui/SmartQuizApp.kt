package com.example.smartquiz.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.smartquiz.R
import com.example.smartquiz.ui.theme.SmartQuizTheme

enum class QuizScreen(@StringRes val title: Int, val showOnBottomBar: Boolean = true, val icon: ImageVector? = null, val iconSelected: ImageVector? = null) {
    Login(title = R.string.login_title, showOnBottomBar = false),
    Home(title = R.string.home_title, icon = Icons.Outlined.Home, iconSelected = Icons.Filled.Home),
    Quiz(title = R.string.quiz_title, showOnBottomBar = false),
    Profile(title = R.string.profile_title, icon = Icons.Outlined.Person, iconSelected = Icons.Filled.Person),
    History(title = R.string.history_title, icon = Icons.Outlined.DateRange, iconSelected = Icons.Filled.DateRange)
}

@Composable
fun SmartQuizBottomBar(
    currentScreen: QuizScreen,
    onTabSelected: (QuizScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        QuizScreen.entries.filter { it.showOnBottomBar }.forEach { screen ->
            val label = stringResource(screen.title)
            NavigationBarItem(
                selected = currentScreen == screen,
                onClick = { onTabSelected(screen) },
                icon = {
                    val icon = if (currentScreen == screen) screen.iconSelected else screen.icon
                    if (icon != null) {
                        Icon(
                            imageVector = icon,
                            contentDescription = stringResource(R.string.nav_item_description, label)
                        )
                    }
                },
                label = { Text(label) }
            )
        }
    }
}

@Composable
fun SmartQuizApp(navController: NavHostController = rememberNavController()) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: QuizScreen.Login.name
    val currentScreen = try {
        QuizScreen.valueOf(currentRoute)
    } catch (e: Exception) {
        QuizScreen.Login
    }

    Scaffold(
        bottomBar = {
            // Hide bottom bar on Quiz and Login screens
            if (currentScreen != QuizScreen.Quiz && currentScreen != QuizScreen.Login) {
                SmartQuizBottomBar(
                    currentScreen = currentScreen,
                    onTabSelected = { screen ->
                        navController.navigate(screen.name) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
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
fun SmartQuizBottomBarPreview() {
    SmartQuizTheme {
        SmartQuizBottomBar(
            currentScreen = QuizScreen.Home,
            onTabSelected = {}
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
