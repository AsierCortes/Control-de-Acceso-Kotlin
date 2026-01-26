package com.example.controldeaccesokotlin.Vistas

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerDefaults.DateRangePickerHeadline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.controldeaccesokotlin.bd_api.ModeloHistorial_se_eliminara
import com.example.controldeaccesokotlin.R
import com.example.controldeaccesokotlin.bd_api.Incidencia
import com.example.controldeaccesokotlin.bd_api.ModeloAcceso
import com.example.controldeaccesokotlin.bd_api.Sala
import com.example.controldeaccesokotlin.viewModel.ControlAccesoViewModel
import kotlin.collections.mutableListOf
import kotlin.collections.set


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Notificaciones() {

    val eventos: List<ModeloHistorial_se_eliminara> = listOf(
        ModeloHistorial_se_eliminara(
            1,
            "Sala 1",
            "Ususario 1",
            "01-12-2025",
            "16:53",
            "Puerta Abierta"
        ),// Puede ser un set con los diferentes eventos posibles
        ModeloHistorial_se_eliminara(
            2,
            "Sala 3",
            "Ususario 2",
            "01-12-2025",
            "15:53",
            "Apertura Forzada"
        ),
        ModeloHistorial_se_eliminara(
            3,
            "Sala 5",
            "Ususario 3",
            "01-12-2025",
            "14:25",
            "Bloqueo de Sala"
        ),
        ModeloHistorial_se_eliminara(
            4,
            "Sala 6",
            "Ususario 1",
            "01-12-2025",
            "12:00",
            "Demasiado Tiempo"
        ),
        ModeloHistorial_se_eliminara(
            5,
            "Sala 1",
            "Ususario 4",
            "01-12-2025",
            "12:00",
            "Acceso Denegado"
        ),
        ModeloHistorial_se_eliminara(
            6,
            "Sala 5",
            "Ususario 6",
            "01-12-2025",
            "12:00",
            "Puerta Abierta"
        ),
        ModeloHistorial_se_eliminara(
            7,
            "Sala 5",
            "Ususario 4",
            "01-12-2025",
            "12:00",
            "Acceso Denegado"
        ),
        ModeloHistorial_se_eliminara(
            8,
            "Sala 5",
            "Ususario 4",
            "01-12-2025",
            "7:00",
            "Puerta Abierta"
        ),
        ModeloHistorial_se_eliminara(
            8,
            "Sala 5",
            "Ususario 4",
            "01-12-2025",
            "12:00",
            "Puerta Abierta"
        )

    )

    var textoBuscador = "" // Para el buscador ¡Plantearse quitar el buscador!

    // Lo puse en una Column para que no se solape. No sé si afecte el funcionamiento de la LC
    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = stringResource(id = R.string.Historial_Eventos),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(id = R.string.Visualiza),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // Plantearse quitar el buscador
        Buscador(textoBuscador)

        Spacer(modifier = Modifier.height(8.dp))

        SelectorAccesosIncidencias(eventos)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorAccesosIncidencias(eventos: List<ModeloHistorial_se_eliminara>) {

    // En una lista almaceno de manera provisional las incidencias
//    val listaIncidencias = remember {
//        mutableStateListOf(
//            ModeloIncidencia(
//                nombre = "Puerta Abierta",
//                fecha_hora = "2025-11-13 09:10:00",
//                estado = "pendiente",
//                tipo_incidencia = "Puerta sin cerrar",
//                motivo_denegacion = "",
//                sala_id = 1
//            ),
//            ModeloIncidencia(
//                nombre = "Apertura Forzada",
//                fecha_hora = "2025-11-13 09:30:00",
//                estado = "aceptada",
//                tipo_incidencia = "Fuerza en cerradura",
//                motivo_denegacion = "",
//                sala_id = 2
//            ),
//            ModeloIncidencia(
//                nombre = "Bloqueo de Sala",
//                fecha_hora = "2025-11-13 10:00:00",
//                estado = "denegada",
//                tipo_incidencia = "Bloqueo manual",
//                motivo_denegacion = "Incidencia previa",
//                sala_id = 3
//            ),
//            ModeloIncidencia(
//                nombre = "Demasiado Tiempo",
//                fecha_hora = "2025-11-13 10:45:00",
//                estado = "pendiente",
//                tipo_incidencia = "Estancia prolongada",
//                motivo_denegacion = "-",
//                sala_id = 1
//            ),
//            ModeloIncidencia(
//                nombre = "Acceso Denegado",
//                fecha_hora = "2025-11-13 11:15:00",
//                estado = "aceptada",
//                tipo_incidencia = "Tarjeta bloqueada",
//                motivo_denegacion = "",
//                sala_id = 2
//            ),
//            ModeloIncidencia(
//                nombre = "Puerta Abierta",
//                fecha_hora = "2025-11-13 12:00:00",
//                estado = "pendiente",
//                tipo_incidencia = "Puerta quedó abierta tras salida",
//                motivo_denegacion = "",
//                sala_id = 3
//            ),
//            ModeloIncidencia(
//                nombre = "Acceso Denegado",
//                fecha_hora = "2025-11-13 12:20:00",
//                estado = "pendiente",
//                tipo_incidencia = "Intento de acceso sin permiso",
//                motivo_denegacion = "",
//                sala_id = 1
//            )
//        )
//    }

    //En otra lista, también provisional almaceno los accesos
    val listaAccesos = listOf(
        ModeloAcceso("2026-01-24", "2026-01-24", "01:15:00", 101, 5),
        ModeloAcceso("2026-01-24", "2026-01-24", "00:45:00", 102, 3),
        ModeloAcceso(null, "2026-01-24", null, 105, 5), // Usuario aún dentro
        ModeloAcceso("2026-01-24", "2026-01-24", "02:10:00", 110, 12),
        ModeloAcceso("2025-11-13", "2025-11-13", "01:30:00", 1, 23),
        ModeloAcceso(null, "2026-01-24", null, 115, 8), // Usuario aún dentro
        ModeloAcceso("2026-01-24", "2026-01-24", "00:20:00", 108, 2),
        ModeloAcceso("2026-01-24", "2026-01-24", "03:05:00", 120, 10),
        ModeloAcceso("2026-01-24", "2026-01-24", "00:50:00", 101, 5),
        ModeloAcceso("2026-01-24", "2026-01-24", "01:00:00", 112, 1)
    )

    val navController = rememberNavController()
    var accesos = true
    var incidencias = false

    var tabSeleccionado by remember { mutableStateOf(0) }

    PrimaryTabRow(selectedTabIndex = tabSeleccionado) {

        // Para las incidencias
        Tab(incidencias, {
            navController.navigate("incidencias")
            incidencias = true
            accesos = false

            tabSeleccionado = 0
        }) {
            Text(
                text = stringResource(id = R.string.Incidencias),
                style = typography.titleMedium,
                modifier = Modifier.padding(bottom = 6.dp)
            )
        }

        // Para los accesos
        Tab(accesos, {
            navController.navigate("accesos")
            accesos = true
            incidencias = false

            tabSeleccionado = 1
        }) {
            Text(
                text = stringResource(id = R.string.Accesos),
                style = typography.titleMedium,
                modifier = Modifier.padding(bottom = 6.dp)
            )
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    NavHost(navController, startDestination = "incidencias") {
        composable("incidencias") { FormacionCardsIncidencias() }
        composable("accesos") { FormacionCardsAccesos(eventos) }
    }
}

@Composable
fun FormacionCardsIncidencias(controller: ControlAccesoViewModel = viewModel()) {

    val publicModel = controller.publicModelo.collectAsState()

    val listaIncidencias: List<Incidencia> = publicModel.value.incidencias

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        items(items = listaIncidencias) { incidenciaAMostrar ->

            val estado = incidenciaAMostrar.estado.lowercase()
            val esPendiente = estado == "pendiente"

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Row(Modifier.padding(10.dp), verticalAlignment = Alignment.Top) {
                    Icon(
                        painterResource(
                            when (incidenciaAMostrar.nombre) {
                                "Acceso Denegado" -> R.drawable.outline_do_not_disturb_on_24
                                "Apertura Forzada" -> R.drawable.outline_report_24
                                "Puerta Abierta" -> R.drawable.outline_lock_open_right_24
                                "Demasiado Tiempo" -> R.drawable.outline_hourglass_empty_24
                                "Bloqueo de Sala" -> R.drawable.outline_lock_person_24
                                else -> {
                                    R.drawable.outline_unknown_med_24
                                }
                            }
                        ), "Estado de Acceso",
//                        tint = Color.Unspecified, // Para que respete los colores asignados a los iconos de drawable
                        modifier = Modifier
                            .padding(
                                horizontal = 20.dp,
                                vertical = 2.dp
                            )
                    )

                    Column {
                        Row(
                            Modifier.padding(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                incidenciaAMostrar.nombre,
                                modifier = Modifier
                                    .padding(2.dp)
                                    .weight(1f),
//                                style = MaterialTheme.typography.titleLarge,
                                fontSize = 19.sp,
                                fontWeight = if (esPendiente) FontWeight.ExtraBold else FontWeight.SemiBold
                            )
                            if (esPendiente) {
                                Text(
                                    "\uD83D\uDD35",
                                    modifier = Modifier
//                                        .align(Alignment.End)
                                        .padding(end = 4.dp)

                                )
                            }
                        }

                        val pesoTexto = if (esPendiente) FontWeight.Bold else FontWeight.Normal

                        Text(
                            "${incidenciaAMostrar.sala_id}", // Tengo que obtener el nombre de la sala apartir de su id con enpoints de Asier o con  un map
                            modifier = Modifier
                                .padding(2.dp),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = pesoTexto
                        )


                        Text(
                            incidenciaAMostrar.fecha_hora,
                            modifier = Modifier
                                .padding(2.dp),
                            fontWeight = pesoTexto
                        )

                        Spacer(Modifier.size(3.dp))

                        var expandir by remember { mutableStateOf(false) } // Terminar
                        // TODO. Mostrar un desplegable dónde se vea el detalle del evento
                        // (ex: por qué se denegó a entrada, cuanto tiempo duró la puerta abierta...)


                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
//                                .clickable { expandir = !expandir },
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            val colorEstado = when (estado) {
                                "aceptada" -> Color(0xFF388E3C)
                                "denegada" -> Color(0xFFB71C1C)
                                else -> Color(0xFF0B1A57)
                            }

                            val iconoEstado = when (estado) {
                                "aceptada" -> Icons.Default.Check
                                "denegada" -> Icons.Default.Close
                                else -> null
                            }

                            if (iconoEstado != null) {
                                Icon(
                                    imageVector = iconoEstado,
                                    contentDescription = null,
                                    tint = colorEstado,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                            }

                            Text(
                                text = estado.uppercase(),
                                style = typography.titleMedium,
                                color = colorEstado,
                                fontWeight = if (esPendiente) FontWeight.Black else FontWeight.SemiBold,
                                modifier = Modifier.weight(1f)
                            )

                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "Expandir",
                                Modifier.clickable {
                                    print("PRUEBA VER DETALLES")
                                    expandir = !expandir
                                }
                            )
                        }

                        if (expandir) {

                            Text(
                                "• ${incidenciaAMostrar.tipo_incidencia}",
                                Modifier
                                    .padding(2.dp),
                                fontWeight = FontWeight.SemiBold,
                                style = MaterialTheme.typography.bodyLarge
                            )

                            when (incidenciaAMostrar.estado) {
                                "pendiente" ->
                                    IncidenciaPendiente(
                                        incidenciaAMostrar
                                    ) { nuevoEstado, motivoDenenegacion, contraer ->
                                        controller.actualizarEstadoIncidencia(
                                            incidenciaAMostrar.id,
                                            nuevoEstado,
                                            motivoDenenegacion
                                        )

                                        expandir = contraer
                                    }

                                "denegada" ->
                                    Text(
                                        "Motivo: ${incidenciaAMostrar.motivo_denegacion}",
                                        Modifier
                                            .padding(2.dp),
                                        fontWeight = FontWeight.SemiBold,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = Color(0xFFB71C1C)
                                    )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun IncidenciaPendiente(
    incidencia: Incidencia,
    onEstadoCambiado: (String, String, Boolean) -> Unit
) {
// La lamba que recibe, devolverá:
    // Un string, que será el nuevo estado al que se cambiará la incidencia cuando sea antendida
    // Un motivo de denegación si es el caso
    // Un boolean para contraer la pestaña donde hemos estado tratando el estado de la incidencia
    var estadoSeleccionado by remember { mutableStateOf(incidencia.estado) }
    var motivoDenegacion by remember { mutableStateOf("") }

    Column {

        Text(
            "Responder incidencia",
            Modifier.padding(2.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = (estadoSeleccionado == "aceptada"),
                    onClick = { estadoSeleccionado = "aceptada" },
                    colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF388E3C))
                )
                Text("Aceptar")
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = (estadoSeleccionado == "denegada"),
                    onClick = { estadoSeleccionado = "denegada" },
                    colors = RadioButtonDefaults.colors(selectedColor = Color(0xFFB71C1C))
                )
                Text("Denegar")
            }
        }

        if (estadoSeleccionado == "aceptada") {
            motivoDenegacion = "-"
        } else if (estadoSeleccionado == "denegada") {

            Text(
                "Motivo de denegación: ",
                Modifier.padding(2.dp)
            )
            OutlinedTextField(
                value = motivoDenegacion,
                onValueChange = { motivoDenegacion = it },
                label = { Text("Escribir...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
                    .height(54.dp),
                singleLine = true,
                shape = RoundedCornerShape(10.dp)
            )
            Spacer(Modifier.size(2.dp))
        }

        Button(
            onClick = {
                print("BOTON CONFIRMAR CAMBIOS ---- ESTADO SELECCIONADO $estadoSeleccionado  --- MOTIVO DENEGACION ${incidencia.motivo_denegacion}")
                onEstadoCambiado(estadoSeleccionado, motivoDenegacion, false)
                Log.d("DEPURACION", "1. Clic en Confirmar detectado")
            },

//            modifier = Modifier.align(Alignment.CenterHorizontally),
//            enabled = estadoSeleccionado != "pendiente"
        ) {
            Text("Confirmar")
        }

    }
}

@Composable
fun FormacionCardsAccesos(accesos: List<ModeloHistorial_se_eliminara>) {

    // Una variable para mostrar en ModalBottomSheet, AlertDialog o DropDown las opciones de exportar y filtrar
    var verOpcionesExportado by remember { mutableStateOf(false) }
    var verOpcionesFiltrado by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize()
    ) {
        // Fila con los botones de filtro y para exportar
        Row(Modifier.padding(bottom = 20.dp)) {
            Button(
                onClick = { verOpcionesFiltrado = true },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF455A64)),
                modifier = Modifier
                    .weight(1f) // Hace referencia a unidades de peso, tal como flex-grow en CSS
                    .padding(end = 8.dp)
            ) { Text(stringResource(id = R.string.Filtrar)) }
            Button(
                onClick = { verOpcionesExportado = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) { Text(stringResource(id = R.string.Exportar)) }
        }

        // Llamo las funciones para mostrar las opciones de exportado/filtrado tras darle al boton
        if (verOpcionesExportado) {
            OpcionesExportado({ verOpcionesExportado = false })
        }

        if (verOpcionesFiltrado) {
            OpcionesFiltrado({ verOpcionesFiltrado = false })
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            items(items = accesos) { accesoAMostrar ->
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
                                R.drawable.outline_badge_24
                            ), "Estado de Acceso",
                            tint = Color.Unspecified, // Para que respete los colores asignados a los iconos de drawable
                            modifier = Modifier
                                .padding(
                                    horizontal = 20.dp,
                                    vertical = 2.dp
                                )
                        )

                        Column {
                            Text(
                                "Acceso a Sala 001",
//                                accesoAMostrar.evento,
                                modifier = Modifier
                                    .padding(2.dp),
//                                style = MaterialTheme.typography.titleLarge,
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                "Jennyfer Dayanna Triana",
//                                    accesoAMostrar.fecha, // ---------- Tarjeta id, que lamara al usuario correspondiente para mostrar su nombre
                                modifier = Modifier
                                    .padding(2.dp),
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold
                            )

                            Row {
                                Text(
                                    "2025-11-13",
//                                    accesoAMostrar.sala,
                                    modifier = Modifier
                                        .padding(2.dp)
                                )
                                Text(
                                    "2025-11-13",
//                                    accesoAMostrar.usuario,
                                    modifier = Modifier
                                        .padding(2.dp)

                                )
                            }
                            Text(
                                "Duración 01:30:00",
//                                    accesoAMostrar.hora,
                                modifier = Modifier
                                    .padding(2.dp),
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
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
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

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpcionesFiltrado(onDismiss: () -> Unit) {

    // Me copio la lista de Uusarios de la DataClass de Usuarios creada por Asier
    // TODO. Unificar todo este tipo de cosas de modo que no repitamos código (DE LA API)
    val usuarios = listOf(
        ModeloUsuarios_se_eliminara(
            "img1",
            "Juan",
            "Pérez",
            "López",
            "1º DAM",
            "juan@mail.com",
            "600111222",
            "12/09/23",
            true,
            false,
            mutableListOf("A1")
        ),
        ModeloUsuarios_se_eliminara(
            "img2",
            "María",
            "García",
            "Ruiz",
            "2º DAW",
            "maria@mail.com",
            "600222333",
            "13/09/23",
            true,
            false,
            mutableListOf("B1", "B2")
        ),
        ModeloUsuarios_se_eliminara(
            "img3",
            "Carlos",
            "Sánchez",
            "Gil",
            "1º ASIR",
            "carlos@mail.com",
            "600333444",
            "14/09/23",
            false,
            true,
            mutableListOf("C1")
        ),
        ModeloUsuarios_se_eliminara(
            "img4",
            "Laura",
            "Martín",
            "Díaz",
            "2º DAM",
            "laura@mail.com",
            "600444555",
            "15/09/23",
            true,
            false,
            mutableListOf("D1")
        ),
        ModeloUsuarios_se_eliminara(
            "img5",
            "Pedro",
            "Ruiz",
            "Sanz",
            "1º DAW",
            "pedro@mail.com",
            "600555666",
            "16/09/23",
            true,
            false,
            mutableListOf("")
        ),
        ModeloUsuarios_se_eliminara(
            "img6",
            "Sofía",
            "López",
            "Mora",
            "2º ASIR",
            "sofia@mail.com",
            "600666777",
            "17/09/23",
            true,
            false,
            mutableListOf("F1", "F2")
        ),
        ModeloUsuarios_se_eliminara(
            "img7",
            "Javier",
            "Gómez",
            "Cano",
            "1º DAM",
            "javier@mail.com",
            "600777888",
            "18/09/23",
            false,
            false,
            mutableListOf("G1")
        ),
        ModeloUsuarios_se_eliminara(
            "img8",
            "Elena",
            "Torres",
            "Vila",
            "2º DAW",
            "elena@mail.com",
            "600888999",
            "19/09/23",
            true,
            false,
            mutableListOf("H1")
        ),
        ModeloUsuarios_se_eliminara(
            "img9",
            "Diego",
            "Díaz",
            "Pola",
            "1º ASIR",
            "diego@mail.com",
            "600999000",
            "20/09/23",
            true,
            true,
            mutableListOf("I1")
        ),
        ModeloUsuarios_se_eliminara(
            "img10",
            "Ana",
            "Vargas",
            "Ríos",
            "2º DAM",
            "ana@mail.com",
            "600000111",
            "21/09/23",
            true,
            false,
            mutableListOf("J1")
        )
    )

    val salas = listOf(
        "Sala 1",
        "Sala 2",
        "Sala 3",
        "Sala 4",
        "Sala 5",
        "Sala 7",
        "Sala 8",
        "Sala 9",
        "Sala 10",
    )

    val tipoEventos = listOf(
        stringResource(id = R.string.Acceso_denegado),
        stringResource(id = R.string.Apertura_forzada),
        stringResource(id = R.string.Puerta_abierta),
        stringResource(id = R.string.Demasiado_tiempo),
        stringResource(id = R.string.Bloqueo_sala)
    )

    val filtros = listOf(
        stringResource(id = R.string.Usuarios),
        stringResource(id = R.string.salas),
        stringResource(id = R.string.Tipo_evento),
        stringResource(id = R.string.Fecha)
    )

    var mostrarUsuarios by remember { mutableStateOf(false) }
    var mostrarSalas by remember { mutableStateOf(false) }
    var mostrarEventos by remember { mutableStateOf(false) }
    var seleccionarFecha by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = { onDismiss() },    // Si pulsa fuera del dialog
        properties = DialogProperties(usePlatformDefaultWidth = false)      // para que el fondo oscurecido no sea tan brusco
    ) {

        // El dialog centra automáticamente la carta

        Card(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .heightIn(max = 500.dp),
//                .fillMaxHeight(0.4f),

            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)

        ) {

            Text(
                stringResource(id = R.string.Suprimir_filtros),
                modifier = Modifier
                    .align(Alignment.End)
//                        .clickable()
                    .padding(end = 25.dp, top = 12.dp),
                fontStyle = FontStyle.Italic,
                color = Color.Blue
            )  // Buscar forma de acomodar el "eliminar filtros"

            Column(
                modifier = Modifier
                    .wrapContentHeight() ////Para que el tamaño de la card se adapte al contenido
                    .padding(25.dp),
            )

            {

                Text(
                    text = stringResource(id = R.string.Opciones_filtrado),
                    style = typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.padding(7.dp))

                filtros.forEach { item ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clickable(
                                enabled = true,
                                onClickLabel = "Mostrar",
                                role = Role.Button,
                                onClick = {
                                    when (item) {
                                        "Usuarios", "Users" -> mostrarUsuarios = true
                                        "Salas", "Rooms" -> mostrarSalas = true
                                        "Tipo de evento", "Type of event" -> mostrarEventos = true
                                        "Fecha", "Date" -> seleccionarFecha = true
                                    }
                                }
                            )
                            .padding(5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            item,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        IconButton({
                            when (item) {
                                "Usuarios", "Users" -> mostrarUsuarios = true
                                "Salas", "Rooms" -> mostrarSalas = true
                                "Tipo de evento", "Type of event" -> mostrarEventos = true
                                "Fecha", "Date" -> seleccionarFecha = true
                            }
                        }
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = "Seleccioinar"
                            )
                        } // Reemplazar por R.drawable
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                }

                Spacer(Modifier.padding(15.dp))

                Button(
                    { onDismiss },
                    Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Mostrar resultados")
                }
            }
        }

        var usuariosFiltro: List<String>
        var salasFiltro: List<String>
        var eventosFiltro: List<String>
        var fechaFiltro: List<String>

        when {
            mostrarUsuarios -> FiltrarPorUsuarios(usuarios, { mostrarUsuarios = false })
            mostrarSalas -> FiltrarPorSalas(salas, { mostrarSalas = false })
            mostrarEventos -> FiltrarPorEvento(tipoEventos, { mostrarEventos = false })
            seleccionarFecha -> FiltrarPorFecha({ seleccionarFecha = false })
        }
    }

//    IconButton(onClick = {
//        val newNote = Note(MyApp.totalNotes,title, body)
//        onNewNote(newNote)
//        MyApp.totalNotes++
//        print(newNote.toString())
//        confirmation()
//    }) {
//        Icon(
//            imageVector = Icons.Default.Check,
//            contentDescription = "GuardarNota",
//            modifier = Modifier
//                . background(color = Color.White, shape = RectangleShape)
//        )
//    ModalBottomSheet(
//        onDismissRequest =
//            { onDismiss() }
//    ) {
//        val scroll = rememberScrollState()
//
//        Column(Modifier.padding(20.dp)) {
//
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
fun FiltrarPorUsuarios(usuarios: List<ModeloUsuarios_se_eliminara>, onDismiss: () -> Unit) {

    var usuarioIntroducido by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = { onDismiss() },    // Si pulsa fuera del dialog
        properties = DialogProperties(usePlatformDefaultWidth = false)      // para que el fondo oscurecido no sea tan brusco
    ) {

        Card(
            modifier = Modifier
//                .fillMaxSize(),
                .fillMaxWidth(0.8f)
                .heightIn(max = 800.dp)
                .fillMaxHeight(0.8f),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {

            Text(
                stringResource(id = R.string.Cancelar),
                modifier = Modifier
                    .align(Alignment.End)
//                        .clickable()
                    .padding(end = 25.dp, top = 12.dp),
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.primary
            )

            Column(
                Modifier
                    .wrapContentHeight() //Para que el tamaño de la card se adapte al contenido
                    .padding(25.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            )
            {

                Text(
                    stringResource(id = R.string.Usuarios),
                    style = typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Buscador(usuarioIntroducido)

                val seleccionados = remember { mutableStateMapOf<String, Boolean>() }

                LazyColumn(
                    modifier = Modifier
                        .weight(1f, fill = false),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                )
                {
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

                                Text(
                                    text = usuarioActual.nombreCompleto,
                                    Modifier
                                        .weight(1f)
                                )

                                Checkbox(
                                    seleccionados[usuarioActual.fotoPerfil] ?: false,
                                    { seleccionados[usuarioActual.fotoPerfil] = it },
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.padding(8.dp))

                Button(
                    {
                        val usuariosSeleccionados =
                            usuarios.filter { seleccionados[it.fotoPerfil] == true }
                        println(usuariosSeleccionados) // Tendré que declararlo dewsde fuera, porque tan pronto sales del método (del Dialog) se pierde la seleccion.
                        // La idea es que se conserve la selección y se borre con un (suprimir filtros)
                        onDismiss()
                    },
                    Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Aplicar")
                }

            }
        }
    }
}

@Composable
fun FiltrarPorSalas(salas: List<String>, onDismiss: () -> Unit, controller: ControlAccesoViewModel = viewModel()) {
    val getDatos = controller.publicModelo.collectAsState()
    val salas = getDatos.value.salas

    var salaIntroducida by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = { onDismiss() },    // Si pulsa fuera del dialog
        properties = DialogProperties(usePlatformDefaultWidth = false)      // para que el fondo oscurecido no sea tan brusco
    ) {

        Card(
            modifier = Modifier
//                .fillMaxSize(),
                .fillMaxWidth(0.8f)
                .heightIn(max = 800.dp)
                .fillMaxHeight(0.8f),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {

            Text(
                stringResource(id = R.string.Cancelar),
                modifier = Modifier
                    .align(Alignment.End)
//                        .clickable()
                    .padding(end = 25.dp, top = 12.dp),
                fontStyle = FontStyle.Italic,
                color = Color.Blue
            )

            Column(
                Modifier
                    .wrapContentHeight() //Para que el tamaño de la card se adapte al contenido
                    .padding(25.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            )
            {

                Text(
                    stringResource(id = R.string.salas),
                    style = typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Buscador(salaIntroducida)

                val seleccionados = remember { mutableStateMapOf<String, Boolean>() }

                LazyColumn(
                    modifier = Modifier
                        .weight(1f, fill = false),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                )
                {
                    // Recorre la lista de usuarios (Instancias ModeloUsuario)
                    items(salas) { salaActual ->
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

                                    painter = painterResource(R.drawable.sala),
                                    contentDescription = "Foto salas",
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier
                                        .size(60.dp)

                                )

                                Text(
                                    text = "ID: ${salaActual.id}, ${salaActual.nombre}",
                                    Modifier
                                        .weight(1f)
                                        .padding(start = 5.dp)
                                )


                                Checkbox(
                                    seleccionados[salaActual.nombre] ?: false,
                                    { seleccionados[salaActual.nombre] = it },
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.padding(8.dp))

                Button(
                    {
                        val salasSeleccionadas = salas.filter { seleccionados[it.nombre] == true }
                        println(salasSeleccionadas)
                        onDismiss()
                    },
                    Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(stringResource(id = R.string.Aplicar))
                }
//                println(salas.filter { seleccionados[it] == true }) // Prueba--------------------------
            }
        }
    }
}

@Composable
fun FiltrarPorEvento(eventos: List<String>, onDismiss: () -> Unit) {


    Dialog(
        onDismissRequest = { onDismiss() },    // Si pulsa fuera del dialog
        properties = DialogProperties(usePlatformDefaultWidth = false)      // para que el fondo oscurecido no sea tan brusco
    ) {

        Card(
            modifier = Modifier
//                .fillMaxSize(),
                .fillMaxWidth(0.8f)
                .heightIn(max = 700.dp),
//                .fillMaxHeight(0.8f),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {

            Text(
                stringResource(id = R.string.Cancelar),
                modifier = Modifier
                    .align(Alignment.End)
//                        .clickable()
                    .padding(end = 25.dp, top = 12.dp),
                fontStyle = FontStyle.Italic,
                color = Color.Blue
            )

            Column(
                Modifier
                    .wrapContentHeight() //Para que el tamaño de la card se adapte al contenido
                    .padding(25.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            )

            {
                Text(
                    stringResource(id = R.string.Eventos),
                    style = typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.padding(7.dp))

                val seleccionados = remember { mutableStateMapOf<String, Boolean>() }

                eventos.forEach { eventoActual ->
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

                            Icon(
                                painterResource(
                                    when (eventoActual) {
                                        "Acceso Denegado", "Access denied" -> R.drawable.outline_do_not_disturb_on_24
                                        "Apertura Forzada", "Forced opening" -> R.drawable.outline_report_24
                                        "Puerta Abierta", "Open door" -> R.drawable.outline_lock_open_right_24
                                        "Demasiado Tiempo", "Enough time" -> R.drawable.outline_hourglass_empty_24
                                        "Bloqueo de Sala", "Room Lockout" -> R.drawable.outline_lock_person_24
                                        else -> {
                                            R.drawable.outline_unknown_med_24
                                        }
                                    }
                                ), "Estado de Acceso",
                                tint = Color.Unspecified, // Para que respete los colores asignados a los iconos de drawable
                                modifier = Modifier
                                    .padding(
                                        horizontal = 20.dp,
                                        vertical = 2.dp
                                    )
                            )

                            Text(
                                text = eventoActual,
                                Modifier
                                    .weight(1f)
                                    .padding(start = 5.dp)
                            )

                            Checkbox(
                                seleccionados[eventoActual] ?: false,
                                { seleccionados[eventoActual] = it },
                            )
                        }
                    }
                }


                Spacer(Modifier.padding(8.dp))

                Button(
                    {
                        val eventosSeleccionados = eventos.filter { seleccionados[it] == true }
                        println(eventosSeleccionados)
                        onDismiss()
                    },
                    Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Aplicar")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// Ejemplo copiado exactamente del ej de la documentacion de Material3
fun FiltrarPorFecha(onDismiss: () -> Unit) {

    //TODOo Adaptarlo al ejercicio, que se vea en un cuadro de dialogo (DONE)
    // Tiene que ser en pantalla completa ya que colapsa si se  cambia su tamaño
    // Mover la "x" a la derecha e intentar poner un boton como en las demas opciones de filtrado
    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()
    SnackbarHost(hostState = snackState, Modifier.zIndex(1f))

    val state = rememberDateRangePickerState()
//    DateRangePicker()

    Dialog(
        onDismissRequest = { onDismiss() },    // Si pulsa fuera del dialog
        properties = DialogProperties(usePlatformDefaultWidth = false)      // para que el fondo oscurecido no sea tan brusco
    ) {

        Card(
            modifier = Modifier
                .fillMaxSize(),
//                .fillMaxWidth(0.95f),
//                .heightIn(max = 800.dp),
//                .fillMaxHeight(0.4f),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {

            Text(
                stringResource(id = R.string.Cancelar),
                modifier = Modifier
                    .align(Alignment.End)
//                        .clickable()
                    .padding(end = 25.dp, top = 12.dp),
                fontStyle = FontStyle.Italic,
                color = Color.Blue
            )
            Column(
                modifier = Modifier
                    .wrapContentHeight() ////Para que el tamaño de la card se adapte al contenido
                    .padding(25.dp)
            ) {
                // Add a row with "Save" and dismiss actions.

                Text(
                    stringResource(id = R.string.Seleccionar_fecha),
                    style = typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.padding(7.dp))


//                Row(
//                    modifier =
//                        Modifier
//                            .fillMaxWidth()
//                            .background(DatePickerDefaults.colors().containerColor)
//                            .padding(),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    IconButton(onClick = { onDismiss() /* dismiss the UI */ }) {
//                        Icon(Icons.Filled.Close, contentDescription = "Localized description")
//                    }
//                    TextButton(
//                        onClick = {
//                            snackScope.launch {
//                                val range =
//                                    state.selectedStartDateMillis!!..state.selectedEndDateMillis!!
//                                snackState.showSnackbar("Saved range (timestamps): $range")
//                            }
//                        },
//                        enabled = state.selectedEndDateMillis != null
//                    ) {
//                        Text(text = "Save")
//                    }
//                }
                DateRangePicker(
                    state = state,
                    modifier = Modifier
                        .weight(1f)
                        .padding(1.dp),

                    title = {
                        Text(
                            "Selecciona fechas",
                            modifier = Modifier
                                .padding(start = 24.dp, top = 16.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    },
                    headline = {
                        DateRangePickerHeadline(
                            selectedStartDateMillis = state.selectedStartDateMillis,
                            selectedEndDateMillis = state.selectedEndDateMillis,
                            displayMode = state.displayMode,
                            dateFormatter = remember { DatePickerDefaults.dateFormatter() },
                            modifier = Modifier.padding(start = 24.dp)
                            // Buscar la forma de sobreescribir los textos de fecha iicial y final
//                        startDateText = "Fecha inicio",
//                        endDateText = "Fecha fin",
//                        startDatePlaceholder = { Text("Inicio") },
//                        endDatePlaceholder = { Text("Fin") },
//                        datesDelimiter = { Text(" - ") }

                        )
                    }
                )

                Spacer(Modifier.padding(8.dp))

                Button(
                    {
                        onDismiss()
                    },
                    Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Aplicar")
                }


            }
        }
    }
}

@Composable
fun Buscador(textoIntruducido: String) {

    var textoIntruducido by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 1. EL BUSCADOR
        OutlinedTextField(
            value = textoIntruducido,
            onValueChange = {
                textoIntruducido = it
            }, // Esto actualiza el estado correctamente
            modifier = Modifier
                .weight(1f)
                .height(50.dp), // Mantenemos la altura compacta
            placeholder = {
                Text(
                    text = stringResource(id = R.string.Buscar) + " ...",
                    style = typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            singleLine = true,
            textStyle = typography.bodyMedium, // IMPORTANTE: Texto un poco más pequeño para que quepa bien
            shape = RoundedCornerShape(10.dp),

            // --- HE BORRADO LA LÍNEA contentPadding QUE DABA ERROR ---

            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.Buscar) + " ...",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
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
                text = stringResource(id = R.string.Buscar),
                style = typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
    // ---------------------------- Fin del buscador
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewNotificaciones() {
    Notificaciones()
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewFormacionCardsAcceso() {
    FormacionCardsAccesos(listOf())
}

@Preview(showBackground = true)
@Composable
fun PreviewOpcionesExportado() {
    OpcionesExportado({})
}

@Preview(showBackground = true)
@Composable
fun PreviewOpcionesFiltrado() {
    OpcionesFiltrado({})
}

@Preview(showBackground = true)
@Composable
fun PreviewFiltrarPorUsuarios() {
    FiltrarPorUsuarios(listOf(), {})
}

@Preview(showBackground = true)
@Composable
fun PreviewFiltrarPorSalas() {
    FiltrarPorSalas(listOf(), {})
}

@Preview(showBackground = true)
@Composable
fun PreviewFiltrarPorEventos() {
    FiltrarPorEvento(listOf(), {})
}

@Preview(showBackground = true)
@Composable
fun PreviewFiltrarPorFecha() {
    FiltrarPorFecha({})
}
