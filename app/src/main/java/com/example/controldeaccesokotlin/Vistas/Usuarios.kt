package com.example.controldeaccesokotlin.Vistas

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.controldeaccesokotlin.R
import com.example.controldeaccesokotlin.bd_api.ModeloUsuario1
import com.example.controldeaccesokotlin.viewModel.UsuariosViewModel

// ------------------ PANTALLA PRINCIPAL ------------------
@Composable
fun Usuarios(
    viewModel: UsuariosViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    var texto by remember { mutableStateOf("") }
    var mostrarFiltros by remember { mutableStateOf(false) }
    var ordenSeleccionado by remember { mutableStateOf("Nombre A-Z") }

    val usuarios by viewModel.usuarios.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getUsuarios()
    }

    val usuariosFiltrados = usuarios
        .filter {
            it.nombre.contains(texto, true) ||
                    it.email.contains(texto, true)
        }
        .sortedWith(
            when (ordenSeleccionado) {
                "Nombre A-Z" -> compareBy { it.nombre }
                "Nombre Z-A" -> compareByDescending { it.nombre }
                else -> compareBy { it.nombre }
            }
        )

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

        BotonFiltrar { mostrarFiltros = true }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(usuariosFiltrados) { usuario ->
                Tarjeta(usuario)
            }
        }
    }

    if (mostrarFiltros) {
        Filtros(
            ordenActual = ordenSeleccionado,
            Cancelar = { mostrarFiltros = false },
            Aplicar = {
                ordenSeleccionado = it
                mostrarFiltros = false
            }
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
fun Tarjeta(usuario: ModeloUsuario1) {

    var mostrarMenuEdicion by remember { mutableStateOf(false) }
    var expandir by remember { mutableStateOf(false) }
    val context = LocalContext.current

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
                }
            }

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expandir = !expandir },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    if (expandir) "Ocultar acciones" else "Mostrar acciones",
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    if (expandir) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            }

            if (expandir) {
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = { mostrarMenuEdicion = true },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Editar") }

                Button(
                    onClick = {
                        Toast.makeText(context, "Usuario Eliminado", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
                ) {
                    Text("Eliminar")
                }
            }
        }
    }

    if (mostrarMenuEdicion) {
        EditarUsuario(
            usuario = usuario,
            Cancelar = { mostrarMenuEdicion = false },
            Aplicar = { mostrarMenuEdicion = false }
        )
    }
}

// ------------------ EDITAR USUARIO ------------------
@Composable
fun EditarUsuario(
    usuario: ModeloUsuario1,
    Cancelar: () -> Unit,
    Aplicar: (ModeloUsuario1) -> Unit
) {
    var nombre by remember { mutableStateOf(usuario.nombre) }
    var email by remember { mutableStateOf(usuario.email) }
    var rol_id by remember { mutableStateOf(usuario.rol_id) }



    Dialog(
        onDismissRequest = Cancelar,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(0.9f),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text("Información del usuario", fontWeight = FontWeight.Bold)

                TextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") })
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") })
                TextField(
                    value = rol_id.toString(),
                    onValueChange = {
                        rol_id = it.toIntOrNull() ?: 0
                    },
                    label = { Text("Rol_ID") }
                )

                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = Cancelar) { Text("Cancelar") }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = {
                        Aplicar(
                            ModeloUsuario1(
                                nombre = nombre,
                                email = email,
                                rol_id = rol_id
                            )
                        )
                    }) {
                        Text("Guardar")
                    }
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
    Aplicar: (String) -> Unit
) {
    var orden by remember { mutableStateOf(ordenActual) }

    AlertDialog(
        onDismissRequest = Cancelar,
        title = { Text("Filtros") },
        text = {
            Column {
                DesplegableOrdenarPor(orden) { orden = it }
            }
        },
        confirmButton = {
            Button(onClick = { Aplicar(orden) }) {
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

// ---------- DESPLEGABLE -------------
@Composable
fun DesplegableOrdenarPor(seleccionado: String, onSeleccionChange: (String) -> Unit) {
    val opciones = listOf("Nombre A-Z", "Nombre Z-A")
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
