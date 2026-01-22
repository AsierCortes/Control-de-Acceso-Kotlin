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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.controldeaccesokotlin.ModeloUsuarios_se_eliminara
import com.example.controldeaccesokotlin.R
import com.example.controldeaccesokotlin.ui.theme.ControlDeAccesoKotlinTheme

@Composable
fun Salas() {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Salas",
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
                    text = "Buscar sala...", style = typography.bodyMedium, color = Color.Gray
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
                text = "Buscar", style = typography.labelLarge, fontWeight = FontWeight.Bold
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

    var tabSeleccionado by remember { mutableStateOf(0) }


    var selectedDestination by rememberSaveable { mutableStateOf("todas") }

    PrimaryTabRow(selectedTabIndex = tabSeleccionado) {
        // TODAS
        Tab(todas, {
            navController.navigate("todas")
            todas = true
            libres = false
            ocupadas = false

            tabSeleccionado = 0
        }) {
            Text(
                text = "Todas",
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

            tabSeleccionado = 1
        }) {
            Text(
                text = "Libres",
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

            tabSeleccionado = 2
        }) {
            Text(
                text = "Ocupadas",
                style = typography.titleMedium,
                modifier = Modifier.padding(bottom = 6.dp)
            )
        }
    }

    NavHost(navController, startDestination = "todas") {
        composable("todas") { Todas() }
        composable("libres") { Libres() }
        composable("ocupadas") { Ocupadas() }


    }

}

@Composable
fun Todas() {
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
            GenerarSalas()
        }

    }
}


@Composable
fun Libres() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GenerarSalas()
    }
}

@Composable
fun Ocupadas() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GenerarSalas()
    }
}

@Composable
fun GenerarSalas() {
    val salas = listOf<String>(
        "Sala 01",
        "Sala 02",
        "Sala 03",
        "Sala 04",
        "Sala 05",
        "Sala 06",
        "Sala 07",
        "Sala 08",
        "Sala 09",
        "Sala 10",
        "Sala 11",
        "Sala 12",
        "Sala 13",
        "Sala 14",
        "Sala 15"
    )

    // Este es null al principio, si alguien pulsa una sala, guarda el nombre de la sala pulsada
    var salaSeleccionada by remember { mutableStateOf<String?>(null) }


    LazyVerticalGrid(
        // Esto va a manejar lo que ocupa la carta también
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .padding(top = 20.dp),
        columns = GridCells.Fixed(1),
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.spacedBy(18.dp)    // Separación entre elementos
    ) {
        items(salas) { infoSalaActual ->

            // CARTA -> No sirve como contenedor de modifier
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),   // SOMBRA TARJETA
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceBright),
                border = BorderStroke(1.dp, Color.Black),
                // Hacemos que sea clickeable
                modifier = Modifier.clickable(
                    onClick = { salaSeleccionada = infoSalaActual }

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
                    Text(text = infoSalaActual, style = typography.titleMedium)
                    // Forzamos para que vaya a la izq
                    Text(text = "\uD83D\uDFE2")
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
                    Text(text = "Capacidad: 7m2")
                    Spacer(Modifier.padding(2.dp))
                    Text(text = "Ultimo acceso: Juan Arechabela")
                }
            }


        }
    }
    // El dialogo se pone fuera de la lazyGrid para que solo pinte la sala seleccionada. Ya que de lo contrario,
    // pintaria todas
    // Solo aparece el dialogo si alguien ha pusado una sala (Es decir, cuando salaSeleccionada es distinto de null)
    if (salaSeleccionada != null) {
        MostrarDialogoInformacionSala(
            textoMostrar = salaSeleccionada!!, // Pasamos la sala seleccionada
            pulsarFuera = {
                salaSeleccionada = null
            } // Al pulsar fuera del dialogo, se sale y se vuelve a null
        )
    }


}


