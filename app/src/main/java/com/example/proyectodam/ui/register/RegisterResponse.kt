package com.example.proyectodam.ui.register

data class RegisterResponse(
    val success: Boolean,
    val message: String,
    val data: User? = null
)
