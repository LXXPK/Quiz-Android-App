package com.example.smartquiz.viewmodel.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.smartquiz.data.local.entity.user.InterestEntity
import com.example.smartquiz.data.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: ProfileRepository
) : ViewModel() {

    private val _interests = MutableStateFlow<List<InterestEntity>>(emptyList())
    val interests: StateFlow<List<InterestEntity>> = _interests

    private val userId = "test_1" // temporary, same as MainActivity

    fun loadUserInterests() {
        viewModelScope.launch {
            _interests.value = repository.getUserInterests(userId)
        }
    }

    fun saveInterests(selected: List<String>) {
        viewModelScope.launch {
            repository.saveUserInterests(userId, selected)
            loadUserInterests() // refresh after save
        }
    }
}

/* ---------- FACTORY ---------- */
class ProfileViewModelFactory(
    private val repository: ProfileRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
