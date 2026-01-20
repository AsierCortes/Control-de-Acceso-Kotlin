package com.example.controldeaccesokotlin.dao

import com.example.controldeaccesokotlin.bd_api.ModeloAcceso
import com.example.controldeaccesokotlin.bd_api.RegisterBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface DaoControlAcceso {


//  // Para cuando nos podamos registrar y obtener el token
//   @POST("register")
//   suspend fun registerUser(@Body registerBody: RegisterBody): Response<Any>
//
//    @GET("usuario")
//    suspend fun getUsuario(): Response<Any>

    // Endpoints para ACCESO ------------

    @GET("/acceso")
    suspend fun getAccesoPorSala(@Query("includes") sala: String): Response<Any>

    @GET("/acceso")
    suspend fun getAcceso(): Response<Any>

    @POST
    suspend fun createAcceso(@Body registrarAcceso: ModeloAcceso): Response<Any>

    @PUT ("/acceso/{id}")
    suspend fun updateAcceso(@Path("id") id: Int): Response<Any>

    @DELETE ("/acceso/{id}")
    suspend fun deleteAcceso(@Path("id") id: Int): Response<Any>

    // Endpoints para INCIDENCIA ------------

}

