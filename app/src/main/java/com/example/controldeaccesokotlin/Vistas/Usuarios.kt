package com.example.controldeaccesokotlin.Vistas

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.controldeaccesokotlin.R
import com.example.controldeaccesokotlin.bd_api.Usuario
import com.example.controldeaccesokotlin.viewModel.ControlAccesoViewModel


// ------------------ PANTALLA PRINCIPAL ------------------
@Composable
fun Usuarios(controller: ControlAccesoViewModel = viewModel()) {

        // Se obtiene el StateFlow del ViewModel un un state y cada vez que hay un cambio se actualiza
        val getDatos = controller.publicModelo.collectAsState()


    // variables locales para el manejo de la UI
        var texto by remember { mutableStateOf("") }
        var mostrarFiltros by remember { mutableStateOf(false) }
        var ordenSeleccionado by remember { mutableStateOf("Nombre A-Z") }

        // se obtiene la lista de usuarios del StateFlow y se actualiza cuando cambie
        val listaUsuarios: List<Usuario> = getDatos.value.usuarios




    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = stringResource(id = R.string.Info_sala),
            style = typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        BuscarTexto(
            texto = texto,
            onTextoChange = { texto = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        BotonFiltrar(onClick = { mostrarFiltros = true })

        Spacer(modifier = Modifier.height(16.dp))
        //muestra los usuarios en la pantalla
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listaUsuarios) { usuarioActual ->
                Tarjeta(usuarioActual)
            }
        }
    }
        //filtros sin mostrar el estado de los usarios
    if (mostrarFiltros) {
        Filtros(
            ordenActual = ordenSeleccionado,
            Cancelar = { mostrarFiltros = false },
            Aplicar = { orden ->
                ordenSeleccionado = orden
                mostrarFiltros = false
            }
        )
    }
}

// ------------------ BUSCADOR ------------------
@Composable
fun BuscarTexto(
    texto: String,
    onTextoChange: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // textfield para introducir la busqueda de usuario
        OutlinedTextField(
            value = texto,
            onValueChange = onTextoChange,
            modifier = Modifier
                .weight(1f)
                .height(50.dp),
            placeholder = {
                Text(stringResource(id = R.string.Buscar_usuarios), color = Color.Gray)
            },
            singleLine = true,
            textStyle = typography.bodyMedium,
            shape = RoundedCornerShape(10.dp),


            //Icono de busuqeda
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

        // boton para buscar usuarios
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
}

// ------------------ BOTÓN FILTROS ------------------
@Composable
fun BotonFiltrar(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.height(50.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Icon(Icons.Filled.AddCircle, contentDescription = "Filtrar")
        Spacer(modifier = Modifier.width(8.dp))
        Text(stringResource(id = R.string.Filtros))
    }
}

// ------------------ TARJETA ------------------
@Composable
fun Tarjeta(usuario: Usuario) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(Modifier.padding(12.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                //perfil del usuario
                Image(
                    painter = painterResource(id = R.drawable.perfilusuario),
                    contentDescription = "foto perfil usuario",
                    modifier = Modifier.size(60.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(Modifier.width(8.dp))
                // datos del usuario
                Column {
                    Text("Id: ${usuario.id}")
                    Text(usuario.nombre, fontWeight = FontWeight.Bold)
                    Text(usuario.email)
                    Text("Rol: ${usuario.rol_id}")
                }
            }


        }
    }


}


// ------------------ FILTROS ------------------
@Composable
fun Filtros(
    ordenActual: String,
    Cancelar: () -> Unit,
    Aplicar: (String) -> Unit,
) {
    //variable para filtrar segun el orden
    var orden by remember { mutableStateOf(ordenActual) }

    AlertDialog(
        onDismissRequest = Cancelar,
        title = { Text(stringResource(id = R.string.Filtros)) },
        text = {
            Column {
                DesplegableOrdenarPor(orden) { orden = it }
            }
        },
        confirmButton = {
            Button(onClick = { Aplicar(orden) }) {
                Text(stringResource(id = R.string.Guardar))
            }
        },
        dismissButton = {
            Button(onClick = Cancelar) {
                Text(stringResource(id = R.string.Cancelar))
            }
        }
    )
}

// ------------------ DESPLEGABLE ------------------

//Actualizado a un solo desplegable debido a que solo hay dos opciones
@Composable
fun DesplegableOrdenarPor(orden: String, onSeleccionChange: (String) -> Unit) {
    val opciones = listOf("Nombre A - Z", "Nombre Z - A")
    var expandir by remember { mutableStateOf(false) }

    Box {
        // El texto que pulsas
        Text(
            text = orden,
            modifier = Modifier
                .clickable { expandir = true }
                .padding(16.dp)
        )

        // Lo que se despliega
        DropdownMenu(
            expanded = expandir,
            onDismissRequest = { expandir = false }
        ) {
            opciones.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(opcion) },
                    onClick = {
                        onSeleccionChange(opcion)
                        expandir = false
                    }
                )
            }
        }
    }
}


// ------------------ PREVIEWS ------------------
@Preview
@Composable
fun UsuariosPreview() {
    Usuarios()
}
