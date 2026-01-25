package com.example.controldeaccesokotlin.bd_api

import com.example.controldeaccesokotlin.dao.DaoControlAcceso
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java


// Singleton
object API {
    private const val BASE_URL = "http://2.139.157.85:8002/api/v1/"


    val okHttpClient = OkHttpClient.Builder()
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

    //Lazy: Se inicializa sólo cuando se necesita.
    val apiDao: DaoControlAcceso by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) //Algoritmo de conversión
            .build()
            .create(DaoControlAcceso::class.java)
    }

}
