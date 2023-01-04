package id.web.safira.protectcare30

import android.app.Activity
import android.content.Context

object Global {
    const val SHARED_PREFERENCES = "PROTECTCARE"
    const val SHARED_PREF_KEY_USERNAME = "USERNAME"
    fun getUsername(context: Context): String? {
        return context.getSharedPreferences(SHARED_PREFERENCES, Activity.MODE_PRIVATE)
            .getString(SHARED_PREF_KEY_USERNAME, null)
    }
}