package com.example.controldeaccesokotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.controldeaccesokotlin.Vistas.Login
import com.example.controldeaccesokotlin.Vistas.Notificaciones
import com.example.controldeaccesokotlin.Vistas.Salas
import com.example.controldeaccesokotlin.Vistas.Usuarios
import com.example.controldeaccesokotlin.ui.theme.ControlDeAccesoKotlinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ControlDeAccesoKotlinTheme {
                Main()
            }
        }
    }
}

// NAVEGACIÓN
@Composable
fun Main(){
    val navController = rememberNavController();

    //Saber en qué ventana estamos en todo momento.
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {if (currentRoute != "login") {
            CustomBottomBar(
            changeView = {
                rutaSeleccionada ->
                navController.navigate(rutaSeleccionada)
            }
        )
        }}
    ) { innerPadding ->

        // Ventana de comienzo
        NavHost(navController = navController, startDestination = "login", modifier = Modifier.padding(innerPadding)){
            composable("salas") { Salas()}
            composable("usuarios") { Usuarios() }
            composable("notificaciones") { Notificaciones() }
            composable("login") { Login(changePrincipal = {
                rutaSeleccionada ->
                navController.navigate(rutaSeleccionada)
            }) }


        }
    }
}


@Composable
fun CustomBottomBar(changeView : (String) -> Unit){

    var salasChecked by remember { mutableStateOf(true) }
    var usuariosChecked by remember { mutableStateOf(false) }
    var notificacionesChecked by remember { mutableStateOf(false) }

    NavigationBar {
        // SALAS
        NavigationBarItem(
            selected = salasChecked,
            onClick = {changeView("salas"); salasChecked = true; usuariosChecked = false; notificacionesChecked = false },
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Icono salas") },
            label = { Text(text = "Salas") }

        )

        // USUARIOS
        NavigationBarItem(
            selected = usuariosChecked,
            onClick = {changeView("usuarios"); usuariosChecked = true; salasChecked = false; notificacionesChecked = false},
            icon = { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Icono usuarios") },
            label = { Text(text = "Usuarios") }

        )
        // NOTIFICACIONES
        NavigationBarItem(
            selected = notificacionesChecked,
            onClick = {changeView("notificaciones"); notificacionesChecked = true; salasChecked = false; usuariosChecked = false},
            icon = { Icon(imageVector = Icons.Default.Notifications, contentDescription = "Icono notificaciones") },
            label = { Text(text = "Notificaciones") }

        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ControlDeAccesoKotlinTheme {
        Main()
    }
}

