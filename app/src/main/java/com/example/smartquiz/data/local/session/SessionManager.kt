package com.example.smartquiz.data.local.session

import android.content.Context
import com.example.smartquiz.utils.SessionConstants
import com.example.smartquiz.utils.SessionConstants.KEY_UID
import com.example.smartquiz.utils.SessionConstants.PREFS_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    @ApplicationContext context: Context
) {

    private val prefs =
        context.getSharedPreferences(PREFS_NAME , Context.MODE_PRIVATE)



    fun saveUid(uid: String) {
        prefs.edit().putString(KEY_UID, uid).apply()
    }

    fun getUid(): String? = prefs.getString(KEY_UID, null)

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}
