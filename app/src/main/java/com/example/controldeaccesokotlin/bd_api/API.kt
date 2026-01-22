package com.example.controldeaccesokotlin.bd_api

import com.example.controldeaccesokotlin.dao.DaoControlAcceso
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java


// Singleton

object API {
    private const val BASE_URL = "http://2.139.157.85:8002/api/v1/"

    // Para registro e inicio de sesion
    private const val BASE_URL_INICIO_SESION = "http://2.139.157.85:8000/api/v1/"

    //Lazy: Se inicializa sólo cuando se necesita.
    val apiDao: DaoControlAcceso by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) //Algoritmo de conversión
            .build()
            .create(DaoControlAcceso::class.java)
    }

}
