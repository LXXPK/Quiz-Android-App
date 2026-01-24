package com.example.smartquiz.ui.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartquiz.data.local.database.AppDatabase
import com.example.smartquiz.data.local.session.SessionManager
import com.example.smartquiz.data.repository.AuthRepository

class AuthViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {

            val db = AppDatabase.getDatabase(context)
            val userDao = db.userDao()
            val sessionManager = SessionManager(context)

            val repository = AuthRepository(
                userDao = userDao,
                sessionManager = sessionManager
            )

            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
