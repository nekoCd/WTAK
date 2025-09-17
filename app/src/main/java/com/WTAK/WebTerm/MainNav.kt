package com.WTAK.WebTerm

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun MainNav(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = Modifier
    ) {
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("main") { HomeScreen(navController) }
        composable("terminal") { TerminalScreen(navController) }

        // 🔑 Admin Dashboard route
        composable("adminDashboard") { AdminDashboardScreen(navController) }
    }
}
