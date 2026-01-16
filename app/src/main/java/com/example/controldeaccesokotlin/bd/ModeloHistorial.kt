package com.example.controldeaccesokotlin.bd

//import androidx.room.Entity
//import androidx.room.PrimaryKey
//
//@Entity(tableName = "historial")
data class ModeloHistorial(
//    @PrimaryKey(true) val id: Int = 0,
    val id: Int = 0,
    val sala: String = "",
    val usuario: String = "",
    val fecha: String = "",
    val hora: String = "",
    val evento: String = ""
)