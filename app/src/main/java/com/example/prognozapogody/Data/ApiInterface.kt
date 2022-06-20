package com.example.prognozapogody.Data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("data/2.5/weather?pl&APPID=a6a97ce5753dc5b79a00c6f01a207dbf")
    fun getData(@Query("q") cityName : String): Call<Weather>
}