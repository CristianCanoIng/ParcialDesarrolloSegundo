package com.example.parcial.Items.Screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.carnet.Items.*
import com.example.parcial.Items.ScreenCarrito
import com.example.parcial.Items.ScreenCatalogo
import com.example.parcial.Items.ScreenDetalle
import com.example.parcial.Items.ScreenRegistro

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val productos = remember { mutableStateListOf<Producto>() }
    val carrito = remember { mutableStateListOf<Producto>() }

    NavHost(navController = navController, startDestination = "catalogo") {
        composable("catalogo") {
            ScreenCatalogo(navController, productos, carrito)
        }
        composable("registro") {
            ScreenRegistro(navController, productos)
        }
        composable("detalle/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            ScreenDetalle(navController, productos, carrito, id)
        }
        composable("carrito") {
            ScreenCarrito(navController, carrito)
        }
    }
}
