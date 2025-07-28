package com.example.proyectodam.ui.login

import com.example.proyectodam.ui.register.User

data class LoginResponse(
    val success: Boolean,
    val message : String,
    val token: String,
    val user: User?
)
