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

    fun getUsuarios() {
        viewModelScope.launch {
            val response = API.apiDao.getUsuarios()

            if (response.isSuccessful && response.body() != null) {
                val data = response.body()!!.getAsJsonArray("data")

                usuariosOriginales = data.map { json ->
                    val obj = json.asJsonObject

                    val rolObj = obj.getAsJsonObject("rol")

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

                _usuarios.value = usuariosOriginales
            }
        }
    }

    fun filtrarPorRol(rolId: Int?) {
        _usuarios.value =
            if (rolId == null) usuariosOriginales
            else usuariosOriginales.filter { it.rol_id == rolId }
    }
}
