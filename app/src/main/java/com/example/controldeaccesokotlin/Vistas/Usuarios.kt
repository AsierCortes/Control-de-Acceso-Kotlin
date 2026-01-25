package com.example.controldeaccesokotlin.Vistas

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
import com.example.controldeaccesokotlin.bd_api.ModeloUsuario1
import com.example.controldeaccesokotlin.viewModel.UsuariosViewModel

// ------------------ PANTALLA PRINCIPAL ------------------
@Composable
fun Usuarios(
    viewModel: UsuariosViewModel = viewModel()
) {
    var texto by remember { mutableStateOf("") }
    var mostrarFiltros by remember { mutableStateOf(false) }
    var ordenSeleccionado by remember { mutableStateOf("Nombre A-Z") }
    var rolIdSeleccionado by remember { mutableStateOf<Int?>(null) }
    val usuarios by viewModel.usuarios.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getUsuarios()
    }

    val usuariosFiltrados = usuarios
        .filter {
            it.nombre.contains(texto, true) || it.email.contains(texto, true)
        }
        .sortedWith(
            if (ordenSeleccionado == "Nombre A-Z") compareBy { it.nombre }
            else compareByDescending { it.nombre }
        )

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Gestión de Usuarios", style = typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        BuscarTexto(texto = texto, onTextoChange = { texto = it })
        Spacer(modifier = Modifier.height(16.dp))

        BotonFiltrar { mostrarFiltros = true }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(usuariosFiltrados) { usuario -> Tarjeta(usuario) }
        }
    }

    if (mostrarFiltros) {
        Filtros(
            ordenActual = ordenSeleccionado,
            Cancelar = { mostrarFiltros = false },
            Aplicar = { orden, id ->
                ordenSeleccionado = orden
                rolIdSeleccionado = id
                mostrarFiltros = false

                viewModel.filtrarPorRol(id)
            }

            ,
            rolActual = rolIdSeleccionado
        )
    }
}
// ------------------ BUSCADOR ------------------
@Composable
fun BuscarTexto(
    texto: String,
    onTextoChange: (String) -> Unit
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
            textStyle = typography.bodyMedium,
            shape = RoundedCornerShape(10.dp),

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

        Button(
            onClick = {},
            shape = RoundedCornerShape(10.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier
                .height(50.dp)
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
fun Tarjeta(usuario: ModeloUsuario1) {

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
    rolActual: Int?,
    Cancelar: () -> Unit,
    Aplicar: (String, Int?) -> Unit
) {
    var orden by remember { mutableStateOf(ordenActual) }
    var idSeleccionado by remember { mutableStateOf(rolActual) }

    AlertDialog(
        onDismissRequest = Cancelar,
        title = { Text("Filtros de Búsqueda") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text("Ordenar por:", fontWeight = FontWeight.Bold)
                DesplegableSimple(orden) { orden = it }

                Text("Filtrar por Rol:", fontWeight = FontWeight.Bold)
                DesplegableID(idSeleccionado) { idSeleccionado = it }
            }
        },
        confirmButton = {
            Button(onClick = { Aplicar(orden, idSeleccionado) }) { Text("Aplicar") }
        },
        dismissButton = {
            TextButton(onClick = Cancelar) { Text("Cancelar") }
        }
    )
}
@Composable
fun DesplegableID(seleccionado: Int?, onSeleccion: (Int?) -> Unit) {
    val opciones = listOf(null, 1, 2, 3)
    var expandir by remember { mutableStateOf(false) }

    Box {
        OutlinedButton(onClick = { expandir = true }, modifier = Modifier.fillMaxWidth()) {
            Text(if (seleccionado == null) "Todos los Roles" else "Rol ID: $seleccionado")
            Icon(Icons.Default.ArrowDropDown, null)
        }
        DropdownMenu(expanded = expandir, onDismissRequest = { expandir = false }) {
            opciones.forEach { id ->
                DropdownMenuItem(
                    text = { Text(if (id == null) "Ver Todos" else "Rol ID: $id") },
                    onClick = {
                        onSeleccion(id)
                        expandir = false
                    }
                )
            }
        }
    }
}

@Composable
fun DesplegableSimple(seleccionado: String, onSeleccion: (String) -> Unit) {
    val opciones = listOf("Nombre A-Z", "Nombre Z-A")
    var expandir by remember { mutableStateOf(false) }

    Box {
        OutlinedButton(onClick = { expandir = true }, modifier = Modifier.fillMaxWidth()) {
            Text(seleccionado)
            Icon(Icons.Default.ArrowDropDown, null)
        }
        DropdownMenu(expanded = expandir, onDismissRequest = { expandir = false }) {
            opciones.forEach { op ->
                DropdownMenuItem(text = { Text(op) }, onClick = { onSeleccion(op); expandir = false })
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
