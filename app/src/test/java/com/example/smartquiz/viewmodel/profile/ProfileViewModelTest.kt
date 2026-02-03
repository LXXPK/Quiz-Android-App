package com.example.smartquiz.viewmodel.profile

import com.example.smartquiz.MainDispatcherRule
import com.example.smartquiz.data.local.entity.user.InterestEntity
import com.example.smartquiz.data.local.entity.user.UserEntity
import com.example.smartquiz.data.local.session.SessionManager
import com.example.smartquiz.data.repository.profile.ProfileRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: ProfileRepository
    private lateinit var sessionManager: SessionManager
    private lateinit var viewModel: ProfileViewModel

    @Before
    fun setup() {
        repository = mock()
        sessionManager = mock()
        viewModel = ProfileViewModel(repository, sessionManager)
    }

    @Test
    fun loadProfile_success_updatesAllStates() = runTest {
        val userId = "uid123"

        val user = UserEntity(
            userId = userId,
            name = "Omkar",
            email = "omkar@gmail.com",
            photoUrl = null,
            currentStreak = 5,
            lastActivityDate = 123456789L
        )

        val interests = listOf(
            InterestEntity(1, userId, "Kotlin"),
            InterestEntity(2, userId, "Android")
        )

        val categories = listOf("Kotlin", "Android", "DSA")

        whenever(sessionManager.getUid()).thenReturn(userId)
        whenever(repository.getUserProfile(userId)).thenReturn(user)
        whenever(repository.getUserInterests(userId)).thenReturn(interests)
        whenever(repository.getAvailableCategories()).thenReturn(categories)

        viewModel.loadProfile()
        advanceUntilIdle()

        assertEquals(user, viewModel.user.value)
        assertEquals(interests, viewModel.interests.value)
        assertEquals(categories, viewModel.availableCategories.value)
        assertFalse(viewModel.isLoading.value)
        assertNull(viewModel.errorMessage.value)
    }

    @Test
    fun loadProfile_withoutSession_setsErrorMessage() = runTest {
        whenever(sessionManager.getUid()).thenReturn(null)

        viewModel.loadProfile()
        advanceUntilIdle()

        assertEquals(
            "Session expired. Please login again.",
            viewModel.errorMessage.value
        )
        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun saveInterests_success_updatesInterests() = runTest {
        val userId = "uid123"

        val updatedInterests = listOf(
            InterestEntity(1, userId, "Kotlin"),
            InterestEntity(2, userId, "Android")
        )

        whenever(sessionManager.getUid()).thenReturn(userId)
        whenever(repository.saveUserInterests(userId, listOf("Kotlin", "Android")))
            .thenReturn(Unit)
        whenever(repository.getUserInterests(userId)).thenReturn(updatedInterests)

        viewModel.saveInterests(listOf("Kotlin", "Android"))
        advanceUntilIdle()

        assertEquals(updatedInterests, viewModel.interests.value)
        assertFalse(viewModel.isLoading.value)
        assertNull(viewModel.errorMessage.value)
    }

    @Test
    fun saveInterests_failure_setsErrorMessage() = runTest {
        val userId = "uid123"

        whenever(sessionManager.getUid()).thenReturn(userId)
        whenever(repository.saveUserInterests(userId, emptyList()))
            .thenThrow(RuntimeException("DB error"))

        viewModel.saveInterests(emptyList())
        advanceUntilIdle()

        assertEquals("DB error", viewModel.errorMessage.value)
        assertFalse(viewModel.isLoading.value)
    }
}
