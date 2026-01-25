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
fun Usuarios(viewModel: UsuariosViewModel = viewModel()) {
    var textoBusqueda by remember { mutableStateOf("") }
    var mostrarFiltros by remember { mutableStateOf(false) }
    var ordenSeleccionado by remember { mutableStateOf("Nombre A-Z") }

    val usuarios by viewModel.usuarios.collectAsState()

    LaunchedEffect(Unit) { viewModel.getUsuarios() }

    val usuariosOrdenados = remember(usuarios, ordenSeleccionado) {
        if (ordenSeleccionado == "Nombre A-Z") usuarios.sortedBy { it.nombre }
        else usuarios.sortedByDescending { it.nombre }
    }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text(
            "Gestión de Usuarios",
            style = typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(24.dp))

        BuscarTexto(
            texto = textoBusqueda,
            onTextoChange = { textoBusqueda = it },
            onBuscarClick = { viewModel.buscarUsuarios(textoBusqueda) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        BotonFiltrar { mostrarFiltros = true }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(usuariosOrdenados) { usuario -> Tarjeta(usuario) }
        }
    }

    if (mostrarFiltros) {
        Filtros(
            ordenActual = ordenSeleccionado,
            Cancelar = { mostrarFiltros = false },
            Aplicar = { nuevoOrden ->
                ordenSeleccionado = nuevoOrden
                mostrarFiltros = false
            }
        )
    }
}

@Composable
fun BuscarTexto(texto: String, onTextoChange: (String) -> Unit, onBuscarClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = texto,
            onValueChange = onTextoChange,
            modifier = Modifier.weight(1f).height(50.dp),
            placeholder = { Text("Buscar usuarios...", color = Color.Gray) },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            leadingIcon = { Icon(Icons.Default.Search, null, modifier = Modifier.size(20.dp)) }
        )

        Button(
            onClick = onBuscarClick,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.height(50.dp)
        ) {
            Text("Buscar", fontWeight = FontWeight.Bold)
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.perfilusuario),
                contentDescription = null,
                modifier = Modifier.size(60.dp).clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.width(12.dp))

            Column {
                Text(usuario.nombre, fontWeight = FontWeight.Bold, style = typography.titleMedium)
                Text(usuario.email, style = typography.bodySmall)
                Text(
                    text = "Rol: ${usuario.rol?.tipo ?: usuario.rol_id}",
                    style = typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

// ------------------ FILTROS ------------------
@Composable
fun Filtros(
    ordenActual: String,
    Cancelar: () -> Unit,
    Aplicar: (String) -> Unit
) {
    var orden by remember { mutableStateOf(ordenActual) }

    AlertDialog(
        onDismissRequest = Cancelar,
        title = { Text("Opciones de Visualización") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text("Ordenar lista local:", fontWeight = FontWeight.Bold)
                DesplegableSimple(orden) { orden = it }
            }
        },
        confirmButton = {
            Button(onClick = { Aplicar(orden) }) { Text("Aplicar") }
        },
        dismissButton = {
            TextButton(onClick = Cancelar) { Text("Cancelar") }
        }
    )
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
