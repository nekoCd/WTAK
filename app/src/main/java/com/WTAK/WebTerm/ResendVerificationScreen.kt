package com.WTAK.WebTerm

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ResendVerificationScreen(
    onBack: () -> Unit
) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var error by remember { mutableStateOf<String?>(null) }
    var success by remember { mutableStateOf(false) }

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
                Text("Resend Verification Email", style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(16.dp))

                if (error != null) {
                    Text(error!!, color = MaterialTheme.colorScheme.error)
                    Spacer(Modifier.height(8.dp))
                }

                if (success) {
                    Text("Verification email sent. Please check your inbox.", color = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.height(16.dp))
                } else {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Enter your email") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(20.dp))

                    Button(
                        onClick = {
                            if (email.text.isNotBlank()) {
                                FirebaseAuth.getInstance().currentUser?.sendEmailVerification()
                                    ?.addOnSuccessListener {
                                        success = true
                                        error = null
                                    }
                                    ?.addOnFailureListener {
                                        error = it.message ?: "Failed to send verification email"
                                    }
                            } else {
                                error = "Email cannot be empty"
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Resend Verification Email")
                    }
                }

                Spacer(Modifier.height(12.dp))

                TextButton(onClick = { onBack() }) {
                    Text("Back")
                }
            }
        }
    }
}
