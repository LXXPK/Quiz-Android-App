package com.example.smartquiz.ui.home


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smartquiz.viewmodel.home.HomeViewModel

@Composable
fun HomeScreen(
    onProfile: () -> Unit,
    onLogout: () -> Unit,
    onCategoryClick: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val categories by viewModel.categories.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadCategories()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "🎉 Login Successful",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (categories.isEmpty()) {
            Text("No quiz categories available")
        } else {
            categories.forEach { category ->
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onCategoryClick(category) }
                ) {
                    Text("$category Quiz")
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onProfile) {
            Text("Profile")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = onLogout) {
            Text("Logout")
        }
    }
}
