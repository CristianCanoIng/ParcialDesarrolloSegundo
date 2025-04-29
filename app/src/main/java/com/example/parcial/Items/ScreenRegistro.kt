package com.example.parcial.Items

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.carnet.Items.Producto

@Composable
fun ScreenRegistro(navController: NavController, productos: MutableList<Producto>) {
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var imagenUrl by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
        TextField(value = precio, onValueChange = { precio = it }, label = { Text("Precio") })
        TextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") })
        TextField(value = imagenUrl, onValueChange = { imagenUrl = it }, label = { Text("URL de Imagen") })

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(onClick = { navController.navigate("catalogo") }, modifier = Modifier.weight(1f)) {
                Text("Cancelar")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (nombre.isNotBlank() && precio.isNotBlank() && descripcion.isNotBlank() && imagenUrl.isNotBlank()) {
                        productos.add(
                            Producto(
                                id = productos.size + 1,
                                nombre = nombre,
                                precio = precio.toDoubleOrNull() ?: 0.0,
                                descripcion = descripcion,
                                imagenUrl = imagenUrl
                            )
                        )
                        navController.navigate("catalogo")
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Guardar")
            }
        }
    }
}
