package com.WTAK.WebTerm

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun AdminDashboard() {
    var users by remember { mutableStateOf<List<User>>(emptyList()) }
    var message by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        loadUsers { fetchedUsers ->
            users = fetchedUsers
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Admin Dashboard", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(message, color = MaterialTheme.colorScheme.error)

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(users) { user ->
                UserRow(user, onBan = {
                    AdminActions.banUser(user.id) { success, msg ->
                        message = msg
                        if (success) loadUsers { fetched -> users = fetched }
                    }
                }, onUnban = {
                    AdminActions.unbanUser(user.id) { success, msg ->
                        message = msg
                        if (success) loadUsers { fetched -> users = fetched }
                    }
                })
            }
        }
    }
}

@Composable
fun UserRow(user: User, onBan: () -> Unit, onUnban: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Column {
            Text("ID: ${user.id}")
            Text("Username: ${user.username}")
            Text("Role: ${user.role}")
        }
        Row {
            Button(onClick = onBan, modifier = Modifier.padding(end = 8.dp)) {
                Text("Ban")
            }
            Button(onClick = onUnban) {
                Text("Unban")
            }
        }
    }
}

fun loadUsers(onResult: (List<User>) -> Unit) {
    val call = RetrofitClient.adminApi.getUsers()
    call.enqueue(object : Callback<List<User>> {
        override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
            if (response.isSuccessful) {
                onResult(response.body() ?: emptyList())
            } else {
                onResult(emptyList())
            }
        }

        override fun onFailure(call: Call<List<User>>, t: Throwable) {
            onResult(emptyList())
        }
    })
}
