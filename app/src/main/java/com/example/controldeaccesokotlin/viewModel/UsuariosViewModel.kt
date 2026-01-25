package com.example.controldeaccesokotlin.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.controldeaccesokotlin.bd_api.API
import com.example.controldeaccesokotlin.bd_api.ModeloRol
import com.example.controldeaccesokotlin.bd_api.ModeloUsuario1
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class UsuariosViewModel : ViewModel() {
    private val _usuarios = MutableStateFlow<List<ModeloUsuario1>>(emptyList())
    val usuarios = _usuarios.asStateFlow()
    private var usuariosOriginales = emptyList<ModeloUsuario1>()

    private fun mapearUsuarios(body: com.google.gson.JsonObject): List<ModeloUsuario1> {
        val data = body.getAsJsonArray("data") ?: return emptyList()
        return data.map { json ->
            val obj = json.asJsonObject
            val rolObj = if (obj.has("rol") && !obj.get("rol").isJsonNull) obj.getAsJsonObject("rol") else null

            ModeloUsuario1(
                nombre = obj.get("nombre")?.asString ?: "",
                email = obj.get("email")?.asString ?: "",
                rol_id = obj.get("rol_id")?.asInt ?: 0,
                rol = rolObj?.let {
                    ModeloRol(
                        id = it.get("id").asInt,
                        tipo = it.get("tipo").asString
                    )
                }
            )
        }
    }
    fun getUsuarios() {
        viewModelScope.launch {
            try {
                val response = API.apiDao.getUsuarios()
                if (response.isSuccessful && response.body() != null) {
                    usuariosOriginales = mapearUsuarios(response.body()!!)
                    _usuarios.value = usuariosOriginales
                }
            } catch (e: Exception) { /* Manejar error */ }
        }
    }
    fun buscarUsuarios(nombre: String) {
        if (nombre.isBlank()) {
            _usuarios.value = usuariosOriginales
            return
        }
        viewModelScope.launch {
            try {
                val response = API.apiDao.getUsuariosPorNombre(nombre = nombre)
                if (response.isSuccessful && response.body() != null) {
                    _usuarios.value = mapearUsuarios(response.body()!!)
                }
            } catch (e: Exception) { /* Manejar error */ }
        }
    }
}
