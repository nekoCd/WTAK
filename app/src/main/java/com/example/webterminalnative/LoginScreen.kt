package com.example.webterminalnative

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel = viewModel(),
    onLoginSuccess: () -> Unit,
    onNavigateRegister: () -> Unit,
    onForgotPassword: () -> Unit,
    onGoogleSignIn: () -> Unit,
    onPrivacyPolicy: () -> Unit = {},
    onTermsOfService: () -> Unit = {},
    onCookiesPolicy: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var agreeTos by remember { mutableStateOf(false) }

    val isLoading by authViewModel.isLoading.collectAsState()
    val errorMessage by authViewModel.errorMessage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Login", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Checkbox(checked = agreeTos, onCheckedChange = { agreeTos = it })
            Text("I agree to the Terms of Service")
        }

        if (errorMessage != null) {
            Text(errorMessage ?: "", color = Color.Red)
        }

        Button(
            onClick = {
                authViewModel.login(
                    email, password,
                    onSuccess = onLoginSuccess,
                    onFailure = {}
                )
            },
            enabled = !isLoading && agreeTos,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Spacer(Modifier.height(16.dp))
        Divider()
        Spacer(Modifier.height(12.dp))
        Text("OR", style = MaterialTheme.typography.labelMedium)
        Spacer(Modifier.height(12.dp))

        Button(
            onClick = { onGoogleSignIn() },
            enabled = agreeTos,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            )
        ) {
            Text("Login with Google")
        }

        Spacer(Modifier.height(12.dp))
        TextButton(onClick = { onForgotPassword() }) {
            Text("Forgot your password?")
        }
        TextButton(onClick = { onNavigateRegister() }) {
            Text("Create an account")
        }

        Spacer(Modifier.height(20.dp))

        TextButton(onClick = { onPrivacyPolicy() }) { Text("Privacy Policy") }
        TextButton(onClick = { onTermsOfService() }) { Text("Terms of Service") }
        TextButton(onClick = { onCookiesPolicy() }) { Text("Cookies Policy") }
    }
}
