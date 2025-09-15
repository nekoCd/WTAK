package com.WTAK.WebTerm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@Composable
fun AdminDashboardScreen(
    backendApi: BackendApi = BackendApi.create(),
    authViewModel: AuthViewModel = viewModel(),
    onLogout: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var users by remember { mutableStateOf(listOf<User>()) }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    // Load users on entry
    LaunchedEffect(Unit) {
        isLoading = true
        try {
            users = backendApi.getUsers()
        } catch (e: Exception) {
            error = "Failed to load users: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp)
    ) {
        Text(
            "Admin Dashboard",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
        }

        error?.let {
            Text(it, color = Color.Red)
        }

        LazyColumn {
            items(users) { user ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text("Username: ${user.username}", color = Color.White)
                        Text("Email: ${user.email}", color = Color.LightGray)
                        Text("Status: ${user.status}", color = if (user.status == "online") Color.Green else Color.Red)
                        Spacer(Modifier.height(8.dp))

                        Row {
                            if (!user.isAdmin) {
                                Button(
                                    onClick = {
                                        scope.launch {
                                            backendApi.banUser(user.username)
                                            users = backendApi.getUsers()
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                                ) {
                                    Text("Ban")
                                }

                                Spacer(Modifier.width(8.dp))

                                Button(
                                    onClick = {
                                        scope.launch {
                                            backendApi.makeAdmin(user.username)
                                            users = backendApi.getUsers()
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                                ) {
                                    Text("Make Admin")
                                }
                            } else {
                                Text("Admin", color = Color.Cyan)
                            }

                            Spacer(Modifier.width(8.dp))

                            Button(
                                onClick = {
                                    // open folder in external browser
                                    backendApi.openFolder(user.username)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A90E2))
                            ) {
                                Text("Open Folder")
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                authViewModel.logout(onLogout)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0A0))
        ) {
            Text("Logout", color = Color.White)
        }
    }
}

// ---------------------- Data & API ----------------------

data class User(
    val username: String,
    val email: String,
    val status: String,
    val isAdmin: Boolean
)

interface BackendApi {
    suspend fun getUsers(): List<User>
    suspend fun banUser(username: String)
    suspend fun makeAdmin(username: String)
    fun openFolder(username: String)

    companion object {
        fun create(): BackendApi = FakeBackendApi() // Replace with Retrofit later
    }
}

// Fake implementation for now
class FakeBackendApi : BackendApi {
    override suspend fun getUsers(): List<User> = listOf(
        User("alice", "alice@example.com", "online", false),
        User("bob", "bob@example.com", "offline", true)
    )

    override suspend fun banUser(username: String) {
        println("Banned $username")
    }

    override suspend fun makeAdmin(username: String) {
        println("Made $username admin")
    }

    override fun openFolder(username: String) {
        println("Open folder for $username")
    }
}
