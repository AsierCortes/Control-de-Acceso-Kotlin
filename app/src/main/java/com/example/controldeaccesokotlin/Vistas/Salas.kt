package com.example.controldeaccesokotlin.Vistas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.controldeaccesokotlin.R
import com.example.controldeaccesokotlin.bd_api.Sala
import com.example.controldeaccesokotlin.ui.theme.ControlDeAccesoKotlinTheme
import com.example.controldeaccesokotlin.viewModel.ControlAccesoViewModel
import kotlin.math.roundToInt

@Composable
fun Salas(controller: ControlAccesoViewModel = viewModel()) {
    val getDatos = controller.publicModelo.collectAsState()


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = stringResource(id = R.string.salas),
            style = typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .align(Alignment.CenterHorizontally)
                .padding(top = 4.dp)
        )

        Spacer(Modifier.padding(2.dp))

        BuscadorSencillo()

        Spacer(Modifier.padding(6.dp))



        SelectorListaSalas()


    }
}


@Composable
fun BuscadorSencillo() {
    var textoIntroducido by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth(0.9f)
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
                    text =stringResource(id = R.string.Buscar_sala) , style = typography.bodyMedium, color = Color.Gray
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
            modifier = Modifier.height(50.dp) // Misma altura que el input
        ) {
            Text(
                text = stringResource(id = R.string.Buscar), style = typography.labelLarge, fontWeight = FontWeight.Bold
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorListaSalas(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    var todas = true
    var libres = false
    var ocupadas = false
    var bloqueadas = false

    var tabSeleccionado by remember { mutableIntStateOf(0) }


    var selectedDestination by rememberSaveable { mutableStateOf("todas") }

    PrimaryTabRow(
        selectedTabIndex = tabSeleccionado,

    ) {
        // TODAS
        Tab(todas, {
            navController.navigate("todas")
            todas = true
            libres = false
            ocupadas = false
            bloqueadas = false

            tabSeleccionado = 0
        }) {
            Text(
                text = stringResource(id = R.string.Todas),
                style = typography.titleMedium,
                modifier = Modifier.padding(bottom = 6.dp)
            )
        }

        // LIBRE
        Tab(libres, {
            navController.navigate("libres")
            libres = true
            todas = false
            ocupadas = false
            bloqueadas = false


            tabSeleccionado = 1
        }) {
            Text(
                text = stringResource(id = R.string.Libres),
                style = typography.titleMedium,
                modifier = Modifier.padding(bottom = 6.dp)
            )
        }

        // OCUPADAS
        Tab(ocupadas, {
            navController.navigate("ocupadas")
            ocupadas = true
            todas = false
            libres = false
            bloqueadas = false

            tabSeleccionado = 2
        }) {
            Text(
                text = stringResource(id = R.string.Ocupadas),
                style = typography.titleMedium,
                modifier = Modifier.padding(bottom = 6.dp)
            )
        }

        // BLOQUEADAS
        Tab(ocupadas, {
            navController.navigate("bloqueadas")
            bloqueadas = true
            ocupadas = false
            todas = false
            libres = false

            tabSeleccionado = 3
        }) {
            Text(
                text = stringResource(id = R.string.Bloqueadas),
                style = typography.titleMedium,
                modifier = Modifier.padding(bottom = 6.dp)
            )
        }
    }

    NavHost(navController, startDestination = "todas") {
        composable("todas") { Todas() }
        composable("libres") { Libres() }
        composable("ocupadas") { Ocupadas() }
        composable("bloqueadas") { Bloqueadas() }


    }

}

@Composable
fun Todas(controller: ControlAccesoViewModel = viewModel()) {
    val publicModel = controller.publicModelo.collectAsState()
    val todasLasSalas : List <Sala> = publicModel.value.salas


    println("DESDE TODAS todasLasSalas: " + todasLasSalas + ", public model salas: " + publicModel.value.salas)

    // Se activa y se pone a true si alguien lo pulsa
    // 1. Usamos BOX para apilar capas (Lista al fondo, Botón arriba)
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        // CAPA DEL FONDO: La lista de salas
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter  // Lo alinea en el centro horizontal

        ) {
            GenerarSalas(todasLasSalas)
        }
        /*
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd) // Alineado abajo a la derecha
                .padding(25.dp)             // Margen para no pegar al borde
        ) {
            BotonFlotanteAniadirSala()
        }
        */

    }
}


@Composable
fun Libres(controller: ControlAccesoViewModel = viewModel()) {

    val publicModel = controller.publicModelo.collectAsState()
    val salasLibres : List <Sala> = publicModel.value.salasLibres
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GenerarSalas(salasLibres)
    }
}

@Composable
fun Ocupadas(controller: ControlAccesoViewModel = viewModel()) {
    val publicModel = controller.publicModelo.collectAsState()
    val salasOcupadas : List <Sala> = publicModel.value.salasOcupadas

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GenerarSalas(salasOcupadas)
    }
}

