package com.example.parcial.Items

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.carnet.Items.Producto

@Composable
fun ScreenCarrito(navController: NavController, carrito: List<Producto>) {
    Column(modifier = Modifier.padding(16.dp)) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(carrito) { producto ->
                Text("${producto.nombre} - $${producto.precio}")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Total a pagar: $${carrito.sumOf { it.precio }}")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navController.navigate("catalogo") }) {
            Text("Finalizar Compra")
        }
    }
}
