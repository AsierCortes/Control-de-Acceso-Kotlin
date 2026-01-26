package com.example.controldeaccesokotlin.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.controldeaccesokotlin.bd_api.API
import com.example.controldeaccesokotlin.bd_api.EstadoIncidencia
import com.example.controldeaccesokotlin.bd_api.Incidencia
import com.example.controldeaccesokotlin.bd_api.ModeloControlAcceso
import com.example.controldeaccesokotlin.bd_api.Sala
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response

class ControlAccesoViewModel : ViewModel() {

    private val privateModelo = MutableStateFlow(ModeloControlAcceso())
    val publicModelo = privateModelo.asStateFlow()

    // Para que carge la info antes
    init {
        recogerInfoSalasPorPagina()
        recogerInfoIncidenciasPorPagina()
    }

    fun recogerInfoSalaSeleccionada(idSala: Int?) {
        if (idSala != -1) {
            viewModelScope.launch {
                val response: Response<Sala> = API.apiDao.getSalaEspecifica(idSala)
                if (response.isSuccessful) {
                    val salaSeleccionada: Sala? = response.body()

                    // Actualizar el modelo
                    // Actualizamos el modelo:
                    privateModelo.update {
                        it.copy(
                            salaSeleccionada = salaSeleccionada
                        )

                    }

                }
            }
        }
    }

    /*
        Planteamiento:
        Estados de las salas: LIBRE, OCUPADA y BLOQUEADA

        1. Calculamos la cantidad de paginas para calcular cuantas veces vamos a llamar al endpoint

        Si el totalSalas % 10 no da 0 entonces -> totalSalas / 10 + 1
        2. En el view model llamamos al métodoo
        3. Diseccionamos para obtener salas y guardamos en la lista salas
        4. Actualizamos el modelo
        5. Se repite hasta la cantidad de pags

     */
    fun recogerInfoSalasPorPagina() {
        var cantidadPaginas: Int = 0;
        val gson = Gson()   // Para no tener que crear un json por cada sala
        var salasTotales: MutableList<Sala> = mutableListOf()


        viewModelScope.launch {
            var response = API.apiDao.getSalasPorPagina(cantidadPaginas)
            var cantidadDeSalas = response.body()
                ?.get("total")!!.asInt                 // Lo recibe coo JSONObject, obtenemos el primitive de total (al hacer get) y hay que convertir el dato a int

            val resultado = cantidadDeSalas % 10
            if (resultado == 0) {
                cantidadPaginas = cantidadDeSalas / 10
            } else {
                cantidadPaginas = (cantidadDeSalas / 10) + 1
            }
            println("Cantidad de paginas: " + cantidadPaginas)
            println("Cantidad de salas: " + cantidadDeSalas)


            // Bucle for hasta rellenar todas las salas
            for (i in 1..cantidadPaginas) {
                val respuestaServidor = API.apiDao.getSalasPorPagina(i)
                val jsonArray = respuestaServidor.body()?.get("data")?.asJsonArray
                /*
                    El problema es que es un JSON array, y yo quiero convertirlo los JSONELements (es decir, cada sala)
                    en object sala, es por ello que utilizo un map y recorro el JSON array para convertir cada JSON ELEMENT
                    a un objecto de tipo sala
                 */
                val salasPorPagina: List<Sala> = jsonArray?.map { jsonElement ->
                    gson.fromJson(jsonElement, Sala::class.java)
                } ?: emptyList()
                salasTotales.addAll(salasPorPagina)     // Agregamos a salas totales
                println("Pagina " + i + ", salas obtenidas: " + salasPorPagina)

            }

            // Ya tenemos todas las salas, el problemas es que estan desordenadas, entonces vamos
            // a ordenarlas por id. El sorted by no modifica la lsita original, te devuelve una copia nueva
            // ordenada
            val salasOrdenadas = salasTotales.sortedBy { sala ->
                sala.id
            }
            val salasLibres = salasOrdenadas.filter { sala ->
                sala.estado.equals("libre", ignoreCase = true)
            }
            val salasOcupadas = salasOrdenadas.filter { sala ->
                sala.estado.equals("ocupada", ignoreCase = true)
            }
            val salasBloqueadas = salasOrdenadas.filter { sala ->
                sala.estado.equals("bloqueada", ignoreCase = true)
            }

            // Actualizamos el modelo:
            privateModelo.update {
                it.copy(
                    salas = salasOrdenadas,
                    salasLibres = salasLibres,
                    salasOcupadas = salasOcupadas,
                    salasBloqueadas = salasBloqueadas
                )

            }

            println(" ")
            var contador: Int = 1
            for (sala in salasOrdenadas) {
                println(contador.toString() + ". " + sala.nombre)
                contador++
            }

        }
    }