@Composable
fun Bloqueadas(controller: ControlAccesoViewModel = viewModel()) {
    val publicModel = controller.publicModelo.collectAsState()
    val salasBloqueadas : List <Sala> = publicModel.value.salasBloqueadas

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GenerarSalas(salasBloqueadas)
    }
}

@Composable
fun GenerarSalas(salasAPintar : List <Sala>, controller: ControlAccesoViewModel = viewModel()) {
    // Este es null al principio, si alguien pulsa una sala, guarda el nombre de la sala pulsada
    var idSalaSeleccionada by remember { mutableStateOf<Int?>(-1) }
    val publicModel = controller.publicModelo.collectAsState()


    LazyVerticalGrid(
        // Esto va a manejar lo que ocupa la carta también
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .padding(top = 20.dp),
        columns = GridCells.Fixed(1),
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.spacedBy(18.dp)    // Separación entre elementos
    ) {
        items(salasAPintar) { infoSalaActual ->

            // CARTA -> No sirve como contenedor de modifier
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),   // SOMBRA TARJETA
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceBright),
                border = BorderStroke(1.dp, Color.Black),
                // Hacemos que sea clickeable
                modifier = Modifier.clickable(
                    onClick = { idSalaSeleccionada = infoSalaActual.id }

                )) {
                // La columna ocupa el 85% de la LazyVerticalGrid
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .padding(top = 15.dp, bottom = 15.dp)
                        // FORZAMOS para que la propia columna se coloque en medio, ya que card no tiene ni vertical ni horizontal aligment
                        .align(Alignment.CenterHorizontally), horizontalAlignment = Alignment.Start
                ) {
                    // Forzamos para que vaya a la izq
                    Text(text = infoSalaActual.nombre, style = typography.titleMedium)
                    // Forzamos para que vaya a la izq

                    Text(
                        text = if (infoSalaActual.estado.equals("libre", ignoreCase = true)) {
                            "\uD83D\uDFE2"
                        } else if(infoSalaActual.estado.equals("ocupada", ignoreCase = true)){
                            "\uD83D\uDFE0"
                        }else{
                            "\uD83D\uDD34"
                        }
                    )



                    // Se alinia al centro por la columna padre
                    Image(
                        painter = painterResource(R.drawable.sala),
                        contentDescription = "Foto salas",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(200.dp)
                            .align(Alignment.CenterHorizontally)

                    )
                    Spacer(Modifier.padding(4.dp))
                    Text(text = "${stringResource(id = R.string.Capacidad)}: ${infoSalaActual.capacidad.roundToInt()} ${stringResource(id = R.string.Personas)}")
                }
            }


        }
    }
    // El dialogo se pone fuera de la lazyGrid para que solo pinte la sala seleccionada. Ya que de lo contrario,
    // pintaria todas
    // Solo aparece el dialogo si alguien ha pusado una sala (Es decir, cuando salaSeleccionada es distinto de null)
    if (idSalaSeleccionada != -1) {
        // SOlo cuando cambie el id se ejecutara
        LaunchedEffect(idSalaSeleccionada) {
            controller.recogerInfoSalaSeleccionada(idSalaSeleccionada)
            controller.getAccesosSalaEspecifica(idSalaSeleccionada)
        }
        val infoSalaSeleccionada : Sala? = publicModel.value.salaSeleccionada

        MostrarDialogoInformacionSala(
            salaMostrar = infoSalaSeleccionada, // Pasamos la sala seleccionada
            pulsarFuera = {
                idSalaSeleccionada = -1
            } // Al pulsar fuera del dialogo, se sale y se vuelve a null
        )
    }


}


@Composable
fun Desplegable(modifier: Modifier, opciones: MutableList<String>) {
    var expanded by remember { mutableStateOf(false) }
    var seleccion by remember { mutableStateOf(opciones[0]) }

    Column(modifier = modifier.border(1.dp, Color.Black)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .padding(8.dp)) {
            Text(
                text = seleccion, modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown, contentDescription = "Desplegar"
            )
        }

        // Menú desplegable
        DropdownMenu(
            expanded = expanded, onDismissRequest = { expanded = false }) {
            opciones.forEach { opcion ->
                DropdownMenuItem(text = { Text(opcion) }, onClick = {
                    seleccion = opcion
                    expanded = false
                })
            }
        }
    }
}


