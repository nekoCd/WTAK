package com.example.webterminalnative

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun Under13Screen(
    username: String,
    onBackToLogin: () -> Unit
) {
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
                Text("Parental Consent Required", style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(12.dp))

                Text(
                    "Thanks — the account for $username has been created in a pending state because the user is under 13.",
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(12.dp))

                Text(
                    "To complete account activation, the parent/guardian must sign the consent form. An admin can also review pending consents in the dashboard.",
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(20.dp))

                Button(onClick = { onBackToLogin() }, modifier = Modifier.fillMaxWidth()) {
                    Text("Back to Login")
                }
            }
        }
    }
}
