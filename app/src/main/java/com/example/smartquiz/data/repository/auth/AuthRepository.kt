package com.example.smartquiz.data.repository.auth

import com.example.smartquiz.data.local.dao.user.UserDao
import com.example.smartquiz.data.local.entity.user.UserEntity
import com.example.smartquiz.data.local.session.SessionManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val userDao: UserDao,
    private val sessionManager: SessionManager
) {

    suspend fun handleLogin(user: FirebaseUser) {
        val entity = UserEntity(
            userId = user.uid,
            name = user.displayName ?: "",
            email = user.email ?: "",
            photoUrl = user.photoUrl?.toString()
        )

        userDao.insertUser(entity)
        sessionManager.saveUid(user.uid)
    }

    fun getLoggedInUid(): String? = sessionManager.getUid()

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        sessionManager.clearSession()
    }
}