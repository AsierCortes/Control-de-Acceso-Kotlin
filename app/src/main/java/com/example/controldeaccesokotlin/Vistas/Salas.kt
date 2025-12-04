package com.example.controldeaccesokotlin.Vistas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

        Spacer(Modifier.padding(7.dp))
        SelectorListaSalas()


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
        }) { Text(text = "Todas", style = typography.titleMedium) }

        // LIBRE
        Tab(libres, {
            navController.navigate("libres")
            libres = true
            todas = false
            ocupadas = false

            tabSeleccionado = 1
        }) { Text(text = "Libres", style = typography.titleMedium) }

        // OCUPADAS
        Tab(ocupadas, {
            navController.navigate("ocupadas")
            ocupadas = true
            todas = false
            libres = false

            tabSeleccionado = 2
        }) { Text(text = "Ocupadas", style = typography.titleMedium) }
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
        Text("Hola desde Todas")
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
        Text("Hola desde Libre")
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
        Text("Hola desde Ocupadas")
        GenerarSalas()
    }
}

@Composable
fun GenerarSalas() {
    val salas =
        listOf<String>("Sala 01", "Sala 02", "Sala 03", "Sala 04", "Sala 05", "Sala 06", "Sala 07", "Sala 08", "Sala 09", "Sala 10", "Sala 11", "Sala 12", "Sala 13", "Sala 14", "Sala 15")

    LazyVerticalGrid(
        // Esto va a manejar lo que ocupa la carta también
        modifier = Modifier.fillMaxWidth(0.85f),
        columns = GridCells.Fixed(1),
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.spacedBy(18.dp)    // Separación entre elementos
    ) {
        items(salas) {
            // CARTA -> No sirve como contenedor de modifier
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),   // SOMBRA TARJETA
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceBright),
                border = BorderStroke(1.dp, Color.Black),

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
                    Text(text = it, style = typography.titleMedium)
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
}

@Preview(showBackground = true)
@Composable
fun PreviewSalas() {
    ControlDeAccesoKotlinTheme {
        Salas()
    }
}