package com.example.controldeaccesokotlin.Vistas

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import com.example.controldeaccesokotlin.ModeloHistorial
import com.example.controldeaccesokotlin.ModeloUsuarios
import com.example.controldeaccesokotlin.R
import kotlinx.coroutines.launch
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Notificaciones() {

    // Una variable para mostrar en ModalBottomSheet, AlertDialog o DropDown las opciones de exportar y filtrar
    var verOpcionesExportado by remember { mutableStateOf(false) }
    var verOpcionesFiltrado by remember { mutableStateOf(false) }

    // En una lista almaceno la información necesaria para cada registro
    val eventos: List<ModeloHistorial> = listOf(
        ModeloHistorial(
            1,
            "Sala 1",
            "Ususario 1",
            "01-12-2025",
            "16:53",
            "Puerta Abierta"
        ),// Puede ser un set con los diferentes eventos posibles
        ModeloHistorial(
            2,
            "Sala 3",
            "Ususario 2",
            "01-12-2025",
            "15:53",
            "Acceso Denegado"
        ),
        ModeloHistorial(
            3,
            "Sala 5",
            "Ususario 3",
            "01-12-2025",
            "14:25",
            "Puerta Abierta"
        ),
        ModeloHistorial(
            4,
            "Sala 6",
            "Ususario 1",
            "01-12-2025",
            "12:00",
            "Puerta Abierta"
        ),
        ModeloHistorial(
            5,
            "Sala 1",
            "Ususario 4",
            "01-12-2025",
            "12:00",
            "Acceso Denegado"
        ),
        ModeloHistorial(
            6,
            "Sala 5",
            "Ususario 6",
            "01-12-2025",
            "12:00",
            "Puerta Abierta"
        ),
        ModeloHistorial(
            7,
            "Sala 5",
            "Ususario 4",
            "01-12-2025",
            "12:00",
            "Acceso Denegado"
        ),
        ModeloHistorial(
            8,
            "Sala 5",
            "Ususario 4",
            "01-12-2025",
            "12:00",
            "Puerta Abierta"
        )

    )

    // Lo puse en una Column para que no se solape. No sé si afecte el funcionamiento de la LC
    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            "Historial de eventos",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Visualiza y exporta el historial de eventos de las salas",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        //------------------ Buscador del que luego tomaré sólo la función del de Asier
        var textoIntroducido by remember { mutableStateOf("") }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 1. EL BUSCADOR
            OutlinedTextField(
                value = textoIntroducido,
                onValueChange = { textoIntroducido = it }, // Esto actualiza el estado correctamente
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp), // Mantenemos la altura compacta
                placeholder = {
                    Text(
                        text = "Buscar...",
                        style = typography.bodyMedium,
                        color = Color.Gray
                    )
                },
                singleLine = true,
                textStyle = typography.bodyMedium, // IMPORTANTE: Texto un poco más pequeño para que quepa bien
                shape = RoundedCornerShape(10.dp),

                // --- HE BORRADO LA LÍNEA contentPadding QUE DABA ERROR ---

                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = Color.LightGray
                )
            )

            // 2. EL BOTÓN
            Button(
                onClick = {},
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = Modifier
                    .height(50.dp) // Misma altura que el input
            ) {
                Text(
                    text = "Buscar",
                    style = typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        // ---------------------------- Fin del buscador

        // Fila con los botones de filtro y para exportar
        // Escoger entre ModalBottomSheet, AlertDialog o DropDown
        Row(Modifier.padding(vertical = 20.dp)) {
            Button(
                onClick = { verOpcionesFiltrado = true },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF455A64)),
                modifier = Modifier
                    .weight(1f) // Hace referencia a unidades de peso, tal como flex-grow en CSS
                    .padding(end = 8.dp)
            ) { Text("Filtrar") }
            Button(
                onClick = { verOpcionesExportado = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) { Text("Exportar") }
        }

        FormacionCardsRegistros(eventos)


        // Llamo las funciones para mostrar las opciones de exportado/filtrado tras darle al boton
        if (verOpcionesExportado) {
            OpcionesExportado({ verOpcionesExportado = false })
        }

        if (verOpcionesFiltrado) {
            OpcionesFiltrado({ verOpcionesFiltrado = false })

        }
    }
}

