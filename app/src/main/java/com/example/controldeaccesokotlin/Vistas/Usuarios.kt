package com.example.controldeaccesokotlin.Vistas

import android.widget.Button
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.controldeaccesokotlin.ModeloUsuarios
import com.example.controldeaccesokotlin.R



@Composable
fun Usuarios() {
    // Estados locales para controlar la UI
    var texto by remember { mutableStateOf("") }
    var registrar by remember { mutableStateOf(false) }
    var cargar by remember { mutableStateOf(false) }
    var mostrarFiltros by remember { mutableStateOf(false) }

    // USUARIOS EJEMPLO
    val usuarios = listOf(
        ModeloUsuarios("img1", "Juan", "Pérez", "López", "1º DAM", "juan@mail.com", "600111222", "12/09/23", true, false, mutableListOf("A1")),
        ModeloUsuarios("img2", "María", "García", "Ruiz", "2º DAW", "maria@mail.com", "600222333", "13/09/23", true, false, mutableListOf("B1", "B2")),
        ModeloUsuarios("img3", "Carlos", "Sánchez", "Gil", "1º ASIR", "carlos@mail.com", "600333444", "14/09/23", false, true, mutableListOf("C1")),
        ModeloUsuarios("img4", "Laura", "Martín", "Díaz", "2º DAM", "laura@mail.com", "600444555", "15/09/23", true, false, mutableListOf("D1")),
        ModeloUsuarios("img5", "Pedro", "Ruiz", "Sanz", "1º DAW", "pedro@mail.com", "600555666", "16/09/23", true, false, mutableListOf()),
        ModeloUsuarios("img6", "Sofía", "López", "Mora", "2º ASIR", "sofia@mail.com", "600666777", "17/09/23", true, false, mutableListOf("F1", "F2")),
        ModeloUsuarios("img7", "Javier", "Gómez", "Cano", "1º DAM", "javier@mail.com", "600777888", "18/09/23", false, false, mutableListOf("G1")),
        ModeloUsuarios("img8", "Elena", "Torres", "Vila", "2º DAW", "elena@mail.com", "600888999", "19/09/23", true, false, mutableListOf("H1")),
        ModeloUsuarios("img9", "Diego", "Díaz", "Pola", "1º ASIR", "diego@mail.com", "600999000", "20/09/23", true, true, mutableListOf("I1")),
        ModeloUsuarios("img10", "Ana", "Vargas", "Ríos", "2º DAM", "ana@mail.com", "600000111", "21/09/23", true, false, mutableListOf("J1"))
    )


    val usuariosFiltrados = usuarios.filter {
        it.nombre.contains(texto, ignoreCase = true) ||
                it.correoElectronico.contains(texto, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // Título
        Text(
            text = "Gestión de Usuarios",
            style = typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botones de acción
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {

            // Botón para registrar nuevo usuario
            Button(
                onClick = { registrar = true },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF263238)),
                modifier = Modifier.height(50.dp)
            ) {
                Text("Nuevo Usuario")
            }

            // Botón para carga masiva desde CSV
            Button(
                onClick = { cargar = true },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                modifier = Modifier.height(50.dp)
            ) {
                Text("Carga Masiva (CSV)")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        //TextField para buscar usuarios
        BuscarTexto()

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para abrir filtros
        BotonFiltrar(onClick = { mostrarFiltros = true })

        // Si se muestran filtros te lleva a la funcion filtros porque empece primero el boton de filtrar
        if (mostrarFiltros) {
            Filtros(
                Cancelar = { mostrarFiltros = false },
                Aplicar = { mostrarFiltros = false }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de usuarios filtrados
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(usuariosFiltrados) { usuario ->
                Tarjeta(usuario) // Mete cada usuario en una tarjeta
            }
        }
    }

    // Diálogo de carga masiva
    if (cargar) {
        CargaMasiva(
            Cancelar = { cargar = false },
            Importar = {
                println("Importando usuarios desde CSV...")
                cargar = false
            },
        )
    }

    // Diálogo de nuevo usuario
    if (registrar) {
        NuevoUsuario(
            Cancelar = { registrar = false },

            )
    }
}

@Composable
fun CargaMasiva(
    Cancelar: () -> Unit,
    Importar: () -> Unit
) {
    Dialog(
        onDismissRequest = { Cancelar() },    // Si pulsa fuera del dialog
        properties = DialogProperties(usePlatformDefaultWidth = false)      // para que el fondo oscurecido no sea tan brusco
    ) {
        // El dialog centra automáticamente la carta
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)  // 90% ancho
                .fillMaxHeight(0.6f),

            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                // Instrucciones sobre el formato del archivo CSV
                Text(
                    text = "Formato del archivo CSV",
                    style = typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "• Columnas requeridas: nombre, apellidos, email, telefono, rol",
                    textAlign = TextAlign.Justify,
                    style = typography.bodyLarge
                )
                Text(
                    text = "• Roles válidos: admin, usuario",
                    textAlign = TextAlign.Justify,
                    style = typography.bodyLarge
                )
                Text(
                    text = "• Columnas opcionales: activo (true/false), bloqueado (true/false)",
                    textAlign = TextAlign.Justify,
                    style = typography.bodyLarge
                )
                Text(
                    text = "• La primera fila debe contener los nombres de las columnas",
                    textAlign = TextAlign.Justify,
                    style = typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Área simulada para seleccionar o arrastrar archivo
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .border(2.dp, Color.Gray, RoundedCornerShape(8.dp))
                        .clickable { }, // Aquí podrías implementar selección de archivo
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Haz clic para seleccionar un archivo CSV\no arrastra y suelta aquí",
                        textAlign = TextAlign.Center
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 14.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // Botón de confirmación
                    Button(
                        onClick = {}
                    ) {
                        Text(text = "Guardar")
                    }

                    Spacer(Modifier.padding(10.dp))
                    // Botón de cancelación
                    Button(
                        onClick = { Cancelar() }
                    ) {
                        Text(text = "Cancelar")
                    }
                }
            }
        }

    }

}

