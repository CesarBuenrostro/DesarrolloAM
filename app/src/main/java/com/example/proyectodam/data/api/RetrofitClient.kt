package com.example.proyectodam.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL ="http://10.10.33.216:3000/"

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}