@Composable
fun BotonesFiltrarExportar(verOpcionesFiltrado: Boolean, verOpcionesExportado: Boolean) {
    // Funcion con LazyColumn donde se irán almacenando los registros
    // TODO. No funcionaban los botones si los ponia en una funcion aparte. Pendiente por lograr
}


@Composable
fun FormacionCardsRegistros(eventos: List<ModeloHistorial>) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        items(items = eventos, key = { it.id }) { eventoAMostrar ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Row(Modifier.padding(10.dp)) {
                    Icon(
                        painterResource(
                            if (eventoAMostrar.evento == "Acceso Denegado") {
                                R.drawable.outline_lock_24
                            } else {
                                R.drawable.outline_lock_open_right_24
                            }
                        ), "Estado de Acceso",
                        tint =
                            if (eventoAMostrar.evento == "Acceso Denegado") {
                                Color(0xFFC62828)
                            } else {
                                Color(0xFF2E7D32)
                            },
                        modifier = Modifier
                            .padding(
                                horizontal = 20.dp,
                                vertical = 2.dp
                            )
                    )

                    Column {
                        Text(
                            eventoAMostrar.evento,
                            modifier = Modifier
                                .padding(2.dp),
//                                style = MaterialTheme.typography.titleLarge,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Row {
                            Text(
                                eventoAMostrar.sala,
                                modifier = Modifier
                                    .padding(2.dp),
                                style = MaterialTheme.typography.bodyLarge,
                            )
                            Text(
                                eventoAMostrar.usuario,
                                modifier = Modifier
                                    .padding(2.dp),
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                        Row {
                            Text(
                                eventoAMostrar.fecha,
                                modifier = Modifier
                                    .padding(2.dp),
                            )
                            Text(
                                eventoAMostrar.hora,
                                modifier = Modifier
                                    .padding(2.dp),
                            )
                        }

                        val expandir = false // Terminar
                        // TODO. Mostrar un desplegable dónde se vea el detalle del evento
                        // (ex: por qué se denegó a entrada, cuanto tiempo duró la puerta abierta...)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (expandir) "Ocultar detalles" else "Ver detalles",
                                style = typography.bodyMedium,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                imageVector = if (expandir) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = "Expandir/Contraer"
                            )
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpcionesExportado(onDismiss: () -> Unit) {

    val context = LocalContext.current // Context para mostrar Toast

    Dialog(
        onDismissRequest = { onDismiss() },    // Si pulsa fuera del dialog
        properties = DialogProperties(usePlatformDefaultWidth = false)      // para que el fondo oscurecido no sea tan brusco
    ) {

        // El dialog centra automáticamente la carta
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)  // 90% ancho
                .fillMaxHeight(0.25f),

            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .align(Alignment.CenterHorizontally),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Seleccione el formato en el que desea exportar",
                    style = typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(30.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button({
                        Toast.makeText(context, "CSV Exportado con éxito", Toast.LENGTH_SHORT)
                            .show()
                    }) { Text("CSV") }
                    Button({
                        Toast.makeText(context, "JSON Exportado con éxito", Toast.LENGTH_SHORT)
                            .show()
                    }) { Text("JSON") }

                }
            }
        }
    }


//------------ CAMBIADO A DIALOG -----------
//    val sheetState = rememberModalBottomSheetState()
//    Column(Modifier.padding(50.dp)){
//        ModalBottomSheet(
//            { onDismiss() },
//            sheetState = sheetState
//        ) {
//
//
//            Text("Seleccione el formato en el que desea exportar ")
//            Row(
//                Modifier
//                    .fillMaxWidth()
//                    .padding(30.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceAround
//            ) {
//                Button({}) { Text("CSV") }
//                Button({}) { Text("JSON") }
//            }
//        }
//    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpcionesFiltrado(onDismiss: () -> Unit) {

    // Me copio la lista de Uusarios de la DataClass de Usuarios creada por Asier
    // TODO. Unificar todo este tipo de cosas de modo que no repitamos código
    val usuario1 = ModeloUsuarios("1", "Jennyfer", "Dyanna", "Triana", "2º DAMP")
    val usuario2 = ModeloUsuarios("2", "Kevin", "Estévez", "García", "1º DAM")
    val usuario3 = ModeloUsuarios("3", "Marta", "Laguna", "Pérez", "2º DAW")
    val usuario4 = ModeloUsuarios("4", "Iker", "Unzueta", "Bilbao", "1º ASIR")
    val usuario5 = ModeloUsuarios("5", "Sofía", "Orellana", "Ruiz", "2º DAMP")
    val usuario6 = ModeloUsuarios("6", "Carlos", "Sánchez", "Mora", "2º ASIR")
    val usuario7 = ModeloUsuarios("7", "Laura", "Gómez", "Vázquez", "1º DAW")
    val usuario8 = ModeloUsuarios("8", "Javier", "Hernández", "Díaz", "2º ASIR")
    val usuario9 = ModeloUsuarios("9", "Cristina", "López", "Martín", "1º DAMP")
    val usuario10 = ModeloUsuarios("10", "Adrián", "Pérez", "Sánchez", "2º DAW")
    val usuario11 = ModeloUsuarios("11", "Natalia", "Gil", "Castro", "1º DAM")
    val usuario12 = ModeloUsuarios("12", "Sergio", "Ramos", "García", "2º ASIR")
    val usuario13 = ModeloUsuarios("13", "Patricia", "Molina", "Serrano", "1º DAW")
    val usuario14 = ModeloUsuarios("14", "Diego", "Ortiz", "Iglesias", "2º DAMP")
    val usuario15 = ModeloUsuarios("15", "Beatriz", "Navarro", "Romero", "1º ASIR")

    // Lista usuarios ejemplo
    val usuarios: MutableList<ModeloUsuarios> =
        mutableListOf(
            usuario1,
            usuario2,
            usuario3,
            usuario4,
            usuario5,
            usuario6,
            usuario7,
            usuario8,
            usuario9,
            usuario10,
            usuario11,
            usuario12,
            usuario13,
            usuario14,
            usuario15
        )

    val salas = mapOf(
        1 to "Sala 1",
        2 to "Sala 2",
        3 to "Sala 3",
        4 to "Sala 4",
        5 to "Sala 5",
        6 to "Sala 6",
    )
    val tipoEventos = listOf(
        "Todos",
        "Acceso Denegado",
        "Aperturas Forzadas",
        "Puertas Abiertas",
        "Demasiado Tiempo",
        "Bloqueo de Sala"
    )
    Dialog(
        onDismissRequest = { onDismiss() },    // Si pulsa fuera del dialog
        properties = DialogProperties(usePlatformDefaultWidth = false)      // para que el fondo oscurecido no sea tan brusco
    ) {

        // El dialog centra automáticamente la carta
        Card(
            modifier = Modifier
                .fillMaxSize(0.9f),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)

        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            )

            {
                Text(
                    text = "Opciones de filtrado",
                    style = typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(Modifier.padding(10.dp))

                // Tengo una funcion para llamar los diferentes tipos, que seguramente se transformará en tres diferentes funciones
                FiltrarPorUsuarios(usuarios)
                Spacer(modifier = Modifier.height(15.dp))

                FiltrarPorUsuarioSalas(salas, "salas")
                Spacer(modifier = Modifier.height(15.dp))

                FiltrarPorEvento(tipoEventos)
                Spacer(modifier = Modifier.height(15.dp))

                FiltrarPorFecha()
            }
        }
    }

//    ModalBottomSheet(
//        onDismissRequest =
//            { onDismiss() }
//    ) {
//        val scroll = rememberScrollState()
//
//        Column(Modifier.padding(20.dp)) {
//
//            // Tengo una funcion para llamar los diferentes tipos, que seguramente se transformará en tres diferentes funciones
//            FiltrarPorUsuarioSalas(usuarios, "usuarios")
//            Spacer(modifier = Modifier.height(15.dp))
//
//            FiltrarPorUsuarioSalas(salas, "salas")
//            Spacer(modifier = Modifier.height(15.dp))
//
//            FiltrarPorUsuarioSalas(eventos, "eventos")
//            Spacer(modifier = Modifier.height(15.dp))
//
//            FiltrarPorFecha()
//
//            // TODO. Buscar mejores formas de integrar los filtros ya que no es viable si la app escala
//        }
//    }
}