//Funcion para crear un nuevo usuario
@Composable
fun NuevoUsuario(
    Cancelar: () -> Unit,
) {
    // Estados locales para los campos del formulario
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }

    // Estados para los checkboxes
    var usuarioActivo by remember { mutableStateOf(false) }
    var usuarioBloqueado by remember { mutableStateOf(false) }

    // Diálogo para crear un nuevo usuario
    Dialog(
        onDismissRequest = { Cancelar() },    // Si pulsa fuera del dialog
        properties = DialogProperties(usePlatformDefaultWidth = false)      // para que el fondo oscurecido no sea tan brusco
    ) {
        // El dialog centra automáticamente la carta
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)  // 90% ancho
                .fillMaxHeight(0.8f),

            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            // La card no tiene para alinear la columna, es por ello que la aliniamos manualmente con aling
            Column(
                modifier = Modifier
                    .fillMaxSize()         // Que ocupe toda la carta
                    .padding(16.dp),   // Un poco de margen para que no toque los bordes

                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
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

                // Campo: Email
                Text("Email*", style = typography.bodyMedium, fontWeight = FontWeight.Bold)
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("ejemplo@gmail.com") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Campo: Teléfono
                Text(
                    "Telefono *",
                    style = typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                OutlinedTextField(
                    value = telefono,
                    onValueChange = { telefono = it },
                    label = { Text("+34 600 333 999") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Campo: Rol (usa un desplegable)
                Text("Rol *", style = typography.bodyMedium, fontWeight = FontWeight.Bold)
                Desplegable()

                // Campo: Fecha de alta
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

                // Checkbox: Usuario activo
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

                // Checkbox: Usuario bloqueado
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // Botón de confirmación
                    Button(
                        onClick = {}
                    ) {
                        Text(text = "Guardar")
                    }

                    Spacer(Modifier.padding(10.dp))
                    // Botón de cancelación
                    Button(
                        onClick = { Cancelar() }
                    ) {
                        Text(text = "Cancelar")
                    }
                }
            }
        }

    }
}


