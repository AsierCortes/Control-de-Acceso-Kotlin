package com.example.controldeaccesokotlin.bd_api


// La estamos usando de manera provisional para ver algo en las vistas, pero terminará desapareciendo
// Ya esta creada la de la API
data class ModeloUsuario (
    val id: Int = 0,
    val nombre: String = "",
    val email: String = "",
    val rol_id: Int = 0,
    val created_at: String = "",
    val updated_at: String = ""
)

data class Profile(
    val name: String = "",
    val email: String = ""
)

data class RegisterBody(
    val name: String,
    val email: String,
    val password: String,
    val password_confirmation: String
)

// No estoy segura si sea del todo necesario
data class ModeloControlAcceso(
    val acceso: ModeloAcceso,
    val incidencia: ModeloIncidencia,
    val permiso: ModeloPermiso,
    val rol: ModeloRol,
    val sala: ModeloSala,
    val tarjeta: ModeloTarjeta,
    val usuario1: ModeloUsuario1
)

//--------------- Dataclases de las entidades reales de la API
data class ModeloAcceso(
    val fecha_salida: String? =  "",
    val fecha_entrada: String = "",
    val duracion_estancia: String? = "",
    val tarjeta_id: Int = 0,
    val sala_id: Int = 0
)

data class ModeloIncidencia(
    val nombre: String = "",
    val fecha_hora: String = "",
    var estado: String = "",
    val tipo_incidencia: String = "",
    var motivo_denegacion: String = "",
    val sala_id: Int =1
)

data class ModeloPermiso(
    val hora_inicio: String,
    val hora_fin: String,
    val dias_semana: Int,
    val sala_id: Int
)

data class ModeloRol(
    val tipo: String
)

data class ModeloSala(
    val nombre: String,
    val ubicacion: String,
    val estado: String,
    val capacidad: Double,
    val tipo_cerradura: String
)

data class ModeloTarjeta(
    val bloqueada: Boolean,
    val usuario_id: Int
)

data class ModeloUsuario1(
    val nombre: String,
    val email: String,
    val rol_id: Int
)