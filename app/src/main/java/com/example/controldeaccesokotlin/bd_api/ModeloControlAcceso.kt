package com.example.controldeaccesokotlin.bd_api

import com.google.gson.annotations.SerializedName

// MODELO PRINCIPAL
data class ModeloControlAcceso(
    // PARA SALAS
    val salas : List<Sala> = emptyList(),
    val salasLibres : List<Sala> = emptyList(),
    val salasOcupadas : List<Sala> = emptyList(),
    val salasBloqueadas : List<Sala> = emptyList(),
    val salaSeleccionada : Sala? = null,

    // PARA USUARIOS
    val usuraios : List<Usuario> = emptyList<Usuario>()


)



data class Sala(
    val id: Int,
    val nombre: String,
    val capacidad: Double,
    val ubicacion: String,
    val estado: String,
    val tipo_cerradura: String,

    // Lo he querido poner el atributo en español, para ello le indico a JSON
    // que el atributo JSON se llama create_at
    @SerializedName("created_at")
    val fechaSalaCreada: String,
    @SerializedName("updated_at")
    val fechaSalaActualizada: String
)

data class Usuario (
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