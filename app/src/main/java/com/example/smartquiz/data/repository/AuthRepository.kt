package com.example.smartquiz.data.repository

import com.example.smartquiz.data.local.dao.UserDao
import com.example.smartquiz.data.local.entity.user.UserEntity
import com.example.smartquiz.data.local.session.SessionManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthRepository(
    private val userDao: UserDao,
    private val sessionManager: SessionManager
) {

    suspend fun handleLogin(user: FirebaseUser) {

        val userEntity = UserEntity(
            userId = user.uid,
            name = user.displayName ?: "",
            email = user.email ?: "",
            photoUrl = user.photoUrl?.toString()
        )


        userDao.insertUser(userEntity)


        sessionManager.saveUid(user.uid)
    }

    fun getLoggedInUid(): String? {
        return sessionManager.getUid()
    }



    fun logout() {

        FirebaseAuth.getInstance().signOut()


        sessionManager.clearSession()
    }
}
