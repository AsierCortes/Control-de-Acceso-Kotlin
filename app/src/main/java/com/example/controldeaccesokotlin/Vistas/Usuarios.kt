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
    val getDatos = controller.publicModelo.collectAsState()

    var texto by remember { mutableStateOf("") }
    var mostrarFiltros by remember { mutableStateOf(false) }
    var estadoSeleccionado by remember { mutableStateOf("Todos") }
    var ordenSeleccionado by remember { mutableStateOf("Nombre A-Z") }

    val listaUsuarios: List<Usuario> = getDatos.value.usuarios




    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "Gestión de Usuarios",
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

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listaUsuarios) { usuarioActual ->
                Tarjeta(usuarioActual)
            }
        }
    }

    if (mostrarFiltros) {
        Filtros(
            estadoActual = estadoSeleccionado,
            ordenActual = ordenSeleccionado,
            Cancelar = { mostrarFiltros = false },
            Aplicar = { estado, orden ->
                estadoSeleccionado = estado
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
        OutlinedTextField(
            value = texto,
            onValueChange = onTextoChange,
            modifier = Modifier
                .weight(1f)
                .height(50.dp),
            placeholder = {
                Text("Buscar usuarios...", color = Color.Gray)
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
        Text("Filtros")
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

                Image(
                    painter = painterResource(id = R.drawable.perfilusuario),
                    contentDescription = "foto perfil usuario",
                    modifier = Modifier.size(60.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(Modifier.width(8.dp))

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
    estadoActual: String,
    ordenActual: String,
    Cancelar: () -> Unit,
    Aplicar: (String, String) -> Unit,
) {
    var estado by remember { mutableStateOf(estadoActual) }
    var orden by remember { mutableStateOf(ordenActual) }

    AlertDialog(
        onDismissRequest = Cancelar,
        title = { Text("Filtros") },
        text = {
            Column {
                DesplegableEstado(estado) { estado = it }
                Spacer(Modifier.height(8.dp))
                DesplegableOrdenarPor(orden) { orden = it }
            }
        },
        confirmButton = {
            Button(onClick = { Aplicar(estado, orden) }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            Button(onClick = Cancelar) {
                Text("Cancelar")
            }
        }
    )
}

// ------------------ DESPLEGABLES ------------------
@Composable
fun DesplegableEstado(seleccionado: String, onSeleccionChange: (String) -> Unit) {
    val opciones = listOf("Todos", "Activo", "Inactivo", "Bloqueado")
    var expandir by remember { mutableStateOf(false) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expandir = true }
                .padding(8.dp)
        ) {
            Text(seleccionado, Modifier.weight(1f))
            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
        }

        DropdownMenu(
            expanded = expandir,
            onDismissRequest = { expandir = false }
        ) {
            opciones.forEach {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = {
                        onSeleccionChange(it)
                        expandir = false
                    }
                )
            }
        }
    }
}

@Composable
fun DesplegableOrdenarPor(seleccionado: String, onSeleccionChange: (String) -> Unit) {
    val opciones = listOf("Nombre A-Z", "Nombre Z-A", "FechaAlta(Reciente)", "FechaAlta(Antigua)")
    var expandir by remember { mutableStateOf(false) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expandir = true }
                .padding(8.dp)
        ) {
            Text(seleccionado, Modifier.weight(1f))
            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
        }

        DropdownMenu(
            expanded = expandir,
            onDismissRequest = { expandir = false }
        ) {
            opciones.forEach {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = {
                        onSeleccionChange(it)
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