@Composable
fun MostrarDialogoInformacionSala(salaMostrar: Sala?, pulsarFuera: () -> Unit, controller: ControlAccesoViewModel = viewModel()) {
    val getDatos = controller.publicModelo.collectAsState()
    val listaUsuarios = getDatos.value.listaUsuariosSalaSeleccionada
    val listaHorarioEntrada = getDatos.value.listaHorasEntradasSalaSeleccionada
    var contadorHoraEntrada = -1
    Dialog(
        onDismissRequest = { pulsarFuera() },    // Si pulsa fuera del dialog
        properties = DialogProperties(usePlatformDefaultWidth = false)      // para que el fondo oscurecido no sea tan brusco
    ) {

        // El dialog centra automáticamente la carta
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)  // 90% ancho
                .fillMaxHeight(0.8f),

            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center // Esto permite alinear al cetnro
            ) {


                // La card no tiene para alinear la columna, es por ello que la aliniamos manualmente con aling
                Column(
                    modifier = Modifier.fillMaxSize(0.9f),        // Que ocupe toda la carta

                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.Info_sala),
                        style = typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )

                    // Id de la sala
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(top = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "ID:",
                            style = typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier.weight(0.3f)
                        )

                        Text(
                            text = "${salaMostrar?.id}",
                            style = typography.bodyLarge,
                            modifier = Modifier.weight(0.6f)
                        )

                    }

                    // Nombre de la sala
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(top = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${stringResource(id = R.string.Nombre)}: ",
                            style = typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier.weight(0.3f)
                        )

                        Text(
                            text = "${salaMostrar?.nombre}",
                            style = typography.bodyLarge,
                            modifier = Modifier.weight(0.6f)
                        )

                    }

                    // Ubicación
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(top = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${stringResource(id = R.string.Ubicacion)}: ",
                            style = typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier.weight(0.3f)
                        )

                        Text(
                            text = "${salaMostrar?.ubicacion}",
                            style = typography.bodyLarge,
                            modifier = Modifier.weight(0.6f)
                        )

                    }

                    // Tipo de cerradura
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(top = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${stringResource(id = R.string.Tipo_cerradura)}: ",
                            style = typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier.weight(0.3f)
                        )

                        Text(
                            text = "${salaMostrar?.tipo_cerradura}",
                            style = typography.bodyLarge,
                            modifier = Modifier.weight(0.6f)
                        )

                    }


                    // Capacidad
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(top = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${stringResource(id = R.string.Capacidad)}: ",
                            style = typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier.weight(0.3f)
                        )

                        Text(
                            text = "${salaMostrar?.capacidad?.roundToInt()}",
                            style = typography.bodyLarge,
                            modifier = Modifier.weight(0.6f)
                        )

                    }


                    // Estado
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${stringResource(id = R.string.Estado)}: ",
                            style = typography.bodyLarge,
                            textDecoration = TextDecoration.Underline,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(0.3f)
                        )

                        Text(
                            text = if (salaMostrar?.estado.equals("libre", ignoreCase = true)) {
                                "${stringResource(id = R.string.Libres)} \uD83D\uDFE2"
                            } else if(salaMostrar?.estado.equals("ocupada", ignoreCase = true)){
                                "${stringResource(id = R.string.Ocupadas)} \uD83D\uDFE0"
                            }else{
                                "${stringResource(id = R.string.Bloqueadas)} \uD83D\uDD34"
                            },
                            modifier = Modifier.weight(0.6f)

                        )

                    }

                    // Usuarios
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(5f)
                            .padding(bottom = 10.dp),

                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "${stringResource(id = R.string.Usuarios_en_sala)}:",
                            textDecoration = TextDecoration.Underline,
                            fontWeight = FontWeight.Bold,
                            style = typography.bodyLarge
                        )





                        // queda implementar aqui una grid lazy column, que muestre usuarios que haya dentro
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            // Recorre la lista de usuarios (Instancias ModeloUsuario)
                            items(listaUsuarios) { usuarioActual ->

                                contadorHoraEntrada++
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceBright),
                                    border = BorderStroke(1.dp, Color.Black),
                                    shape = RoundedCornerShape(4.dp)

                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {


                                        // IMG Y NOMBRE
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
                                            Column(
                                                Modifier.padding(start = 2.dp),
                                                verticalArrangement = Arrangement.spacedBy(2.dp)
                                            ) {
                                                Text("Id: ${usuarioActual.id}")
                                                Text(usuarioActual.nombre, fontWeight = FontWeight.Bold)
                                                Text(usuarioActual.email)
                                                Text("Rol: ${usuarioActual.rol_id}")
                                            }


                                        }

                                        // HORA ACCESO
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(start = 10.dp, bottom = 5.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                                        ) {
                                            Text(
                                                text = "${stringResource(id = R.string.Hora_acceso)}:",
                                                style = typography.bodyLarge,
                                                fontWeight = FontWeight.Bold
                                            )

                                            Text(
                                                text = listaHorarioEntrada[contadorHoraEntrada],
                                                style = typography.bodyLarge,
                                            )

                                        }
                                    }


                                }


                            }
                        }

                    }
                }
            }
        }
    }
}
/*
@Composable
fun BotonFlotanteAniadirSala() {
    // Pulsa y llama a la funcion crearNuevaSala()
    FloatingActionButton(
        onClick = {

        },
        shape = CircleShape,
        containerColor = Color(0xFFFFFFE0),
    ) {
        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Add")
    }
}
*/
@Preview(showBackground = true)
@Composable
fun PreviewSalas() {
    ControlDeAccesoKotlinTheme {
        Salas()
    }
}


