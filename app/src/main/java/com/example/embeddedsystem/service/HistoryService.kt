package com.example.embeddedsystem.service

import com.example.embeddedsystem.model.History
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface HistoryService {
    @GET("get")
    suspend fun getHistories(@Query("houseId") houseId : Int) : List<History>

    @POST("add")
    suspend fun addHistory(@Body history: History) : Response<History>
}
object HistoryApiHelper{
    fun getHistories(houseId : Int) : Flow<List<History>> = flow {
        emit(HistoryApi.retrofitService.getHistories(houseId))
    }
}
object HistoryApi{
    val retrofitService : HistoryService by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://192.168.92.145:8080/api/history/")
            .build().create(HistoryService::class.java)
    }
}