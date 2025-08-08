package com.example.proyectodam.ui.history

import com.example.proyectodam.ui.shoppingcart.ItemCarrito

data class ApiResponseHistory(
    val success: Boolean,
    val data: List<Pedido>,
    val message: String?
)

data class Pedido(
    val _id: String,
    val usuarioId: Usuario,
    val productos: List<ProductoPedido>,
    val total: Double,
    val estado: String,
    val fecha: String
)

data class Usuario(
    val _id: String,
    val nombre: String,
    val correo: String
)

data class ProductoPedido(
    val _id: String,
    val productoId: String,
    val nombre: String,
    val precio: Double,
    val cantidad: Int,
    val talla: String
)