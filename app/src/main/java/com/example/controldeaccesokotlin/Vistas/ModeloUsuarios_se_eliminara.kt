package com.example.controldeaccesokotlin.Vistas

data class ModeloUsuarios_se_eliminara(
    val fotoPerfil : String = "",
    val nombre : String = "",
    val primerApellido : String = "",
    val segundoApellido : String = "",
    val curso : String = "",
    val correoElectronico : String = "",
    val telefono: String = "",
    val fecha: String = "",
    val activo: Boolean,
    val bloqueado: Boolean,
    val tarjetasAsociadas : MutableList<String>
){
    val nombreCompleto: String
        get() = "$nombre $primerApellido $segundoApellido"
}
