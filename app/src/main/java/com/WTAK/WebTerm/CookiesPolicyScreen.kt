package com.WTAK.WebTerm

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CookiesPolicyScreen(
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Cookies Policy", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        Text(
            "This is where your cookies policy text will go. " +
                    "Explain how cookies are used for session management and analytics."
        )

        Spacer(Modifier.height(20.dp))

        Button(onClick = { onBack() }, modifier = Modifier.fillMaxWidth()) {
            Text("Back")
        }
    }
}
