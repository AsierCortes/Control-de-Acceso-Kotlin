package com.example.controldeaccesokotlin.Vistas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

data class Usuario(
    val nombre: String,
    val email: String
)

@Composable
fun Usuarios() {
    var inputText by remember { mutableStateOf("") }

    val usuarios = listOf(
        Usuario("Bruno Linares", "bruno@gmail.com"),
        Usuario("Ana Pérez", "ana@gmail.com"),
        Usuario("Carlos Ruiz", "carlos@gmail.com"),
        Usuario("Lucía Gómez", "lucia@gmail.com"),
        Usuario("Miguel Torres", "miguel@gmail.com"),
        Usuario("Sofía Martínez", "sofia@gmail.com"),
        Usuario("David Fernández", "david@gmail.com"),
        Usuario("Laura Sánchez", "laura@gmail.com"),
        Usuario("Javier Morales", "javier@gmail.com"),
        Usuario("Elena Navarro", "elena@gmail.com")
    )

    val usuariosFiltrados = usuarios.filter {
        it.nombre.contains(inputText, ignoreCase = true) ||
                it.email.contains(inputText, ignoreCase = true)
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
                onClick = { /* Acción para crear nuevo usuario */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))
            ) {
                Text("Nuevo Usuario")
            }

            Button(
                onClick = { /* Acción para carga masiva */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text("Carga Masiva (CSV)")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        BuscarTexto(
            query = inputText,
            onQueryChange = { newQuery -> inputText = newQuery },
            onSearchClick = { /* Acción de búsqueda remota si quieres */ }
        )

        Spacer(modifier = Modifier.height(16.dp))

        BotonFiltrar(onClick = { /* Acción de filtrado */ })

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
}

@Composable
fun BuscarTexto(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearchClick: () -> Unit
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Buscar usuarios...") },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(28.dp),
        singleLine = true,
        trailingIcon = {
            IconButton(onClick = onSearchClick) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar"
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            cursorColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun BotonFiltrar(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(48.dp)
            .padding(horizontal = 8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)), // Azul original
        shape = RoundedCornerShape(24.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.AddCircle,  // Ícono integrado de Material Icons (no requiere drawable personalizado)
            contentDescription = "Filtrar",
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Filtros",
            color = Color.White,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun Tarjeta(usuario: Usuario) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        Column(
            Modifier.fillMaxSize().padding(8.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Usuario: ${usuario.nombre}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = usuario.email,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}