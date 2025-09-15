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
fun PrivacyPolicyScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Privacy Policy") },
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
            Text("Privacy Policy", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(12.dp))
            Text(
                "This Privacy Policy describes how we collect, use, and protect your information.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(20.dp))
            SectionHeader("Information we collect")
            Bullet("Account data (e.g., username, email).")
            Bullet("Usage data (e.g., interactions, diagnostics).")
            Bullet("Device data (e.g., OS version, device model).")

            Spacer(Modifier.height(16.dp))
            SectionHeader("How we use information")
            Bullet("To provide and improve services.")
            Bullet("To secure accounts and prevent abuse.")
            Bullet("To communicate updates and support.")

            Spacer(Modifier.height(16.dp))
            SectionHeader("Sharing and disclosure")
            Bullet("We do not sell personal data.")
            Bullet("We share data with service providers under contract.")
            Bullet("We may disclose when required by law.")

            Spacer(Modifier.height(16.dp))
            SectionHeader("Data retention")
            Text(
                "We retain data for as long as necessary to provide services and comply with legal obligations.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(16.dp))
            SectionHeader("Your rights")
            Bullet("Access, correction, deletion where applicable.")
            Bullet("Opt-out of certain processing where available.")

            Spacer(Modifier.height(16.dp))
            SectionHeader("Security")
            Text(
                "We use technical and organizational measures to protect your data.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(16.dp))
            SectionHeader("Contact")
            Text(
                "For privacy inquiries, contact support.",
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
