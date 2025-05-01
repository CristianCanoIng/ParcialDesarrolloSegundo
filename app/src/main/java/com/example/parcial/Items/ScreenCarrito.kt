package com.example.parcial.Items

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.carnet.Items.Producto

@Composable
fun ScreenCarrito(navController: NavController, carrito: MutableList<Producto>) {
    var compraFinalizada by remember { mutableStateOf(false) }
    var productoAEliminar by remember { mutableStateOf<Producto?>(null) }
    var mostrarDialogoBorrado by remember { mutableStateOf(false) }

    if (compraFinalizada) {
        AlertDialog(
            onDismissRequest = { compraFinalizada = false },
            title = { Text("Compra Confirmada") },
            text = {
                Column {
                    Text("¡Gracias por tu compra!")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Productos:")
                    carrito.forEach {
                        Text("- ${it.nombre}: $${"%.2f".format(it.precio)}")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Total: $${"%.2f".format(carrito.sumOf { it.precio })}")
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    compraFinalizada = false
                    navController.navigate("catalogo")
                }) {
                    Text("Volver al catálogo")
                }
            }
        )
    }

    if (mostrarDialogoBorrado && productoAEliminar != null) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoBorrado = false },
            title = { Text("Confirmar Borrado") },
            text = { Text("¿Estás seguro de que deseas eliminar el producto '${productoAEliminar?.nombre}'?") },
            confirmButton = {
                TextButton(onClick = {
                    productoAEliminar?.let { carrito.remove(it) }
                    mostrarDialogoBorrado = false
                }) {
                    Text("Sí, Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoBorrado = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Tu Carrito",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(carrito) { producto ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = producto.nombre,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = producto.descripcion,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "$${"%.2f".format(producto.precio)}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            IconButton(
                                onClick = {
                                    productoAEliminar = producto
                                    mostrarDialogoBorrado = true
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Eliminar Producto",
                                    tint = Color.Red
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Total a pagar: $${"%.2f".format(carrito.sumOf { it.precio })}",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = { compraFinalizada = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = ButtonDefaults.buttonElevation(6.dp)
        ) {
            Icon(Icons.Default.ShoppingCart, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
            Text("Finalizar Compra")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = { navController.navigate("catalogo") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al catálogo")
        }
    }
}

