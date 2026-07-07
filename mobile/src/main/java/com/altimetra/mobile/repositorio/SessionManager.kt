package com.altimetra.mobile.repositorio

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("AltimetraPrefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_USER = "usuario_local"
        private const val KEY_PASS = "password_local"
        private const val KEY_IS_LOGGED_IN = "esta_logueado"
    }

    fun registrarUsuario(usuario: String, contrasena: String) {
        prefs.edit().apply {
            putString(KEY_USER, usuario)
            putString(KEY_PASS, contrasena)
            apply()
        }
    }

    fun existeUsuario(): Boolean {
        return prefs.getString(KEY_USER, null) != null
    }

    fun validarCredenciales(usuario: String, contrasena: String): Boolean {
        val userGuardado = prefs.getString(KEY_USER, "")
        val passGuardado = prefs.getString(KEY_PASS, "")
        return usuario == userGuardado && contrasena == passGuardado
    }

    fun setLoginState(isLoggedIn: Boolean) {
        prefs.edit().putBoolean(KEY_IS_LOGGED_IN, isLoggedIn).apply()
    }

    fun isUserLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }
}