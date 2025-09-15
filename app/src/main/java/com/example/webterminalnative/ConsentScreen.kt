package com.example.webterminalnative

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun ConsentScreen(
    firstName: String,
    lastName: String,
    username: String,
    errorMessage: String? = null,
    onSubmit: (parentName: String, parentEmail: String, signatureText: String) -> Unit
) {
    var parentName by remember { mutableStateOf("") }
    var parentEmail by remember { mutableStateOf("") }
    var signatureText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Parental Consent", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        Text("Child: $firstName $lastName (username: $username)")

        if (!errorMessage.isNullOrBlank()) {
            Spacer(Modifier.height(8.dp))
            Text(errorMessage, color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = parentName,
            onValueChange = { parentName = it },
            label = { Text("Parent / Guardian Full Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = parentEmail,
            onValueChange = { parentEmail = it },
            label = { Text("Parent / Guardian Email") },
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = signatureText,
            onValueChange = { signatureText = it },
            label = { Text("Signature (type full name as signature)") },
            placeholder = { Text("Type your full name to sign") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))
        Button(
            onClick = { onSubmit(parentName, parentEmail, signatureText) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign Consent")
        }

        Spacer(Modifier.height(12.dp))
        Text(
            "After signing, the account will be activated and created for the child. An admin can also review consents.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
