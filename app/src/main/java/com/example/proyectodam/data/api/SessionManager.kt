package com.example.proyectodam.data.api

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)

    fun saveAuthToken(token: String) {
        prefs.edit().putString("AUTH_TOKEN", token).apply()
    }

    fun fetchAuthToken(): String? {
        return prefs.getString("AUTH_TOKEN", null)
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }

    fun saveUserInfo(name: String, email: String) {
        prefs.edit().apply {
            putString("USER_NAME", name)
            putString("USER_EMAIL", email)
            apply()
        }
    }

    fun fetchUserName(): String? {
        return prefs.getString("USER_NAME", null)
    }

    fun fetchUserEmail(): String? {
        return prefs.getString("USER_EMAIL", null)
    }

}
