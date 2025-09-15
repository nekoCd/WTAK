package com.example.webterminalnative

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Setup2FAScreen(
    secretKey: String,
    onConfirm: (String) -> Unit,
    onBack: () -> Unit
) {
    var code by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Set Up Two-Factor Authentication", style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(12.dp))

                Text("Scan this secret key with your authenticator app:")
                Spacer(Modifier.height(8.dp))
                Text(secretKey, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary)

                Spacer(Modifier.height(20.dp))

                OutlinedTextField(
                    value = code,
                    onValueChange = { code = it },
                    label = { Text("Enter code from app") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = { onConfirm(code) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Confirm 2FA")
                }

                Spacer(Modifier.height(12.dp))

                TextButton(onClick = { onBack() }) {
                    Text("Back")
                }
            }
        }
    }
}
