package com.WTAK.WebTerm

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun VerifyPinScreen(
    username: String,
    error: String? = null,
    onVerify: (String) -> Unit,
    onCancel: () -> Unit
) {
    var pin by remember { mutableStateOf("") }

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
                Text("Verify Admin PIN", style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(12.dp))

                Text("Please enter the PIN to access $username's folder.")
                Spacer(Modifier.height(12.dp))

                if (error != null) {
                    Text(error, color = MaterialTheme.colorScheme.error)
                    Spacer(Modifier.height(8.dp))
                }

                OutlinedTextField(
                    value = pin,
                    onValueChange = { pin = it },
                    label = { Text("PIN") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = { onVerify(pin) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Unlock Folder")
                }

                Spacer(Modifier.height(12.dp))

                TextButton(onClick = { onCancel() }) {
                    Text("Cancel")
                }
            }
        }
    }
}
