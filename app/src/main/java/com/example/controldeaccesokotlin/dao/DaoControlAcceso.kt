package com.example.controldeaccesokotlin.dao

import com.example.controldeaccesokotlin.Vistas.Usuario
import com.example.controldeaccesokotlin.bd_api.*
import com.google.gson.JsonObject
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

    @GET("/incidencia")
    suspend fun getIncidencias(): Response<Any>

    @GET("/incidencia")
    suspend fun getIncidenciasPorSala(@Query("includes") sala: String): Response<Any>

    @POST( "/incidencia")
    suspend fun createIncidencias(@Body registrarIncidencias: ModeloIncidencia): Response<Any>

    @PUT("/incidencia/{id}")
    suspend fun updateIncidencias(@Path("id") id: Int): Response<Any>

    @DELETE("/incidencia/{id}")
    suspend fun deleteIncidencias(@Path("id") id: Int): Response<Any>



    // Endpoints para PERMISO ------------

    @GET("/permiso")
    suspend fun getPermisos(): Response<Any>

    @GET("/permiso")
    suspend fun getPermisosPorSala(@Query("includes") sala: String): Response<Any>

    @POST("/permiso")
    suspend fun createPermisos(@Body registrarPermisos: ModeloPermiso): Response<Any>

    @PUT("/permiso/{id}")
    suspend fun updatePermisos(@Path("id") id: Int): Response<Any>

    @DELETE("/permiso/{id}")
    suspend fun deletePermisos(@Path("id") id: Int): Response<Any>



    // Endpoints para ROL ------------

    @GET("/rol")
    suspend fun getRolesPorSala(@Query("includes") sala: String): Response<Any>

    @GET("/rol")
    suspend fun getRoles(): Response<Any>

    @POST("/rol")
    suspend fun createRoles(@Body registrarRoles: ModeloRol): Response<Any>

    @PUT("/rol/{id}")
    suspend fun updateRoles(@Path("id") id: Int): Response<Any>

    @DELETE("/rol/{id}")
    suspend fun deleteRoles(@Path("id") id: Int): Response<Any>



    // Endpoints para SALA ------------

    // Retrofit agrega automáticamente el "?page="
    @GET("sala")        // Devuelve JSON OBJECT
    suspend fun getSalasPorPagina(@Query("page") num: Int): Response<JsonObject>

    @GET("sala/{id}")        // Devuelve JSON OBJECT
    suspend fun getSalaEspecifica(@Path("id") num: Int?): Response<Sala>




    // Endpoints para TARJETA ------------


    @GET("/tarjeta")
    suspend fun getTarjetas(): Response<Any>

    @GET("/tarjeta")
    suspend fun getTarjetasPorUsuario(@Query("includes") usuario: Usuario): Response<Any>

    @POST("/tarjeta")
    suspend fun createTarjetas(@Body registrarTarjetas: ModeloTarjeta): Response<Any>

    @PUT("/tarjeta/{id}")
    suspend fun updateTarjetas(@Path("id") id: Int): Response<Any>

    @DELETE("/tarjeta/{id}")
    suspend fun deleteTarjetas(@Path("id") id: Int): Response<Any>



    // Endpoints para USUARIO ------------

    @GET("/usuario")
    suspend fun getUsuarios(): Response<Any>

    @GET("/usuario")
    suspend fun getUsuariosPorSala(@Query("includes") sala: String): Response<Any>

    @POST("/usuario")
    suspend fun createUsuarios(@Body registrarUsuarios: ModeloUsuario1): Response<Any>

    @PUT("/usuario/{id}")
    suspend fun updateUsuarios(@Path("id") id: Int): Response<Any>

    @DELETE("/usuario/{id}")
    suspend fun deleteUsuarios(@Path("id") id: Int): Response<Any>

}



