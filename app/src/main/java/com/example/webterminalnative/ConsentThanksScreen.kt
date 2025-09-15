package com.example.webterminalnative

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ConsentThanksScreen(
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
                Text("Consent Submitted", style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(12.dp))

                Text(
                    "Thank you! The parental consent for $username has been recorded. " +
                            "The account will now be reviewed and activated.",
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = { onBackToLogin() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Back to Login")
                }
            }
        }
    }
}
