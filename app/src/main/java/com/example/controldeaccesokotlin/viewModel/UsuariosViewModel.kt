package com.example.controldeaccesokotlin.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.controldeaccesokotlin.bd_api.API
import com.example.controldeaccesokotlin.bd_api.ModeloUsuario1
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsuariosViewModel : ViewModel() {

    private val _usuarios = MutableStateFlow<List<ModeloUsuario1>>(emptyList())
    val usuarios = _usuarios.asStateFlow()

    fun getUsuarios() {
        viewModelScope.launch {

            val response = API.apiDao.getUsuarios()

            if (response.isSuccessful && response.body() != null) {

                val body = response.body() as JsonObject
                val data = body.getAsJsonArray("data")

                println("Tamaño: ${data.size()}")
                println("Primer Usuario: ${data[0]}")


                val listaUsuarios = data.map { json ->
                    ModeloUsuario1(
                        nombre = json.asJsonObject.get("nombre")?.asString ?: "",
                        email = json.asJsonObject.get("email")?.asString ?: "",
                        rol_id = json.asJsonObject.get("rol_id")?.asInt?: 0
                    )
                }

                _usuarios.value = listaUsuarios

            } else {
                println("Código: ${response.code()} y mensaje: ${response.message()}")
            }
        }
    }
}