package com.example.embeddedsystem.service

import com.example.embeddedsystem.model.Device
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query


interface DeviceService {
    @GET("get")
    suspend fun getDevicesByHouseId(@Query("houseId") houseId : Int): List<Device>

    @POST("update")
    suspend fun updateDevice(@Body device: Device): Response<Device>
}

object DeviceApiHelper {
    fun getDevices(houseId: Int) = flow {
        val devices = DeviceApi.retrofitService.getDevicesByHouseId(houseId)
        emit(devices)
    }
}

object DeviceApi {
    val retrofitService : DeviceService by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://192.168.92.145:8080/api/device/")
            .build().create(DeviceService::class.java)
    }
}