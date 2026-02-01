package com.example.smartquiz.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smartquiz.R
import com.example.smartquiz.viewmodel.profile.ProfileViewModel

@Composable
fun ProfileScreen(
    onLogout: () -> Unit = {}
) {
    val viewModel: ProfileViewModel = hiltViewModel()

    val user by viewModel.user.collectAsState()
    val savedInterests by viewModel.interests.collectAsState()
    val allCategories by viewModel.availableCategories.collectAsState()

    val selected = remember { mutableStateListOf<String>() }
    var isEditMode by remember { mutableStateOf(true) }
    var expand by remember { mutableStateOf(false) }
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.errorMessage.collectAsState()



    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    LaunchedEffect(savedInterests) {
        selected.clear()
        selected.addAll(savedInterests.map { it.interest })
        isEditMode = savedInterests.isEmpty()
        expand = savedInterests.isEmpty()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(dimensionResource(id = R.dimen.medium_padding))
            .verticalScroll(rememberScrollState())
    ) {

        ProfileHeader(
            name = user?.name.orEmpty(),
            email = user?.email.orEmpty()
        )

        Spacer(Modifier.height(dimensionResource(id = R.dimen.spacing_xlarge)))

        InterestsSection(
            allInterests = allCategories,
            selected = selected,
            isEditMode = isEditMode,
            expand = expand,
            maxSelection = 4,
            onEdit = {
                isEditMode = true
                expand = true
            },
            onExpandToggle = { expand = !expand },
            onClearAll = { selected.clear() },
            onSave = {
                viewModel.saveInterests(selected)
                isEditMode = false
                expand = false
            },
            onToggleInterest = { interest ->
                if (interest in selected) {
                    selected.remove(interest)
                } else if (selected.size < 4) {
                    selected.add(interest)
                }
            }
        )

    }
}
