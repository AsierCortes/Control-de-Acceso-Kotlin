package com.example.controldeaccesokotlin.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.controldeaccesokotlin.bd_api.API
import com.example.controldeaccesokotlin.bd_api.ModeloHistorial_se_eliminara
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HistorialViewModel : ViewModel(){

    // Aqui iré obteniendo información del Modelo de la pantalla relacionada con el Historial
    private val modeloHistorial = MutableStateFlow(ModeloHistorial_se_eliminara())
    val getModeloHistorial = modeloHistorial.asStateFlow() // Este es el que usamos para llamar en la vista

    fun getAcceso(){
        viewModelScope.launch {
            val response = API.apiDao.getAcceso()
            if(response.isSuccessful){
                println(response.body())
            }else{
                println("Código: ${response.code()} y mensaje: ${
                    response.message()}")
            }
        }
    }
}