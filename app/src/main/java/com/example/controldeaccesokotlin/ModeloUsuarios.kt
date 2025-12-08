package com.example.controldeaccesokotlin

data class ModeloUsuarios(
    val fotoPerfil : String = "",
    val nombre : String = "",
    val primerApellido : String = "",
    val segundoApellido : String = "",
    val curso : String = ""
){
    val nombreCompleto: String
        get() = "$nombre $primerApellido $segundoApellido"
}