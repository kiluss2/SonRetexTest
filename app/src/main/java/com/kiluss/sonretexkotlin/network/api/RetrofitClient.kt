package com.kiluss.sonretexkotlin.network.api

import android.content.Context
import com.kiluss.sonretexkotlin.utils.SingletonHolder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor(private val context: Context) {
    companion object : SingletonHolder<RetrofitClient, Context>(::RetrofitClient)

    internal fun getClientUnAuthorize(url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
