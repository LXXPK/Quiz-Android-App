package com.example.smartquiz.ui.auth
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartquiz.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var loginSuccess by mutableStateOf(false)
        private set   // 👈 THIS IS KEY

    private val auth = FirebaseAuth.getInstance()

    fun loginWithEmailPassword(email: String, password: String) {
        isLoading = true
        errorMessage = null

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                it.user?.let(::onFirebaseLoginSuccess)
            }
            .addOnFailureListener {
                errorMessage = it.message
                isLoading = false
            }
    }

    fun registerWithEmailPassword(email: String, password: String) {
        isLoading = true
        errorMessage = null

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                it.user?.let(::onFirebaseLoginSuccess)
            }
            .addOnFailureListener {
                errorMessage = it.message
                isLoading = false
            }
    }

    fun onFirebaseLoginSuccess(user: FirebaseUser) {
        viewModelScope.launch {
            try {
                authRepository.handleLogin(user)
                loginSuccess = true   // ✅ SIGNAL UI
            } catch (e: Exception) {
                errorMessage = e.message
            } finally {
                isLoading = false
            }
        }
    }

    fun sendPasswordResetEmail(email: String) {
        if (email.isBlank()) {
            errorMessage = "Please enter your email"
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorMessage = "Invalid email format"
            return
        }

        isLoading = true
        errorMessage = null

        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                errorMessage = "Password reset email sent"
                isLoading = false
            }
            .addOnFailureListener {
                errorMessage = it.message ?: "Failed to send reset email"
                isLoading = false
            }
    }


    fun consumeLoginSuccess() {
        loginSuccess = false
    }

    fun getUid(): String? {
        return authRepository.getLoggedInUid()
    }


    fun logout() {
        authRepository.logout()
    }
}
