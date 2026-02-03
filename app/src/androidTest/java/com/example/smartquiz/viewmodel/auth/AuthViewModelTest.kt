package com.example.smartquiz.viewmodel.auth

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.smartquiz.MainDispatcherRule
import com.example.smartquiz.data.repository.auth.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AuthViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val authRepository: AuthRepository = mockk(relaxed = true)
    private val firebaseAuth: FirebaseAuth = mockk(relaxed = true)

    private lateinit var viewModel: AuthViewModel

    @Before
    fun setup() {
        viewModel = AuthViewModel(authRepository, firebaseAuth)
    }

    @Test
    fun loginFails_whenFieldsAreEmpty() {
        viewModel.loginWithEmailPassword("", "")

        assertEquals("Fields cannot be empty", viewModel.errorMessage)
        assertFalse(viewModel.isLoading)
    }

    @Test
    fun loginFails_whenEmailIsInvalid() {
        viewModel.loginWithEmailPassword("abc", "123456")

        assertEquals("Invalid email format", viewModel.errorMessage)
    }

    @Test
    fun loginFails_whenPasswordIsShort() {
        viewModel.loginWithEmailPassword("test@mail.com", "123")

        assertEquals("Password must be at least 6 characters", viewModel.errorMessage)
    }

    @Test
    fun passwordResetFails_whenEmailIsBlank() {
        viewModel.sendPasswordResetEmail("")

        assertEquals("Please enter your email", viewModel.errorMessage)
    }

    @Test
    fun firebaseLoginSuccess_updatesState() = runTest {
        val firebaseUser: FirebaseUser = mockk()

        viewModel.onFirebaseLoginSuccess(firebaseUser)
        advanceUntilIdle()

        coVerify { authRepository.handleLogin(firebaseUser) }

        assertTrue(viewModel.loginSuccess)
        assertFalse(viewModel.isLoading)
    }

    @Test
    fun consumeLoginSuccess_resetsFlag() {
        viewModel.consumeLoginSuccess()

        assertFalse(viewModel.loginSuccess)
    }

    @Test
    fun logout_callsRepository() {
        viewModel.logout()

        verify { authRepository.logout() }
    }
}
