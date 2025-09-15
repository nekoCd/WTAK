package com.example.webterminalnative

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TermsOfServiceScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Terms of Service") },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text("Back") }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            Text("Terms of Service", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(12.dp))
            Text(
                "By using this app, you agree to these terms. Please read them carefully.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(20.dp))
            SectionHeader("Use of the service")
            Bullet("You must follow all policies made available within the app.")
            Bullet("Do not misuse the services or attempt to access them using unauthorized methods.")

            Spacer(Modifier.height(16.dp))
            SectionHeader("Account")
            Bullet("You are responsible for the activity that happens on or through your account.")
            Bullet("Keep your password confidential and secure.")

            Spacer(Modifier.height(16.dp))
            SectionHeader("Content")
            Bullet("You retain ownership of the content you upload.")
            Bullet("You grant us a license to host, store, and process content as needed to provide the service.")

            Spacer(Modifier.height(16.dp))
            SectionHeader("Termination")
            Text(
                "We may suspend or stop providing services if you do not comply with terms or if we are investigating suspected misconduct.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(16.dp))
            SectionHeader("Disclaimers")
            Text(
                "The services are provided 'as is' without warranties of any kind to the fullest extent permitted by law.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(16.dp))
            SectionHeader("Limitation of liability")
            Text(
                "To the extent permitted by law, we are not liable for lost profits, revenues, or data; financial losses; or indirect, special, consequential, exemplary, or punitive damages.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(16.dp))
            SectionHeader("Changes")
            Text(
                "We may modify these terms. Changes will be effective when posted in the app.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(16.dp))
            SectionHeader("Contact")
            Text(
                "For questions about these terms, contact support.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SectionHeader(text: String) {
    Text(text, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold))
    Spacer(Modifier.height(6.dp))
}

@Composable
private fun Bullet(text: String) {
    Row(Modifier.fillMaxWidth()) {
        Text("•  ", style = MaterialTheme.typography.bodyMedium)
        Text(text, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
    }
    Spacer(Modifier.height(6.dp))
}
