package com.example.controldeaccesokotlin.Vistas

import android.annotation.SuppressLint
import android.os.Build
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import com.example.controldeaccesokotlin.bd_api.ModeloHistorial_se_eliminara
import com.example.controldeaccesokotlin.ModeloUsuarios_se_eliminara
import com.example.controldeaccesokotlin.R
import kotlin.collections.mutableListOf
import kotlin.collections.set


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Notificaciones() {

    // Una variable para mostrar en ModalBottomSheet, AlertDialog o DropDown las opciones de exportar y filtrar
    var verOpcionesExportado by remember { mutableStateOf(false) }
    var verOpcionesFiltrado by remember { mutableStateOf(false) }

    // En una lista almaceno la información necesaria para cada registro
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
fun FormacionCardsRegistros(eventos: List<ModeloHistorial_se_eliminara>) {

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
                            when (eventoAMostrar.evento) {
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
                        tint = Color.Unspecified, // Para que respete los colores asignados a los iconos de drawable
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

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpcionesFiltrado(onDismiss: () -> Unit) {

    // Me copio la lista de Uusarios de la DataClass de Usuarios creada por Asier
    // TODO. Unificar todo este tipo de cosas de modo que no repitamos código
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
        ModeloUsuarios_se_eliminara("img2", "María", "García", "Ruiz", "2º DAW", "maria@mail.com", "600222333", "13/09/23", true, false, mutableListOf("B1", "B2")),
        ModeloUsuarios_se_eliminara("img3", "Carlos", "Sánchez", "Gil", "1º ASIR", "carlos@mail.com", "600333444", "14/09/23", false, true, mutableListOf("C1")),
        ModeloUsuarios_se_eliminara("img4", "Laura", "Martín", "Díaz", "2º DAM", "laura@mail.com", "600444555", "15/09/23", true, false, mutableListOf("D1")),
        ModeloUsuarios_se_eliminara("img5", "Pedro", "Ruiz", "Sanz", "1º DAW", "pedro@mail.com", "600555666", "16/09/23", true, false, mutableListOf("")),
        ModeloUsuarios_se_eliminara("img6", "Sofía", "López", "Mora", "2º ASIR", "sofia@mail.com", "600666777", "17/09/23", true, false, mutableListOf("F1", "F2")),
        ModeloUsuarios_se_eliminara("img7", "Javier", "Gómez", "Cano", "1º DAM", "javier@mail.com", "600777888", "18/09/23", false, false, mutableListOf("G1")),
        ModeloUsuarios_se_eliminara("img8", "Elena", "Torres", "Vila", "2º DAW", "elena@mail.com", "600888999", "19/09/23", true, false, mutableListOf("H1")),
        ModeloUsuarios_se_eliminara("img9", "Diego", "Díaz", "Pola", "1º ASIR", "diego@mail.com", "600999000", "20/09/23", true, true, mutableListOf("I1")),
        ModeloUsuarios_se_eliminara("img10", "Ana", "Vargas", "Ríos", "2º DAM", "ana@mail.com", "600000111", "21/09/23", true, false, mutableListOf("J1"))
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
//        "Todos",
        "Acceso Denegado",
        "Apertura Forzada",
        "Puerta Abierta",
        "Demasiado Tiempo",
        "Bloqueo de Sala"
    )

    val filtros = listOf(
        "Usuarios",
        "Salas",
        "Tipo de evento",
        "Fecha"
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
            colors = CardDefaults.cardColors(containerColor = Color.White)

        ) {

            Text(
                "Suprimir filtros",
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
                    text = "Opciones de filtrado",
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
                                        "Usuarios" -> mostrarUsuarios = true
                                        "Salas" -> mostrarSalas = true
                                        "Tipo de evento" -> mostrarEventos = true
                                        "Fecha" -> seleccionarFecha = true
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
                                "Usuarios" -> mostrarUsuarios = true
                                "Salas" -> mostrarSalas = true
                                "Tipo de evento" -> mostrarEventos = true
                                "Fecha" -> seleccionarFecha = true
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
                        color = Color.Gray
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

        var usuariosFiltro : List<String>
        var salasFiltro : List<String>
        var eventosFiltro : List<String>
        var fechaFiltro : List<String>

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
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {

            Text(
                "Cancelar",
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
                    "Usuarios",
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
fun FiltrarPorSalas(salas: List<String>, onDismiss: () -> Unit) {

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
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {

            Text(
                "Cancelar",
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
                    "Salas",
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
                                    text = salaActual,
                                    Modifier
                                        .weight(1f)
                                        .padding(start = 5.dp)
                                )

                                Checkbox(
                                    seleccionados[salaActual] ?: false,
                                    { seleccionados[salaActual] = it },
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.padding(8.dp))

                Button(
                    {
                        val salasSeleccionadas = salas.filter { seleccionados[it] == true }
                        println(salasSeleccionadas)
                        onDismiss()
                    },
                    Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Aplicar")
                }
//                println(salas.filter { seleccionados[it] == true }) // Prueba--------------------------
            }
        }
    }
}

@Composable
fun FiltrarPorEvento(eventos: List<String>, onDismiss: () -> Unit) {

    var expandir by remember { mutableStateOf(false) } // Para usar el DropDown
    var seleccion by remember { mutableStateOf(eventos[0]) } // Para recordar selección, en principio mostrará la opción "Todos"


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
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {

            Text(
                "Cancelar",
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
                    "Eventos",
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

    //TODO Adaptarlo al ejercicio, que se vea en un cuadro de dialogo (DONE)
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
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {

            Text(
                "Cancelar",
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
                    "Seleccionar fecha",
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