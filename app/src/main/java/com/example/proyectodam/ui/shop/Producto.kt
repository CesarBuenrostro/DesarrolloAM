package com.example.proyectodam.ui.shop

import java.util.Date

data class Producto(
    val _id : String,
    val nombre: String,
    val precio: String,
    val talla: String,
    val imagen: String,
    val genero: String,
    val temporada: String,
    val fecha: Date
)