@Composable
fun BotonFlotanteAniadirSala(pulsaBotonFlontante: () -> Unit) {
    // Pulsa y llama a la funcion crearNuevaSala()
    FloatingActionButton(
        { pulsaBotonFlontante() },
        shape = CircleShape,
        containerColor = Color(0xFFFFFFE0),
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
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
fun MostrarDialogoInformacionSala(textoMostrar: String, pulsarFuera: () -> Unit) {
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
                        text = "Información de la Sala",
                        style = typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )

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
                            text = "Nombre:",
                            style = typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier.weight(0.3f)
                        )

                        Text(
                            text = "B104",
                            style = typography.bodyLarge,
                            modifier = Modifier.weight(0.6f)
                        )

                    }

                    // Ubicación
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                    ) {
                        // Parrafo Ubicación Negro
                        Row(
                            modifier = Modifier.weight(1.2f)    //Para tener un poco de separacion
                        ) {
                            Text(
                                text = "Ubicación:",
                                textDecoration = TextDecoration.Underline,
                                fontWeight = FontWeight.Bold,
                                style = typography.bodyLarge
                            )
                        }


                        // EDIFICIO Y VALOR
                        Row(
                            modifier = Modifier.weight(1f)
                        ) {

                            // EDIFICIO
                            Text(
                                text = "Edificio:",
                                style = typography.bodyLarge,
                                modifier = Modifier.weight(0.3f)
                            )

                            // VALOR
                            Text(
                                text = "Berlín",
                                style = typography.bodyLarge,
                                modifier = Modifier.weight(0.6f)
                            )

                        }

                        // NUMERO Y VALOR
                        Row(
                            modifier = Modifier.weight(1f)
                        ) {

                            // NUMERO
                            Text(
                                text = "Número:",
                                style = typography.bodyLarge,
                                modifier = Modifier.weight(0.3f)
                            )

                            // VALOR
                            Text(
                                text = "104",
                                style = typography.bodyLarge,
                                modifier = Modifier.weight(0.6f)
                            )

                        }


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
                            text = "Estado:",
                            style = typography.bodyLarge,
                            textDecoration = TextDecoration.Underline,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(0.3f)
                        )

                        Text(
                            text = "Libre \uD83D\uDFE2",
                            style = typography.bodyLarge,
                            modifier = Modifier.weight(0.6f)
                        )

                    }

                    // Cantidad de usuarios
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Cantidad de usuarios:",
                            style = typography.bodyLarge,
                            textDecoration = TextDecoration.Underline,
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(Modifier.padding(5.dp))
                        Text(
                            text = "10",
                            style = typography.bodyLarge
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
                            text = "Usuarios en la sala:",
                            textDecoration = TextDecoration.Underline,
                            fontWeight = FontWeight.Bold,
                            style = typography.bodyLarge
                        )


                        // CREAMOS LOS USUARIO DE EJEMPLO (DATA CLASS ModeloUsuarios)
                        val listaUsuariosMutable = listOf(
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
                            ), ModeloUsuarios_se_eliminara(
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
                            ), ModeloUsuarios_se_eliminara(
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
                            ), ModeloUsuarios_se_eliminara(
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
                            ), ModeloUsuarios_se_eliminara(
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
                                mutableListOf()
                            ), ModeloUsuarios_se_eliminara(
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
                            ), ModeloUsuarios_se_eliminara(
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
                            ), ModeloUsuarios_se_eliminara(
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
                            ), ModeloUsuarios_se_eliminara(
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
                            ), ModeloUsuarios_se_eliminara(
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


                        // queda implementar aqui una grid lazy column, que muestre usuarios que haya dentro
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            // Recorre la lista de usuarios (Instancias ModeloUsuario)
                            items(listaUsuariosMutable) { usuarioActual ->
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceBright),
                                    border = BorderStroke(1.dp, Color.Black),
                                    shape = RoundedCornerShape(4.dp)

                                ) {
                                    Column (
                                        modifier = Modifier.fillMaxWidth()
                                    ){


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

                                            Text(text = usuarioActual.nombreCompleto)
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
                                                text = "Hora de acceso:",
                                                style = typography.bodyLarge,
                                                fontWeight = FontWeight.Bold
                                            )

                                            Text(
                                                text = "9:34 AM",
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

@Composable
fun MostrarInformacionSalaDetallada() {

}

@Preview(showBackground = true)
@Composable
fun PreviewSalas() {
    ControlDeAccesoKotlinTheme {
        Salas()
    }
}


@Preview(showBackground = true)
@Composable
fun PrevierMostrarDialogoInformacionSala() {
    ControlDeAccesoKotlinTheme {
        MostrarDialogoInformacionSala("Sala1") {

        }
    }
}