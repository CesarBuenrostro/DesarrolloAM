package com.example.proyectodam.data.api

data class ApiResponseGeneric(
    val success: Boolean,
    val message: String,
    val data: Any? = null
)
