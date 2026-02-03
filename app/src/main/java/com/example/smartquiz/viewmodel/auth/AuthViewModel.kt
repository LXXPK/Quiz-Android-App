package com.example.smartquiz.viewmodel.auth

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartquiz.data.repository.auth.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var loginSuccess by mutableStateOf(false)
        private set

    fun loginWithEmailPassword(email: String, password: String) {
        if (!validateLogin(email, password)) return
        isLoading = true
        errorMessage = null

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                it.user?.let(::onFirebaseLoginSuccess)
            }
            .addOnFailureListener {
                errorMessage = it.message
                isLoading = false
            }
    }

    fun registerWithEmailPassword(email: String, password: String) {
        if (!validateRegister(email, password)) return
        isLoading = true
        errorMessage = null

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                it.user?.let(::onFirebaseLoginSuccess)
            }
            .addOnFailureListener {
                errorMessage = it.message
                isLoading = false
            }
    }

    fun sendPasswordResetEmail(email: String) {
        if (email.isBlank()) {
            errorMessage = "Please enter your email"
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorMessage = "Invalid email format"
            return
        }

        isLoading = true
        errorMessage = null

        firebaseAuth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                errorMessage = "Password reset email sent"
                isLoading = false
            }
            .addOnFailureListener {
                errorMessage = it.message ?: "Failed to send reset email"
                isLoading = false
            }
    }

    private fun validateLogin(email: String, password: String): Boolean {
        errorMessage = null

        return when {
            email.isBlank() || password.isBlank() -> {
                errorMessage = "Email and password are required"
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                errorMessage = "Invalid email format"
                false
            }
            else -> true
        }
    }

    private fun validateRegister(email: String, password: String): Boolean {
        errorMessage = null

        return when {
            email.isBlank() || password.isBlank() -> {
                errorMessage = "All fields are required"
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                errorMessage = "Invalid email format"
                false
            }
            password.length < 6 -> {
                errorMessage = "Password must be at least 6 characters"
                false
            }
            else -> true
        }
    }


    fun onFirebaseLoginSuccess(user: FirebaseUser) {
        viewModelScope.launch {
            authRepository.handleLogin(user)
            loginSuccess = true
            isLoading = false
        }
    }

    fun clearError() {
        errorMessage = null
    }

    fun consumeLoginSuccess() {
        loginSuccess = false
    }

    fun getUid(): String? = authRepository.getLoggedInUid()

    fun logout() {
        authRepository.logout()
    }
}