@Composable
fun Desplegable() {
    var expandir by remember { mutableStateOf(false) } // Controla si el menú está abierto
    val opciones = listOf("Usuario", "Administrador") // Opciones disponibles
    var seleccion by remember { mutableStateOf(opciones[0]) } // Opción seleccionada

    Column {
        // Fila que muestra la opción seleccionada y el icono de desplegar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expandir = true } // Al hacer clic abre el menú
                .padding(8.dp)
        ) {
            Text(text = seleccion, modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Desplegar")
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
                        seleccion = opcion // Actualiza la selección
                        expandir = false   // Cierra el menú
                    }
                )
            }
        }
    }
}

@Composable
fun DesplegableOrdenarPor() {
    var expandir by remember { mutableStateOf(false) } // Controla si el menú está abierto
    val opciones = listOf("Nombre A-Z", "Nombre Z-A", "FechaAlta(Reciente)", "FechaAlta(Antigua)")
    var seleccion by remember { mutableStateOf(opciones[0]) } // Opción seleccionada

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expandir = true } // Abre el menú
                .padding(8.dp)
        ) {
            Text(text = seleccion, modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Desplegar")
        }

        // Menú desplegable con opciones de ordenación
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
fun BuscarTexto() {
    var textoIntroducido by remember { mutableStateOf("") }

    OutlinedTextField(
        value = textoIntroducido,
        onValueChange = { textoIntroducido = it },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        placeholder = {
            Text(
                text = "Buscar usuario...",
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
        //Colores del textfield
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = Color.LightGray
        )
    )
}

//Funcion del boton filtrar
@Composable
fun BotonFiltrar(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(50.dp)
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        //Icono para filtrar
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

//Tarjeta aplicada a cada usuario
@Composable
fun Tarjeta(usuario: ModeloUsuarios) {
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

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // FOTO DE PERFIL
                Image(
                    painter = painterResource(id = R.drawable.perfilusuario),
                    contentDescription = "foto perfil usuario",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(60.dp)
                )

                Column() {
                    // NOMBRE DE USUARIO
                    Text(
                        text = usuario.nombreCompleto,
                        style = typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    )

                    // CORREO ELECTRÓNICO
                    Text(
                        text = usuario.correoElectronico,
                        style = typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                }
            }





            Spacer(modifier = Modifier.height(8.dp))

            // Fila clickable para expandir y contraer
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expandir = !expandir },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (expandir) "Ocultar acciones" else "Mostrar acciones",
                    style = typography.bodyMedium,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (expandir) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expandir/Contraer"
                )
            }
            //Condicion si se expande muestra todos los botones de opciones
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
    }
}

//Funcion para los filtros
@Composable
fun Filtros(
    Cancelar: () -> Unit,
    Aplicar: () -> Unit
) {
    var desdeFecha by remember { mutableStateOf("") }
    var hastaFecha by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = { Cancelar() },    // Si pulsa fuera del dialog
        properties = DialogProperties(usePlatformDefaultWidth = false)      // para que el fondo oscurecido no sea tan brusco
    ) {
        // El dialog centra automáticamente la carta
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)  // 90% ancho
                .fillMaxHeight(0.5f),

            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()         // Que ocupe toda la carta
                    .padding(16.dp),   // Un poco de margen para que no toque los bordes

                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Fecha Alta Desde*",
                    style = typography.bodyMedium,
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
                    style = typography.bodyMedium,
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
                    style = typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                DesplegableEstado()

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "Rol *",
                    style = typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                DesplegableOrdenarPor()

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 14.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // Botón de confirmación
                    Button(
                        onClick = {}
                    ) {
                        Text(text = "Guardar")
                    }

                    Spacer(Modifier.padding(10.dp))
                    // Botón de cancelación
                    Button(
                        onClick = { Cancelar() }
                    ) {
                        Text(text = "Cancelar")
                    }
                }
            }

        }
    }
}


@Preview
@Composable
fun UsuariosPreview() {
    Usuarios()
}

@Preview
@Composable
fun NuevoUsuarioPreview() {
    NuevoUsuario() { }
}

@Preview
@Composable
fun CargaMasivaPreview() {
    CargaMasiva({}, {})
}

@Preview
@Composable
fun FiltrosPreview() {
    Filtros(
        Cancelar = {},
        Aplicar = {}
    )
}

