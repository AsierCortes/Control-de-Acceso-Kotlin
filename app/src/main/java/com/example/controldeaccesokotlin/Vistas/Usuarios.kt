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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.controldeaccesokotlin.R

// ------------------ MODELO ------------------
data class Usuario(
    val nombre: String,
    val email: String,
    val telefono: String,
    val fecha: String,
    val activo: Boolean,
    val bloqueado: Boolean
)

// ------------------ PANTALLA PRINCIPAL ------------------
@Composable
fun Usuarios() {

    var texto by remember { mutableStateOf("") }
    var mostrarFiltros by remember { mutableStateOf(false) }
    var estadoSeleccionado by remember { mutableStateOf("Todos") }
    var ordenSeleccionado by remember { mutableStateOf("Nombre A-Z") }

    var usuarios by remember {
        mutableStateOf(
            listOf(
                Usuario("Bruno Linares", "bruno@gmail.com", "600111222", "13/03/2024", true, false),
                Usuario("Ana Pérez", "ana@gmail.com", "600222333", "13/03/2024", true, false),
                Usuario("Carlos Ruiz", "carlos@gmail.com", "600333444", "13/03/2024", true, false),
                Usuario("Lucía Gómez", "lucia@gmail.com", "600444555", "13/03/2024", true, false),
                Usuario(
                    "Miguel Torres",
                    "miguel@gmail.com",
                    "600555666",
                    "13/03/2024",
                    true,
                    false
                ),
                Usuario(
                    "Sofía Martínez",
                    "sofia@gmail.com",
                    "600666777",
                    "13/03/2024",
                    true,
                    false
                ),
                Usuario(
                    "David Fernández",
                    "david@gmail.com",
                    "600777888",
                    "13/03/2024",
                    true,
                    false
                ),
                Usuario("Laura Sánchez", "laura@gmail.com", "600888999", "13/03/2024", true, false),
                Usuario(
                    "Javier Morales",
                    "javier@gmail.com",
                    "600999000",
                    "13/03/2024",
                    true,
                    false
                ),
                Usuario("Elena Navarro", "elena@gmail.com", "601000111", "13/03/2024", true, false)
            )
        )
    }

    val usuariosFiltrados = usuarios
        .filter {
            it.nombre.contains(texto, true) ||
                    it.email.contains(texto, true)
        }
        .filter {
            when (estadoSeleccionado) {
                "Activo" -> it.activo && !it.bloqueado
                "Inactivo" -> !it.activo
                "Bloqueado" -> it.bloqueado
                else -> true
            }
        }
        .sortedWith(
            when (ordenSeleccionado) {
                "Nombre A-Z" -> compareBy { it.nombre }
                "Nombre Z-A" -> compareByDescending { it.nombre }
                "FechaAlta(Reciente)" -> compareByDescending { it.fecha }
                "FechaAlta(Antigua)" -> compareBy { it.fecha }
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

        BotonFiltrar(onClick = { mostrarFiltros = true })

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
fun Tarjeta(usuario: Usuario) {

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
            Aplicar = { mostrarMenuEdicion = false },
            Permisos = { mostrarMenuEdicion = false },
            Bloquear = { mostrarMenuEdicion = false }
        )
    }
}

// ------------------ EDITAR USUARIO ------------------
@Composable
fun EditarUsuario(
    usuario: Usuario,
    Cancelar: () -> Unit,
    Permisos: () -> Unit,
    Bloquear: () -> Unit,
    Aplicar: () -> Unit
) {
    var nombre by remember { mutableStateOf(usuario.nombre) }
    var email by remember { mutableStateOf(usuario.email) }
    var telefono by remember { mutableStateOf(usuario.telefono) }
    var fecha by remember { mutableStateOf(usuario.fecha) }
    var activo by remember { mutableStateOf(usuario.activo) }
    var bloqueado by remember { mutableStateOf(usuario.bloqueado) }

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
                TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
                TextField(
                    value = telefono,
                    onValueChange = { telefono = it },
                    label = { Text("Teléfono") })
                TextField(value = fecha, onValueChange = { fecha = it }, label = { Text("Fecha") })

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Activo")
                    Switch(checked = activo, onCheckedChange = { activo = it })
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Bloqueado")
                    Switch(checked = bloqueado, onCheckedChange = { bloqueado = it })
                }

                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = Cancelar) { Text("Cancelar") }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = Aplicar) { Text("Guardar") }
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
    Aplicar: (String, String) -> Unit
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
