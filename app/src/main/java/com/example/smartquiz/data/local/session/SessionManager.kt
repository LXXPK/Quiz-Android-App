package com.example.smartquiz.data.local.session

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    @ApplicationContext context: Context
) {

    private val prefs =
        context.getSharedPreferences("smart_quiz_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_UID = "user_uid"
    }

    fun saveUid(uid: String) {
        prefs.edit().putString(KEY_UID, uid).apply()
    }

    fun getUid(): String? = prefs.getString(KEY_UID, null)

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}
