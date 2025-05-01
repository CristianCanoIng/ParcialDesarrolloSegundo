package com.example.parcial.Items

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.carnet.Items.Producto
import android.util.Patterns
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent

@Composable
fun ScreenRegistro(navController: NavController, productos: MutableList<Producto>) {
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var imagenUrl by remember { mutableStateOf("") }

    var nombreError by remember { mutableStateOf(false) }
    var precioError by remember { mutableStateOf(false) }
    var descripcionError by remember { mutableStateOf(false) }
    var imagenUrlError by remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf("") }
    var mostrarDialogo by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Registro de Producto",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            OutlinedTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                    nombreError = false
                },
                label = { Text("Nombre del Producto") },
                placeholder = { Text("Ej: Smartphone XYZ") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                isError = nombreError,
                leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null) },
                singleLine = true
            )

            OutlinedTextField(
                value = precio,
                onValueChange = {
                    precio = it
                    precioError = false
                },
                label = { Text("Precio") },
                placeholder = { Text("Ej: 299.99") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                isError = precioError,
                leadingIcon = { Icon(Icons.Default.Star, contentDescription = null) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal
                ),
                singleLine = true
            )

            OutlinedTextField(
                value = descripcion,
                onValueChange = {
                    descripcion = it
                    descripcionError = false
                },
                label = { Text("Descripción") },
                placeholder = { Text("Ingrese una descripción detallada") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(bottom = 16.dp),
                isError = descripcionError,
                leadingIcon = { Icon(Icons.Default.List, contentDescription = null) },
                maxLines = 5
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = imagenUrl,
                    onValueChange = {
                        imagenUrl = it
                        imagenUrlError = false
                    },
                    label = { Text("URL de Imagen") },
                    placeholder = { Text("Ej: https://example.com/imagen.jpg") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = imagenUrlError,
                    leadingIcon = { Icon(Icons.Default.AccountBox, contentDescription = null) },
                    singleLine = true
                )

                if (imagenUrl.isNotEmpty()) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            SubcomposeAsyncImage(
                                model = imagenUrl,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                val state = painter.state
                                if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (state is AsyncImagePainter.State.Loading) {
                                            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                                        } else {
                                            imagenUrlError = true
                                            errorMessage = "No se pudo cargar la imagen. Verifique la URL."
                                            Icon(
                                                imageVector = Icons.Default.Warning,
                                                contentDescription = "Error al cargar imagen",
                                                tint = MaterialTheme.colorScheme.error,
                                                modifier = Modifier.size(64.dp)
                                            )
                                        }
                                    }
                                } else {
                                    SubcomposeAsyncImageContent()
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = { navController.navigate("catalogo") },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                    Text("Cancelar")
                }

                Button(
                    onClick = {
                        val precioDouble = precio.toDoubleOrNull()

                        nombreError = nombre.isBlank()
                        precioError = precio.isBlank() || precioDouble == null || precioDouble <= 0
                        descripcionError = descripcion.isBlank()
                        imagenUrlError = imagenUrl.isBlank() || !Patterns.WEB_URL.matcher(imagenUrl).matches()

                        if (nombreError || precioError || descripcionError || imagenUrlError) {
                            errorMessage = "Por favor, corrija los campos marcados en rojo."
                        } else {
                            errorMessage = ""
                            mostrarDialogo = true
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(8.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                ) {
                    Icon(Icons.Default.Done, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                    Text("Guardar")
                }
            }
        }
    }

    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = { Text("Confirmar registro") },
            text = { Text("¿Está seguro de que desea registrar este producto?") },
            confirmButton = {
                Button(onClick = {
                    val precioDouble = precio.toDoubleOrNull() ?: 0.0
                    productos.add(
                        Producto(
                            id = productos.size + 1,
                            nombre = nombre,
                            precio = precioDouble,
                            descripcion = descripcion,
                            imagenUrl = imagenUrl
                        )
                    )
                    mostrarDialogo = false
                    navController.navigate("catalogo")
                }) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { mostrarDialogo = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