@Composable
fun FiltrarPorUsuarioSalas(mapa: Map<Int, String>, cadena: String) {

    val seleccionados = remember { mutableStateMapOf<Int, Boolean>() }

    Text(
        "Filtrar por $cadena",
        style = typography.bodyLarge
    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.Center
    ) {
        mapa.forEach { (id, usuario) ->
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = seleccionados[id] ?: false,
                        onCheckedChange = {
                            seleccionados[id] = it
                        }
                    )
                    Text(
                        usuario,
                        fontSize = 13.sp,
                        modifier = Modifier.clickable {
                            val actual = seleccionados[id] ?: false
                            seleccionados[id] = !actual
                        }
                    )
                }
            }
        }
    }

}

@Composable
fun FiltrarPorUsuarios(usuarios: List<ModeloUsuarios>) {
    Column (Modifier.fillMaxHeight(0.32f),
            verticalArrangement = Arrangement.spacedBy(10.dp))
    {

    Text(
        "Usuarios",
        style = typography.bodyLarge
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Recorre la lista de usuarios (Instancias ModeloUsuario)
        items(usuarios) { usuarioActual ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceBright),
                border = BorderStroke(1.dp, Color.Black),
                shape = RoundedCornerShape(4.dp)

            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.perfilusuario),
                        contentDescription = "foto perfil usuario",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(60.dp)
                    )

                    Text(text = usuarioActual.nombreCompleto)

                    Checkbox(false, {}

//                        checked = seleccionados[id] ?: false,
//                        onCheckedChange = {
//                            seleccionados[id] = it
//                        }
                    )
                }
            }
        }

        }
    }


}