    // ---------------------INCIDENCIAS------------------------------------------------
    // Recojo la información de las incidencias usando la misma lógica de las salas
    // Intentar dejar una sola funcion que podamos usar todos

    fun recogerInfoIncidenciasPorPagina() {

        var cantidadPaginas: Int = 0;
        val gson = Gson()
        var incidenciasTotales: MutableList<Incidencia> = mutableListOf()

        viewModelScope.launch {
            var response = API.apiDao.getIncidenciasPorPagina(cantidadPaginas)
            var totalIncidencias = response.body()?.get("total")!!.asInt
            val resultado = totalIncidencias % 10
            if (resultado == 0) {
                cantidadPaginas = totalIncidencias / 10
            } else {
                cantidadPaginas = (totalIncidencias / 10) + 1
            }
            println("Cantidad de paginas: $cantidadPaginas")
            println("Cantidad de incidencias: $totalIncidencias")

            for (i in 1..cantidadPaginas) {

                val respuestaServidor = API.apiDao.getIncidenciasPorPagina(i)
                val jsonArray = respuestaServidor.body()?.get("data")?.asJsonArray
                val incidenciasPorPagina: List<Incidencia> = jsonArray?.map { jsonElement ->
                    gson.fromJson(jsonElement, Incidencia::class.java)
                } ?: emptyList()
                incidenciasTotales.addAll(incidenciasPorPagina)
                println("Pagina $i incidencias, obtenidas: $incidenciasPorPagina")
            }

            privateModelo.update {
                it.copy(
                    incidencias = incidenciasTotales
                )
            }
        }
    }

    // Para llamar el endpoint que hace el update a la api
    fun actualizarEstadoIncidencia(idIncidencia: Int, estado: String, motivoDenegacion: String) {

        print("PRUEBA ID ------------ $idIncidencia")
        val estadoCambiado = EstadoIncidencia(idIncidencia, estado, motivoDenegacion)

        viewModelScope.launch {

            val response: Response<Incidencia> =
                API.apiDao.updateIncidencias(idIncidencia, estadoCambiado)
            if (response.isSuccessful) {

                val incidenciaEstadoCambiado: Incidencia? = response.body()
                print("PRUEBA NUEVO ESTADO ----------------- ${incidenciaEstadoCambiado?.estado}")

                // Lo hice de esta forma, ya que al ser llamada desde una LazyColum, no se deja actualizar, asi que es una "lista auxiliar" para ver lo cambios
                if (incidenciaEstadoCambiado != null) {
                    privateModelo.update { estadoActual ->

                        val nuevaLista = estadoActual.incidencias.map {
                            if (it.id == idIncidencia) incidenciaEstadoCambiado else it
                        }
                        print("PRUEBA LISTA NUEVA ------------------ $nuevaLista")
                        estadoActual.copy(incidencias = nuevaLista)
                    }
                } else {
                    println(
                        "RESPUESTA API : ${response.code()} Mensaje: ${
                            response.errorBody()?.string()
                        }"
                    )

                }
            }
        }
    }

    // ------------ ACCESOS --------------------------------------------
}