package com.WTAK.WebTerm

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TermsOfServiceScreen(
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Terms of Service", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        Text(
            "This is where your terms of service text will go. " +
                    "Explain the rules and conditions for using this app."
        )

        Spacer(Modifier.height(20.dp))

        Button(onClick = { onBack() }, modifier = Modifier.fillMaxWidth()) {
            Text("Back")
        }
    }
}
