@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.example.webterminalnative

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainScaffold(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(),
    onLogout: () -> Unit
) {
    var selectedItem by remember { mutableStateOf("home") }

    val items = listOf(
        NavItem("home", "Home", Icons.Default.Home),
        NavItem("terminal", "Home", Icons.Default.Home),
        NavItem("logout", "Logout", Icons.Default.ExitToApp)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("WebHome Native") }
            )
        },
        bottomBar = {
            NavigationBar {
                items.forEach { item ->
                    NavigationBarItem(
                        selected = selectedItem == item.route,
                        onClick = {
                            selectedItem = item.route
                            when (item.route) {
                                "home" -> { /* Already here */ }
                                "terminal" -> { /* TODO: Launch terminal screen */ }
                                "logout" -> {
                                    authViewModel.logout {
                                        onLogout()
                                        navController.navigate("login") {
                                            popUpTo("main") { inclusive = true }
                                        }
                                    }
                                }
                            }
                        },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        when (selectedItem) {
            "home" -> {
                Text(
                    "Welcome to WebHome Native!",
                    modifier = Modifier.padding(innerPadding)
                )
            }
            "terminal" -> {
                Text(
                    "TODO: Home Emulator Screen",
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

data class NavItem(
    val route: String,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)
