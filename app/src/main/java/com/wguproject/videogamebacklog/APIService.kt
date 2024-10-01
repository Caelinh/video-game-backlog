package com.wguproject.videogamebacklog



import com.api.igdb.apicalypse.APICalypse
import com.wguproject.videogamebacklog.data.APIGameItem
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface APIService{

    @Headers("x-api-key: LyTid97mtE6PnFHSnIRn5rOQdYyo0JR9mq2AtWL7","Content-Type: text/plain")
    @POST("games")
    suspend fun getGames(@Body query:RequestBody): List<APIGameItem>

    @Headers("x-api-key: LyTid97mtE6PnFHSnIRn5rOQdYyo0JR9mq2AtWL7",)
    @POST("search")
    suspend fun searchGames(@Body string: String): List<APIGameItem>

    @Headers("x-api-key: LyTid97mtE6PnFHSnIRn5rOQdYyo0JR9mq2AtWL7","Content-Type: text/plain")
    @POST("covers")
    suspend fun getCovers(@Body query:RequestBody): List<CoverResponse>


}

private val retrofit = Retrofit.Builder().baseUrl("https://pxatfro29b.execute-api.us-east-1.amazonaws.com/production/v4/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
val vgbService = retrofit.create(APIService::class.java)
data class CoverResponse(val image_id: String)
