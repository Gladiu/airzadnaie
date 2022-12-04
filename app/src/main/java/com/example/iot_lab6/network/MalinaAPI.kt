package com.example.iot_lab6.network

import com.example.iot_lab6.definitions.Mwykresy.WykresRow
import com.example.iot_lab6.definitions.ledPanel.SetSingleLedPanelRequest
import com.example.iot_lab6.definitions.wykresiki.GetHumidityResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MalinaAPI {
    @POST("/setSingleLedPanel")
    suspend fun setSingleLedPanel(@Body request: SetSingleLedPanelRequest): Response<Unit>

    @POST("/setAllLedPanel")
    suspend fun setAllLedPanel(): Response<Unit>

    @POST("/clearAllLedPanel")
    suspend fun clearAllLedPanel(): Response<Unit>

    @POST("/getHumidity")
    suspend fun getHumidity(): Response<GetHumidityResponse>

    // M wykresy
    @GET("/")
    suspend fun getWykresyData(): Response<List<WykresRow>>

//    @POST("/")
//    suspend fun test(@Body request: String): Response<String>
}