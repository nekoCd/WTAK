package com.WTAK.WebTerm

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") { HomeScreen(navController) }
        composable("becomeAdmin") { BecomeAdminScreen(navController) }
        composable("admin") { AdminDashboard() }
        composable("admin2fa") { Admin2FASetupScreen(secretKey = "SECRET") { code ->
            // Handle confirm code
        } onBack@{
            navController.popBackStack()
        } }
    }
}
