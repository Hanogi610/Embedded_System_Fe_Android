package com.example.embeddedsystem.service

import com.example.embeddedsystem.model.House
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface HouseService {
    @POST("login")
    suspend fun login(@Body house: House): Response<House>
}
object HouseApi {
    val retrofitService: HouseService by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://192.168.92.145:8080/api/house/")
            .build().create(HouseService::class.java)
    }
}