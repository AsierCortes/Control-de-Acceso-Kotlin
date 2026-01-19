package com.example.controldeaccesokotlin.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.controldeaccesokotlin.bd_api.API
import com.example.controldeaccesokotlin.bd_api.ModeloUsuario
import com.example.controldeaccesokotlin.bd_api.Profile
import com.example.controldeaccesokotlin.bd_api.RegisterBody
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsuariosViewModel : ViewModel() {

//    //Para cuando nos podamos registrar y obtener el token
//    private val modeloReal = MutableStateFlow(Profile())
//    val getModelo = modeloReal.asStateFlow()
//
//    fun registrarUsuario() {
//        viewModelScope.launch {
//            val response = API.apiDao.registerUser(
//
//                RegisterBody(
//                    "Supernenas",
//                    "supernenas@test.com",
//                    "12345678",
//                    "12345678"
//                )
//            )
//
//            if (response.isSuccessful) {
//                println(response.body())
//            } else {
//                println(
//                    "Código: ${response.code()} y mensaje: ${
//                        response.message()
//                    }"
//                )
//            }
//        }
//    }
}