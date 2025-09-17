package com.WTAK.WebTerm

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.ceil

@Composable
fun HomeScreen(navController: NavController) {
    val user = FirebaseAuth.getInstance().currentUser
    var role by remember { mutableStateOf("user") }
    var permAdmin by remember { mutableStateOf(false) }
    var expiresAt by remember { mutableStateOf<Long?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Welcome, ${user?.email ?: "Guest"}")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("becomeAdmin") }) {
            Text("Become Admin")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (role == "admin") {
            Button(onClick = { navController.navigate("adminDashboard") }) {
                Text("Go to Admin Dashboard")
            }

            if (!permAdmin) {
                // Show countdown if temp admin
                expiresAt?.let { expiry ->
                    val now = System.currentTimeMillis()
                    if (expiry > now) {
                        val millisLeft = expiry - now
                        val daysLeft = ceil(millisLeft / (1000.0 * 60 * 60 * 24)).toInt()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("⏳ Temp admin expires in $daysLeft day(s)")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        role = "user"
                        permAdmin = false
                        expiresAt = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Revoke My Admin")
                }
            }
        }
    }
}
