package com.example.controldeaccesokotlin.Vistas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.controldeaccesokotlin.Main
import com.example.controldeaccesokotlin.ui.theme.ControlDeAccesoKotlinTheme

@Composable
fun Salas() {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Salas")
        SelectorListaSalas()


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorListaSalas(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    var todasSeleccionada = true
    var libresSeleccionada = false
    var ocupadasSeleccionada = false

   var tabSeleccionado by remember { mutableStateOf(0) }



    var selectedDestination by rememberSaveable { mutableStateOf("todas") }

    PrimaryTabRow(selectedTabIndex = tabSeleccionado, modifier = modifier) {
        Tab(todasSeleccionada, {
            navController.navigate("todas")
            todasSeleccionada = true
            libresSeleccionada = false
            ocupadasSeleccionada = false

            tabSeleccionado = 0
        }) { Text("Todas") }

        Tab(libresSeleccionada, {
            navController.navigate("libres")
            libresSeleccionada = true
            todasSeleccionada = false
            ocupadasSeleccionada = false

            tabSeleccionado = 1
        }) { Text("Libre") }

        Tab(ocupadasSeleccionada, {
            navController.navigate("ocupadas")
            ocupadasSeleccionada = true
            todasSeleccionada = false
            libresSeleccionada = false

            tabSeleccionado = 2
        }) { Text("Ocupadas") }
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
            .fillMaxSize()
            .background(Color.LightGray),
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
            .fillMaxSize()
            .background(Color.LightGray),
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
            .fillMaxSize()
            .background(Color.LightGray),
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
        listOf<String>("Sala 01", "Sala 02", "Sala 03", "Sala 04", "Sala 05", "Sala 06", "Sala 07")

    // Va a ver dos salas por fila
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(salas) {
            Card() {
                Text(it)
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