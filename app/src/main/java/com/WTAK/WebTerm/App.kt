package com.WTAK.WebTerm

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@Composable
fun App() {
    val navController = rememberNavController()

    WTAKTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            NavGraph(navController = navController)
        }
    }
}
