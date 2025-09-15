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
fun CookiesPolicyScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cookies Policy") },
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
            Text("Cookies Policy", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(12.dp))
            Text(
                "This Cookies Policy explains what cookies are, how we use them, and your choices about cookies.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(20.dp))
            SectionHeader("What are cookies?")
            Text(
                "Cookies are small text files stored on your device to remember information about your visit.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(16.dp))
            SectionHeader("How we use cookies")
            Bullet("Essential cookies for security and basic functionality.")
            Bullet("Analytics cookies to understand usage and improve performance.")
            Bullet("Preference cookies to remember your settings.")

            Spacer(Modifier.height(16.dp))
            SectionHeader("Your choices")
            Bullet("You can disable cookies in your browser settings.")
            Bullet("You can opt out of analytics where available.")

            Spacer(Modifier.height(16.dp))
            SectionHeader("Contact")
            Text(
                "If you have questions about this Cookies Policy, contact support.",
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
