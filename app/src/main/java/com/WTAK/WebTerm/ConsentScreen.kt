package com.WTAK.WebTerm

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ConsentScreen(
    username: String,
    consentId: String,
    onSubmitConsent: (String) -> Unit,
    onBack: () -> Unit
) {
    var parentName by remember { mutableStateOf("") }
    var consentGiven by remember { mutableStateOf(false) }

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
                Text("Parental Consent", style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(12.dp))

                Text("User $username requires parental consent to activate their account.")
                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = parentName,
                    onValueChange = { parentName = it },
                    label = { Text("Parent/Guardian Full Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = consentGiven, onCheckedChange = { consentGiven = it })
                    Text("I consent to the creation of this account.")
                }

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = { if (consentGiven) onSubmitConsent(parentName) },
                    enabled = consentGiven,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Submit Consent")
                }

                Spacer(Modifier.height(12.dp))

                TextButton(onClick = { onBack() }) {
                    Text("Back")
                }
            }
        }
    }
}
