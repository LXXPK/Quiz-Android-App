package com.example.smartquiz.data.remote.auth

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FirebaseAuthSource {

    private val auth = FirebaseAuth.getInstance()

    fun signInWithGoogle(
        credential: AuthCredential,
        onSuccess: (FirebaseUser) -> Unit,
        onFailure: (String) -> Unit
    ) {
        auth.signInWithCredential(credential)
            .addOnSuccessListener {
                it.user?.let(onSuccess)
            }
            .addOnFailureListener {
                onFailure(it.message ?: "Authentication failed")
            }
    }
}
