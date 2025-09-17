package com.WTAK.WebTerm

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.WTAK.WebTerm.ui.theme.WTAKTheme

class MainActivity : ComponentActivity() {

    private lateinit var appNavController: androidx.navigation.NavHostController

    // Launcher for requesting storage permission on Android 10 and below
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.values.all { it }
            if (granted) {
                Toast.makeText(this, "Storage permission granted", Toast.LENGTH_SHORT).show()
                navigateToTerminal()
            } else {
                Toast.makeText(this, "Storage permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance = applicationContext

        setContent {
            WTAKTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    appNavController = navController
                    NavGraph(navController = navController)
                }
            }
        }

        // Auto-check storage permission
        if (!checkStoragePermission()) {
            requestStoragePermissionCompat()
        } else {
            navigateToTerminal()
        }
    }

    private fun requestStoragePermissionCompat() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            // Android 11+ handled by PermissionsHelper
            requestStoragePermission()
        } else {
            // Android 10 and below → runtime popup
            requestPermissionLauncher.launch(
                arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            )
        }
    }

    private fun navigateToTerminal() {
        // Make sure navController is initialized
        if (::appNavController.isInitialized) {
            appNavController.navigate("terminal") {
                popUpTo("login") { inclusive = true }
                popUpTo("register") { inclusive = true }
            }
        }
    }
}
