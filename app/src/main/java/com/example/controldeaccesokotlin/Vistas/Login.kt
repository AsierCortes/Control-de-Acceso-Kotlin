package com.example.controldeaccesokotlin.Vistas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.controldeaccesokotlin.R


@Composable
fun Login(changePrincipal: (String) -> Unit) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {CustomTopBar()}

    ) {padding ->
    Column (Modifier.padding(padding).fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("HOLA DESDE LOGIN")
        Button(onClick = {changePrincipal("salas")}) { Text("Acceder a login")}
    }
}
}

// PARTE DE ARRIBA (Palabra y logo)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(){
    TopAppBar(
        title = {
            Image(
                painter = painterResource(R.drawable.palabralogo),
                contentDescription = "Gate Manager Palabra",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize(0.9f)
            )
        },
        actions = {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Gate Manager Palabra",
                contentScale = ContentScale.Crop
            )
        }
    )
}



