package com.example.proyectodam.data.api

import com.example.proyectodam.ui.login.LoginRequest
import com.example.proyectodam.ui.login.LoginResponse
import com.example.proyectodam.ui.register.RegisterResponse
import com.example.proyectodam.ui.register.User
import com.example.proyectodam.ui.shop.ApiResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    // Productos
    @GET("valeua/ropa")
    fun getProductos(): Call<ApiResponse>

    // Registro
    @POST ("valeua/user")
    fun registerUser(@Body user: User): Call<RegisterResponse>

    // Login
    @POST("valeua/login")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

}