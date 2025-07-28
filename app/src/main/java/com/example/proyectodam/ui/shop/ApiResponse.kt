package com.example.proyectodam.ui.shop

data class ApiResponse(
    val success: Boolean,
    val data: List<Producto>,
    val message: String
)
