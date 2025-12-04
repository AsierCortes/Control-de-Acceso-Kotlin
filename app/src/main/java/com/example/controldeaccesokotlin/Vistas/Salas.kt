package com.example.controldeaccesokotlin.Vistas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.controldeaccesokotlin.R
import com.example.controldeaccesokotlin.ui.theme.ControlDeAccesoKotlinTheme

@Composable
fun Salas() {

    Column(
        modifier = Modifier
            .fillMaxSize(),
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
                    text = "Buscar sala...",
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
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(20.dp)
            )
            Text(
                text = "Añadir",
                style = typography.labelLarge,
                fontWeight = FontWeight.Bold
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
        }) { Text(text = "Todas", style = typography.titleMedium, modifier = Modifier.padding(bottom = 6.dp)) }

        // LIBRE
        Tab(libres, {
            navController.navigate("libres")
            libres = true
            todas = false
            ocupadas = false

            tabSeleccionado = 1
        }) { Text(text = "Libres", style = typography.titleMedium, modifier = Modifier.padding(bottom = 6.dp))}

        // OCUPADAS
        Tab(ocupadas, {
            navController.navigate("ocupadas")
            ocupadas = true
            todas = false
            libres = false

            tabSeleccionado = 2
        }) { Text(text = "Ocupadas", style = typography.titleMedium, modifier = Modifier.padding(bottom = 6.dp))}
    }

    NavHost(navController, startDestination = "todas") {
        composable("todas") { Todas() }
        composable("libres") { Libres() }
        composable("ocupadas") { Ocupadas() }


    }

}

@Composable
fun Todas() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        GenerarSalas()
    }
}


@Composable
fun Libres() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GenerarSalas()
    }
}

@Composable
fun Ocupadas() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GenerarSalas()
    }
}

@Composable
fun GenerarSalas() {
    val salas =
        listOf<String>("Sala 01", "Sala 02", "Sala 03", "Sala 04", "Sala 05", "Sala 06", "Sala 07", "Sala 08", "Sala 09", "Sala 10", "Sala 11", "Sala 12", "Sala 13", "Sala 14", "Sala 15")

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
                modifier = Modifier.clickable(
                    onClick = { salaSeleccionada = infoSalaActual }

                )
                ) {
                // La columna ocupa el 85% de la LazyVerticalGrid
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .padding(top = 15.dp, bottom = 15.dp)
                        // FORZAMOS para que la propia columna se coloque en medio, ya que card no tiene ni vertical ni horizontal aligment
                        .align(Alignment.CenterHorizontally),
                        horizontalAlignment = Alignment.Start
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
        mostrarDialogo(
            textoMostrar = salaSeleccionada!!, // Pasamos la sala seleccionada
            pulsarFuera = { salaSeleccionada = null } // Al pulsar fuera del dialogo, se sale y se vuelve a null
        )
    }
}






@Composable
fun mostrarDialogo(textoMostrar : String, pulsarFuera : () -> Unit){
    Dialog(
        onDismissRequest = {pulsarFuera()} ,    // Si pulsa fuera del dialog
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
        ){

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Text(textoMostrar)
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewSalas() {
    ControlDeAccesoKotlinTheme {
        Salas()
    }
}