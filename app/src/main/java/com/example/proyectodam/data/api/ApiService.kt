package com.example.proyectodam.data.api

import com.example.proyectodam.ui.history.ApiResponseHistory
import com.example.proyectodam.ui.login.LoginRequest
import com.example.proyectodam.ui.login.LoginResponse
import com.example.proyectodam.ui.register.RegisterResponse
import com.example.proyectodam.ui.register.User
import com.example.proyectodam.ui.shop.ApiResponse
import com.example.proyectodam.ui.shoppingcart.ApiResponseCarrito
import com.example.proyectodam.ui.shoppingcart.addCartRequest
import okhttp3.Request
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    // Productos
    @GET("valeua/ropa")
    fun getProductos(): Call<ApiResponse>

    @GET("valeua/ropa/{key}/{value}")
    fun getProductosFiltrados(
        @Path("key") key: String,
        @Path("value") value: String
    ): Call<ApiResponse>

    @POST("valeua/history")
    fun crearPedido(
        @Header("Authorization") token: String): Call<ApiResponseGeneric>



    // Registro
    @POST ("valeua/user")
    fun registerUser(@Body user: User): Call<RegisterResponse>

    // Login
    @POST("valeua/login")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

    @GET("valeua/carrito")
    fun obtenerCarrito(
        @Header("Authorization") token: String): Call<ApiResponseCarrito>

    @POST("valeua/carrito/product")
    fun addProductCarrito(
        @Header("Authorization") token: String,
        @Body addCartRequest: addCartRequest
    ) : Call<ApiResponseCarrito>

    @DELETE("valeua/carrito")
    fun clearCarrito(
        @Header("Authorization") token: String
    ): Call<ApiResponseCarrito>

    @DELETE("valeua/carrito/product")
    fun deleteProductCarrito(
        @Header("Authorization") token: String,
        @Query ("productoId") itemId: String
    ): Call<ApiResponseCarrito>

    @GET("/valeua/history")
    fun obtenerHistorial(
        @Header("Authorization") token: String): Call<ApiResponseHistory>
}