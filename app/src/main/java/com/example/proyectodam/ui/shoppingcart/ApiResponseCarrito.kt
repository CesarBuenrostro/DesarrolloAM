package com.example.proyectodam.ui.shoppingcart

import com.example.proyectodam.ui.shop.Producto

data class ApiResponseCarrito(
    val success: Boolean,
    val data: CarritoData,
    val message: String
)
data class CarritoData(
    val _id: String,
    val usuarioId: String,
    val items: List<ItemCarrito>,
    val total: Double,
    val creadoEn: String,
    val actualizadoEn: String
)

data class ItemCarrito(
    val _id: String,
    val productoId: String,
    val nombre: String,
    val precio: Double,
    var cantidad: Int,
    val talla: String,
    var subtotal: Double
)