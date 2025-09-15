package com.example.webterminalnative

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

enum class TwoFAMethod { APP, EMAIL }

@Composable
fun Setup2FAScreen(
    qrCodeImage: String?,
    secret: String,
    errorMessage: String? = null,
    onVerifyAppCode: (token: String) -> Unit,
    onEnableEmail2FA: () -> Unit,
    onBack: () -> Unit
) {
    var selectedMethod by remember { mutableStateOf(TwoFAMethod.APP) }
    var token by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Setup Two-Factor Authentication") },
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
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Choose your preferred method of two-factor authentication.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(16.dp))
            TabRow(selectedTabIndex = selectedMethod.ordinal) {
                Tab(
                    selected = selectedMethod == TwoFAMethod.APP,
                    onClick = { selectedMethod = TwoFAMethod.APP },
                    text = { Text("Authenticator App") }
                )
                Tab(
                    selected = selectedMethod == TwoFAMethod.EMAIL,
                    onClick = { selectedMethod = TwoFAMethod.EMAIL },
                    text = { Text("Email") }
                )
            }

            Spacer(Modifier.height(16.dp))
            if (!errorMessage.isNullOrBlank()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(12.dp))
            }

            when (selectedMethod) {
                TwoFAMethod.APP -> {
                    Text(
                        "Scan the QR code below with an authenticator app (like Google Authenticator or Authy) to add this account.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(Modifier.height(12.dp))
                    if (qrCodeImage != null) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(qrCodeImage),
                                contentDescription = "QR Code",
                                modifier = Modifier
                                    .size(180.dp)
                                    .padding(16.dp),
                                contentScale = ContentScale.Fit
                            )
                        }
                    } else {
                        Text("No QR code available. Please try again.")
                    }
                    Spacer(Modifier.height(12.dp))
                    Text("Alternatively, manually enter this secret key into your app:")
                    Spacer(Modifier.height(4.dp))
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Text(
                            secret,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                    Spacer(Modifier.height(12.dp))
                    Text("Once you've added the account, enter the 6-digit code to confirm and activate 2FA.")
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = token,
                        onValueChange = { token = it },
                        label = { Text("Authenticator Code") },
                        singleLine = true,
                        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(20.dp))
                    Button(
                        onClick = { onVerifyAppCode(token) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Enable 2FA")
                    }
                }
                TwoFAMethod.EMAIL -> {
                    Text(
                        "Receive a unique 6-digit code via your registered email address every time you log in.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(Modifier.height(20.dp))
                    Button(
                        onClick = onEnableEmail2FA,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Enable Email 2FA")
                    }
                }
            }
        }
    }
}
