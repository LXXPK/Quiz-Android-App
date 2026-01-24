package com.example.smartquiz.data.local.session

import android.content.Context

class SessionManager(context: Context) {

    private val prefs =
        context.getSharedPreferences("smart_quiz_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_UID = "user_uid"
    }

    fun saveUid(uid: String) {
        prefs.edit().putString(KEY_UID, uid).apply()
    }

    fun getUid(): String? {
        return prefs.getString(KEY_UID, null)
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}
