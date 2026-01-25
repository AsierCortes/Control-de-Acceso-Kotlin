package com.example.controldeaccesokotlin.Vistas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.controldeaccesokotlin.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(changePrincipal: (String) -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column (
                modifier = Modifier.fillMaxWidth(0.85f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ){
                Image(
                    painter = painterResource(R.drawable.logobueno),
                    contentDescription = "Foto salas",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(200.dp)
                        .align(Alignment.CenterHorizontally)

                )
                Text(
                    text = stringResource(id = R.string.correo),
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(top = 50.dp)
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    onValueChange = {},
                    value = ""
                )

                Spacer(Modifier.padding(20.dp))
                Text(
                    text = stringResource(id = R.string.pwd),
                    modifier = Modifier.align(Alignment.Start)

                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    onValueChange = {},
                    value = ""
                )

                Button(
                    onClick = { changePrincipal("salas") },
                    modifier = Modifier.padding(top = 30.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.mensaje_btn_acceder)
                    )
                }
            }

        }
    }

//    Scaffold(
//        modifier = Modifier
//            .fillMaxSize(),
//        topBar = {CustomTopBar()}
//
//    ) {padding ->
//    Column (Modifier.padding(padding).fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
//        Text("HOLA DESDE LOGIN")
//        Button(onClick = {changePrincipal("salas")}) { Text("Acceder a login")}

}


// PARTE DE ARRIBA (Palabra y logo)
//@ Jenn: Lo cambié para poner el fondo, si dejamos ese bg borramos esta función y el Scaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar() {
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

@Composable
@Preview
fun PreviewLogin(){
    Login {  }
}