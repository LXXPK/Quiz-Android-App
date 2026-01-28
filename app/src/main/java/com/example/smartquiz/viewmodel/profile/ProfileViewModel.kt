package com.example.smartquiz.viewmodel.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartquiz.data.local.entity.user.InterestEntity
import com.example.smartquiz.data.local.entity.user.UserEntity
import com.example.smartquiz.data.local.session.SessionManager
import com.example.smartquiz.data.repository.profile.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _interests =
        MutableStateFlow<List<InterestEntity>>(emptyList())
    val interests: StateFlow<List<InterestEntity>> = _interests

    private val _user =
        MutableStateFlow<UserEntity?>(null)
    val user: StateFlow<UserEntity?> = _user

    // ✅ NEW
    private val _availableCategories =
        MutableStateFlow<List<String>>(emptyList())
    val availableCategories: StateFlow<List<String>> = _availableCategories

    private fun uid(): String =
        sessionManager.getUid()
            ?: throw IllegalStateException("User not logged in")

    fun loadProfile() {
        viewModelScope.launch {
            val userId = uid()

            _user.value =
                repository.getUserProfile(userId)

            _interests.value =
                repository.getUserInterests(userId)

            // ✅ LOAD CATEGORIES FROM QUIZZES
            _availableCategories.value =
                repository.getAvailableCategories()
        }
    }

    fun saveInterests(selected: List<String>) {
        viewModelScope.launch {
            repository.saveUserInterests(uid(), selected)
            _interests.value =
                repository.getUserInterests(uid())
        }
    }
}
