package com.example.webterminalnative

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainScaffold(
    title: String = "WebTerminal",
    username: String? = null,
    onLogout: (() -> Unit)? = null,
    content: @Composable (Modifier) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                actions = {
                    if (username != null && onLogout != null) {
                        TextButton(onClick = onLogout) {
                            Text("Logout ($username)")
                        }
                    }
                }
            )
        }
    ) { padding ->
        content(Modifier.padding(padding))
    }
}
