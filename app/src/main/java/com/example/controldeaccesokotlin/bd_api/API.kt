package com.example.controldeaccesokotlin.bd_api

import com.example.controldeaccesokotlin.dao.DaoControlAcceso
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Singleton
object API {

    private const val BASE_URL = "http://2.139.157.85:8002/api/v1/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .addHeader("Authorization", "5|qPBzj6TVSpQ0WUrgcqHpWhJAaXrFaUWInUgmrAEpd70a40d8")
                .addHeader("Accept", "application/json")
                .method(original.method, original.body)
                .build()
            chain.proceed(request)
        }
        .build()

    val apiDao: DaoControlAcceso by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DaoControlAcceso::class.java)
    }
}