package com.example.smartquiz.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.example.smartquiz.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmartQuizTopBar(
    currentRoute: String?,
    navController: NavHostController,
    onLogoutClick: () -> Unit
) {

    var showMenu by remember { mutableStateOf(false) }

    val showProfileIcon = when {
        currentRoute == Routes.PROFILE -> false
        currentRoute?.startsWith(Routes.QUIZ_PLAY) == true -> false
        else -> true
    }

    val showOverflowMenu = currentRoute == Routes.PROFILE

    TopAppBar(
        title = {
            Text(
                text = when {
                    currentRoute?.startsWith(Routes.QUIZ_PLAY) == true -> "Quiz"
                    currentRoute == Routes.HISTORY -> "History"
                    currentRoute == Routes.PROFILE -> "Profile"
                    else -> "SmartQuiz"
                },
                style = MaterialTheme.typography.titleLarge
            )
        },
        actions = {

            /* ---------- PROFILE ICON ---------- */
            if (showProfileIcon) {
                IconButton(
                    onClick = { navController.navigate(Routes.PROFILE) }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.AccountCircle,
                        contentDescription = "Profile"
                    )
                }
            }

            /* ---------- OVERFLOW MENU ---------- */
            if (showOverflowMenu) {
                IconButton(onClick = { showMenu = true }) {
                    Icon(
                        imageVector = Icons.Outlined.MoreVert,
                        contentDescription = "More"
                    )
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Logout") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Logout,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error
                            )
                        },
                        onClick = {
                            showMenu = false
                            onLogoutClick()
                        }
                    )
                }
            }
        }
    )
}
