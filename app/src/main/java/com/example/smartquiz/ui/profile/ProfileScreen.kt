package com.example.smartquiz.ui.profile



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smartquiz.viewmodel.profile.ProfileViewModel


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ProfileScreen(
    onBack: () -> Unit = {},
    onHistory: () -> Unit = {},
    onLogout: () -> Unit = {}
) {

    val viewModel: ProfileViewModel = hiltViewModel()

    val user by viewModel.user.collectAsState()
    val savedInterests by viewModel.interests.collectAsState()

    val allInterests = listOf(
        "DSA", "Android", "Backend",
        "Java", "Kotlin", "SQL",
        "Python", "C++"
    )

    val selected = remember { mutableStateListOf<String>() }

    var expandInterests by remember { mutableStateOf(false) }
    var showMenu by remember { mutableStateOf(false) }
    var isEditMode by remember { mutableStateOf(true) }

    val maxSelection = 4
    val canSave = selected.isNotEmpty()

    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    LaunchedEffect(savedInterests) {
        selected.clear()
        selected.addAll(savedInterests.map { it.interest })

        isEditMode = savedInterests.isEmpty()
        expandInterests = savedInterests.isEmpty()
    }

    Column(modifier = Modifier.fillMaxSize().systemBarsPadding()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant,
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text("👤")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    user?.name ?: "",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    user?.email ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text("Your Interest", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(12.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Column(modifier = Modifier.padding(12.dp)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            if (isEditMode) "Select your interest"
                            else "Your interests"
                        )

                        if (isEditMode) {
                            IconButton(onClick = {
                                expandInterests = !expandInterests
                            }) {
                                Icon(
                                    Icons.Default.KeyboardArrowDown,
                                    contentDescription = "Expand"
                                )
                            }
                        }
                    }

                    if (!isEditMode) {
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            selected.forEach { interest ->
                                AssistChip(
                                    onClick = {},
                                    label = { Text(interest) }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        TextButton(
                            onClick = {
                                isEditMode = true
                                expandInterests = true
                            }
                        ) {
                            Text("Edit your interest")
                        }
                    }

                    if (isEditMode && expandInterests) {

                        Spacer(modifier = Modifier.height(12.dp))

                        Column(
                            modifier = Modifier
                                .height(200.dp)
                                .verticalScroll(rememberScrollState())
                        ) {

                            FlowRow(
                                maxItemsInEachRow = 3,
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                allInterests.forEach { interest ->
                                    val isSelected = interest in selected
                                    val disabled =
                                        !isSelected && selected.size >= maxSelection

                                    AssistChip(
                                        onClick = {
                                            if (isSelected) {
                                                selected.remove(interest)
                                            } else if (!disabled) {
                                                selected.add(interest)
                                            }
                                        },
                                        label = { Text(interest) },
                                        enabled = !disabled,
                                        modifier = Modifier
                                            .height(48.dp)
                                            .alpha(if (disabled) 0.4f else 1f)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (isEditMode) {
                Button(
                    onClick = {
                        viewModel.saveInterests(selected)
                        isEditMode = false
                        expandInterests = false
                    },
                    enabled = canSave,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Profile")
                }
            }
        }
    }
}