@Composable
fun FiltrarPorEvento(eventos: List<String>) {

    var expandir by remember { mutableStateOf(false) } // Para usar el DropDown
    var seleccion by remember { mutableStateOf(eventos[0]) } // Para recordar selección, en principio mostrará la opción "Todos"


    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
//                .padding(8.dp)
    ) {
        Text(
            "Tipo de evento",
            style = typography.bodyLarge,
            modifier = Modifier.weight(0.3f)
        )

        Row(
            Modifier
                .border(1.dp, Color.Black)
                .fillMaxWidth(0.60f)
                .padding(5.dp)
                .clickable { expandir = true },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(seleccion,
                Modifier.padding(start = 5.dp))
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Desplegar"
            )
        }
    }
    DropdownMenu(
        expanded = expandir,
        onDismissRequest = { expandir = false }
    ) {
        eventos.forEach { evento ->
            DropdownMenuItem(
                text = { Text(evento) },
                onClick = {
                    seleccion = evento
                    expandir = false
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
// Ejemplo copiado exactamente del ej de la documentacion de Material3
fun FiltrarPorFecha() {
    //TODO Adaptarlo al ejercicio, que se vea en un cuadro de dialogo
    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()
    SnackbarHost(hostState = snackState, Modifier.zIndex(1f))

    val state = rememberDateRangePickerState()
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
        // Add a row with "Save" and dismiss actions.

        Text(
            "Filtrar por fecha",
            style = typography.bodyLarge
        )
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(DatePickerDefaults.colors().containerColor)
                    .padding(start = 12.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { /* dismiss the UI */ }) {
                Icon(Icons.Filled.Close, contentDescription = "Localized description")
            }
            TextButton(
                onClick = {
                    snackScope.launch {
                        val range =
                            state.selectedStartDateMillis!!..state.selectedEndDateMillis!!
                        snackState.showSnackbar("Saved range (timestamps): $range")
                    }
                },
                enabled = state.selectedEndDateMillis != null
            ) {
                Text(text = "Save")
            }
        }
        DateRangePicker(state = state, modifier = Modifier.weight(1f))
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewNotificaciones() {
    Notificaciones()
}

@Preview(showBackground = true)
@Composable
fun PreviewOpcionesExportado() {
    OpcionesExportado({})
}

@Preview(showBackground = true)
@Composable
fun PreviewOpcionesFiltrado() {
    OpcionesFiltrado ({})
}