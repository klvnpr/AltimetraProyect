package com.altimetra.wear.repositorio

import android.content.Context
import android.content.SharedPreferences

class SessionManagerWear(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("AltimetraWearPrefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_IS_LOGGED_IN = "esta_logueado"
    }

    fun setLoginState(isLoggedIn: Boolean) {
        prefs.edit().putBoolean(KEY_IS_LOGGED_IN, isLoggedIn).apply()
    }

    fun isUserLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }
}
