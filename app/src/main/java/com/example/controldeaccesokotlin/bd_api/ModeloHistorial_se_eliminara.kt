package com.example.controldeaccesokotlin.bd_api

//import androidx.room.Entity
//import androidx.room.PrimaryKey
//
//@Entity(tableName = "historial")


//--------- En cuanto se genere la conexión, esta dataclass se usara con
// ---------los atributos que consumamos de la API
data class ModeloHistorial_se_eliminara(
//    @PrimaryKey(true) val id: Int = 0,
    val id: Int = 0,
    val sala: String = "",
    val usuario: String = "",
    val fecha: String = "",
    val hora: String = "",
    val evento: String = ""
)



