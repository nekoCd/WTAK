package com.example.webterminalnative

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

enum class TwoFactorMethod { AUTHENTICATOR, EMAIL, WEBAUTHN }

@Composable
fun TwoFAScreen(
    method: TwoFactorMethod,
    errorMessage: String? = null,
    onVerifyCode: (String) -> Unit,
    onResendEmail: () -> Unit = {},
    onWebAuthnLogin: () -> Unit = {}
) {
    var code by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Two-Factor Authentication", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))

        if (!errorMessage.isNullOrBlank()) {
            Text(errorMessage, color = MaterialTheme.colorScheme.error)
            Spacer(Modifier.height(8.dp))
        }

        when (method) {
            TwoFactorMethod.AUTHENTICATOR -> {
                Text("Enter the code from your authenticator app.")
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = code,
                    onValueChange = { code = it },
                    label = { Text("6-digit code") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = { onVerifyCode(code) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Verify Code")
                }
            }
            TwoFactorMethod.EMAIL -> {
                Text("A one-time code has been sent to your email.")
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = code,
                    onValueChange = { code = it },
                    label = { Text("Code from email") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = { onVerifyCode(code) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Verify Code")
                }
                TextButton(onClick = onResendEmail, modifier = Modifier.fillMaxWidth()) {
                    Text("Resend code")
                }
            }
            TwoFactorMethod.WEBAUTHN -> {
                Text("Login with Fingerprint / FaceID")
                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = onWebAuthnLogin,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Login with Fingerprint / FaceID")
                }
            }
        }
    }
}
