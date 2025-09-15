package com.example.webterminalnative

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun VerifiedScreen(
    message: String,
    email: String? = null,
    onGoToLogin: () -> Unit,
    onResendVerification: (String) -> Unit
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
                Text("Account Verification", style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(12.dp))

                Text(
                    message,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(20.dp))

                when (message) {
                    "Your account has been successfully verified. You can now log in." -> {
                        Button(
                            onClick = { onGoToLogin() },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Go to Login")
                        }
                    }

                    "Invalid or expired verification token." -> {
                        Button(
                            onClick = { email?.let { onResendVerification(it) } },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.tertiary
                            )
                        ) {
                            Text("Resend Verification Email")
                        }
                    }
                }
            }
        }
    }
}
