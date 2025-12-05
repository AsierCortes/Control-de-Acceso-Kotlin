package com.example.controldeaccesokotlin.Vistas

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class Usuario(
    val nombre: String,
    val email: String,
    val telefono: String,
    val fecha: String,
    val activo: Boolean,
    val bloqueado: Boolean,
)


@Composable
fun Usuarios() {
    var texto by remember { mutableStateOf("") }
    var registrar by remember { mutableStateOf(false) }
    var cargar by remember { mutableStateOf(false) }
    var mostrarFiltros by remember { mutableStateOf(false) }

    var usuarios by remember {
        mutableStateOf(
            listOf(
                Usuario("Bruno Linares", "bruno@gmail.com", "600111222", "13/03/2024",true,false),
                Usuario("Ana Pérez", "ana@gmail.com", "600222333", "13/03/2024",true,false),
                Usuario("Carlos Ruiz", "carlos@gmail.com", "600333444", "13/03/2024",true,false),
                Usuario("Lucía Gómez", "lucia@gmail.com", "600444555", "13/03/2024",true,false),
                Usuario("Miguel Torres", "miguel@gmail.com", "600555666", "13/03/2024",true,false),
                Usuario("Sofía Martínez", "sofia@gmail.com", "600666777", "13/03/2024",true,false),
                Usuario("David Fernández", "david@gmail.com", "600777888", "13/03/2024",true,false),
                Usuario("Laura Sánchez", "laura@gmail.com", "600888999", "13/03/2024",true,false),
                Usuario("Javier Morales", "javier@gmail.com", "600999000", "13/03/2024",true,false),
                Usuario("Elena Navarro", "elena@gmail.com", "601000111", "13/03/2024",true,false)
            )
        )
    }

    val usuariosFiltrados = usuarios.filter {
        it.nombre.contains(texto, ignoreCase = true) ||
                it.email.contains(texto, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Gestión de Usuarios",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {

            Button(
                onClick = { registrar = true },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                modifier = Modifier
                    .height(50.dp)
            ) {
                Text("Nuevo Usuario")
            }

            Button(
                onClick = { cargar = true },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                modifier = Modifier
                    .height(50.dp)
            ) {
                Text("Carga Masiva (CSV)")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        BuscarTexto()

        Spacer(modifier = Modifier.height(16.dp))


        BotonFiltrar(onClick = { mostrarFiltros = true })

        if (mostrarFiltros) {
            Filtros(
                Cancelar = { mostrarFiltros = false }
                , Aplicar = { mostrarFiltros = false }
            )
        }


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

    if (cargar) {
        CargaMasiva(
            Cancelar = { cargar = false },
            Importar = {
                println("Importando usuarios desde CSV...")
                cargar = false
            },
        )
    }


    if (registrar) {
        NuevoUsuario(
            Cancelar = { registrar = false },
            Guardar = { nombre, email, telefono, fecha ->
                if (nombre.isNotBlank() && email.isNotBlank() && telefono.isNotBlank() && fecha.isNotBlank()) {
                    usuarios = usuarios + Usuario(nombre, email, telefono, fecha, true, false)

                }
                registrar = false
            }
        )
    }
}

@Composable
fun CargaMasiva(
    Cancelar: () -> Unit,
    Importar: () -> Unit
) {
    AlertDialog(
        onDismissRequest = Cancelar,
        title = { Text("Carga Masiva (CSV)") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Formato del archivo CSV",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("• Columnas requeridas: nombre, apellidos, email, telefono, rol")
                Text("• Roles válidos: admin, usuario")
                Text("• Columnas opcionales: activo (true/false), bloqueado (true/false)")
                Text("• La primera fila debe contener los nombres de las columnas")

                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .border(2.dp, Color.Gray, RoundedCornerShape(8.dp))
                        .clickable { },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Haz clic para seleccionar un archivo CSV\no arrastra y suelta aquí",
                        textAlign = TextAlign.Center
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = Importar) {
                Text("Importar Usuarios...")
            }
        },
        dismissButton = {
            Button(onClick = Cancelar) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun NuevoUsuario(
    Cancelar: () -> Unit,
    Guardar: (String, String, String, String) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }

    var usuarioActivo by remember { mutableStateOf(false) }
    var usuarioBloqueado by remember { mutableStateOf(false) }
    AlertDialog(
        onDismissRequest = Cancelar,
        title = { Text("Nuevo Usuario") },
        text = {
            Column {
                Text(
                    "* Campos obligatorios*",
                    style = typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Nombre Completo*",
                    style = typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre Completo") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Email*",
                    style = typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("ejemplo@gmail.com") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Telefono *",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                OutlinedTextField(
                    value = telefono,
                    onValueChange = { telefono = it },
                    label = { Text("+34 600 333 999") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Rol *",
                    style = typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Desplegable()
                Text(
                    "Fecha del alta *",
                    style = typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                OutlinedTextField(
                    value = fecha,
                    onValueChange = { fecha = it },
                    label = { Text("DD/MM/AAAA") },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = usuarioActivo,
                        onCheckedChange = { usuarioActivo = it }
                    )
                    Text(
                        "Usuario Activo",
                        style = typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = usuarioBloqueado,
                        onCheckedChange = { usuarioBloqueado = it }
                    )
                    Text(
                        "Usuario Bloqueado",
                        style = typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }


        },
        confirmButton = {
            Button(onClick = { Guardar(nombre, email, telefono, fecha) }) {
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

@Composable
fun Desplegable() {
    var expandir by remember { mutableStateOf(false) }
    val opciones = listOf("Usuario", "Administrador")
    var seleccion by remember { mutableStateOf(opciones[0]) }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expandir = true }
                .padding(8.dp)
        ) {
            Text(
                text = seleccion,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Desplegar"
            )
        }

        // Menú desplegable
        DropdownMenu(
            expanded = expandir,
            onDismissRequest = { expandir = false }
        ) {
            opciones.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(opcion) },
                    onClick = {
                        seleccion = opcion
                        expandir = false
                    }
                )
            }
        }
    }
}
@Composable
fun DesplegableOrdenarPor() {
    var expandir by remember { mutableStateOf(false) }
    val opciones = listOf("Nombre A-Z", "Nombre Z-A", "FechaAlta(Reciente)", "FechaAlta(Antigua)")
    var seleccion by remember { mutableStateOf(opciones[0]) }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expandir = true }
                .padding(8.dp)
        ) {
            Text(
                text = seleccion,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Desplegar"
            )
        }

        // Menú desplegable
        DropdownMenu(
            expanded = expandir,
            onDismissRequest = { expandir = false }
        ) {
            opciones.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(opcion) },
                    onClick = {
                        seleccion = opcion
                        expandir = false
                    }
                )
            }
        }
    }
}
@Composable
fun DesplegableEstado() {
    var expandir by remember { mutableStateOf(false) }
    val opciones = listOf("Todos", "Activo", "Inactivo", "Bloqueado")
    var seleccion by remember { mutableStateOf(opciones[0]) }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expandir = true }
                .padding(8.dp)
        ) {
            Text(
                text = seleccion,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Desplegar"
            )
        }

        DropdownMenu(
            expanded = expandir,
            onDismissRequest = { expandir = false }
        ) {
            opciones.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(opcion) },
                    onClick = {
                        seleccion = opcion
                        expandir = false
                    }
                )
            }
        }
    }
}
@Composable
fun BuscarTexto(

) {
    var textoIntroducido by remember { mutableStateOf("") }

    OutlinedTextField(
        value = textoIntroducido,
        onValueChange = { textoIntroducido = it },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        placeholder = {
            Text(
                text = "Buscar sala...",
                style = typography.bodyMedium,
                color = Color.Gray
            )
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
}

@Composable
fun BotonFiltrar(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(50.dp)
            .padding(horizontal = 8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.AddCircle,
            contentDescription = "Filtrar",
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Filtros",
            color = Color.White,
            style = typography.labelLarge
        )
    }
}

@Composable
fun Tarjeta(usuario: Usuario) {
    var expandir by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .clip(RoundedCornerShape(12.dp))
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Encabezado
            Text(
                text = usuario.nombre,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
            Text(
                text = usuario.email,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Fila clickable para expandir/contraer
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expandir = !expandir },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (expandir) "Ocultar acciones" else "Mostrar acciones",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (expandir) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expandir/Contraer"
                )
            }

            if (expandir) {
                Spacer(modifier = Modifier.height(8.dp))

                Column {
                    Button(
                        onClick = {
                            Toast.makeText(context, "Mostrar información", Toast.LENGTH_SHORT)
                                .show()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("Mostrar información") }

                    Button(
                        onClick = {
                            Toast.makeText(context, "Editar", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("Editar") }

                    Button(
                        onClick = {
                            Toast.makeText(context, "Permisos", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("Permisos") }

                    Button(
                        onClick = {
                            Toast.makeText(context, "Bloquear", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("Bloquear") }

                    Button(
                        onClick = {
                            Toast.makeText(context, "Eliminar", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("Eliminar") }
                }
            }
        }
    }}
    @Composable
    fun Filtros(
        Cancelar: () -> Unit,
        Aplicar: () -> Unit) {
        var desdeFecha by remember { mutableStateOf("") }
        var hastaFecha by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { /* nada */ },
            title = { Text("Filtrar por:") },
            text = {
                Column {
                    Text(
                        "Fecha Alta Desde*",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    OutlinedTextField(
                        value = desdeFecha,
                        onValueChange = { desdeFecha = it },
                        label = { Text("DD/MM/AAAA") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        "Fecha Alta Hasta*",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    OutlinedTextField(
                        value = hastaFecha,
                        onValueChange = { hastaFecha = it },
                        label = { Text("DD/MM/AAAA") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        "Estado",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    DesplegableEstado()

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        "Rol *",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    DesplegableOrdenarPor()
                }
            },
            confirmButton = {
                Button(onClick = { /* nada */ }) {
                    Text("Aplicar filtros")
                }
            },
            dismissButton = {
                Button(onClick = { /* nada */ }) {
                    Text("Cancelar")
                }
            }
        )
    }

    @Preview
    @Composable
    fun UsuariosPreview() {
        Usuarios()
    }
