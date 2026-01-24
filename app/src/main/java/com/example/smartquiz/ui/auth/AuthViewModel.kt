package com.example.smartquiz.ui.auth
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartquiz.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun onFirebaseLoginSuccess(user: FirebaseUser) {
        viewModelScope.launch {
            try {
                isLoading = true
                authRepository.handleLogin(user)   // Room + Session
            } catch (e: Exception) {
                errorMessage = e.message
            } finally {
                isLoading = false
            }
        }
    }
    fun getUid(): String? {
        return authRepository.getLoggedInUid()
    }

    fun logout() {
        authRepository.logout()
    }

}
