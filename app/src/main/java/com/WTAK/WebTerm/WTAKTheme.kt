package com.WTAK.WebTerm

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun WTAKTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(),
        typography = Typography(),
        content = content
    )
}
