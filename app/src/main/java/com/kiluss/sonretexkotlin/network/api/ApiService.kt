package com.kiluss.sonretexkotlin.network.api

import com.google.gson.JsonObject
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET

interface ApiService {
    @GET(".")
    fun getMovie(): Call<JsonObject>
}

