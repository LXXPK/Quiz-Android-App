package com.example.smartquiz.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartquiz.data.local.database.AppDatabase
import com.example.smartquiz.data.repository.ProfileRepository
import com.example.smartquiz.viewmodel.profile.ProfileViewModel
import com.example.smartquiz.viewmodel.profile.ProfileViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onBack: () -> Unit = {},
    onHistory: () -> Unit = {},
    onLogout: () -> Unit = {}
) {

    /* ---------- VIEWMODEL SETUP ---------- */
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val repository = ProfileRepository(database.userDao())

    val profileViewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(repository)
    )

    val savedInterests by profileViewModel.interests.collectAsState()

    /* ---------- USER INFO ---------- */
    val userName = "Omkar Patil"
    val userEmail = "omkar@gmail.com"

    /* ---------- INTEREST DATA ---------- */
    val interests = listOf(
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

    /* ---------- LOAD + RESTORE ---------- */
    LaunchedEffect(Unit) {
        profileViewModel.loadUserInterests()
    }

    LaunchedEffect(savedInterests) {
        selected.clear()
        selected.addAll(savedInterests.map { it.interest })

        isEditMode = savedInterests.isEmpty()
        expandInterests = savedInterests.isEmpty()
    }

    Column(modifier = Modifier.fillMaxSize()) {

        /* ---------- TOP APP BAR ---------- */
        TopAppBar(
            title = { Text("Profile") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = { showMenu = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("History") },
                        onClick = {
                            showMenu = false
                            onHistory()
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(
                                "Logout",
                                color = MaterialTheme.colorScheme.error
                            )
                        },
                        onClick = {
                            showMenu = false
                            onLogout()
                        }
                    )
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            /* ---------- PROFILE HEADER ---------- */
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
                Text(userName, style = MaterialTheme.typography.titleMedium)
                Text(
                    userEmail,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            /* ---------- INTEREST SECTION ---------- */
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
                            if (isEditMode) "Select your interest" else "Your interests"
                        )

                        if (isEditMode) {
                            Row(verticalAlignment = Alignment.CenterVertically) {

                                if (selected.isNotEmpty()) {
                                    TextButton(
                                        onClick = { selected.clear() },
                                        contentPadding = PaddingValues(horizontal = 4.dp)
                                    ) {
                                        Text(
                                            "Clear all",
                                            color = MaterialTheme.colorScheme.error
                                        )
                                    }
                                }

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
                    }

                    /* ---------- VIEW MODE ---------- */
                    if (!isEditMode) {
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            selected.forEach { interest ->
                                AssistChip(
                                    onClick = {},
                                    label = {
                                        Text(
                                            interest,
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    },
                                    modifier = Modifier.height(48.dp)
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

                    /* ---------- EDIT MODE ---------- */
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
                                interests.forEach { interest ->
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
                                        label = {
                                            Text(
                                                interest,
                                                style = MaterialTheme.typography.bodyLarge
                                            )
                                        },
                                        enabled = !disabled,
                                        modifier = Modifier
                                            .height(48.dp)
                                            .alpha(if (disabled) 0.4f else 1f),
                                        colors = AssistChipDefaults.assistChipColors(
                                            containerColor = if (isSelected)
                                                MaterialTheme.colorScheme.primary
                                            else
                                                MaterialTheme.colorScheme.surface,
                                            labelColor = if (isSelected)
                                                MaterialTheme.colorScheme.onPrimary
                                            else
                                                MaterialTheme.colorScheme.onSurface
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            /* ---------- SAVE PROFILE ---------- */
            if (isEditMode) {
                Button(
                    onClick = {
                        profileViewModel.saveInterests(selected)
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
