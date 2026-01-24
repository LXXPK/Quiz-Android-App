package com.example.smartquiz.ui.auth

import android.content.Context
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.example.smartquiz.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class GoogleAuthHandler(
    private val context: Context,
    private val onFirebaseUser: (FirebaseUser) -> Unit,
    private val onError: (String) -> Unit
) {

    private val auth = FirebaseAuth.getInstance()
    private val credentialManager = CredentialManager.create(context)

    suspend fun signIn() {
        try {
            val googleIdOption = GetGoogleIdOption.Builder()
                .setServerClientId(
                    context.getString(R.string.default_web_client_id)
                )
                .setFilterByAuthorizedAccounts(false)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val result = credentialManager.getCredential(
                context = context,
                request = request
            )

            handleCredential(result.credential)

        } catch (e: Exception) {
            onError(e.localizedMessage ?: "Login failed")
        }
    }

    private fun handleCredential(credential: Credential) {
        if (
            credential is CustomCredential &&
            credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ) {
            val googleIdTokenCredential =
                GoogleIdTokenCredential.createFrom(credential.data)

            firebaseAuthWithGoogle(googleIdTokenCredential.idToken)
        } else {
            onError("Invalid credential type")
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val firebaseCredential =
            GoogleAuthProvider.getCredential(idToken, null)

        auth.signInWithCredential(firebaseCredential)
            .addOnSuccessListener {
                it.user?.let(onFirebaseUser)
            }
            .addOnFailureListener {
                onError(it.localizedMessage ?: "Firebase login failed")
            }
    }
}
