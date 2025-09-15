package com.WTAK.WebTerm

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun RegisterScreen(
    navController: NavController,
    auth: FirebaseAuth = FirebaseAuth.getInstance()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val activity = LocalContext.current as Activity

    // Setup Google Sign-In client
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(activity.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()
    val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(activity, gso)

    // Launcher to handle Google registration result
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.result
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener { authTask ->
                    if (authTask.isSuccessful) {
                        navController.navigate("main") {
                            popUpTo("register") { inclusive = true }
                        }
                    } else {
                        errorMessage = authTask.exception?.localizedMessage ?: "Google signup failed"
                    }
                }
        } catch (e: Exception) {
            errorMessage = e.localizedMessage
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Username") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = firstName, onValueChange = { firstName = it }, label = { Text("First Name") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = lastName, onValueChange = { lastName = it }, label = { Text("Last Name") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = dob, onValueChange = { dob = it }, label = { Text("Date of Birth") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, modifier = Modifier.fillMaxWidth(), visualTransformation = PasswordVisualTransformation())

        if (errorMessage != null) {
            Text(errorMessage ?: "", color = MaterialTheme.colorScheme.error)
        }

        Button(
            onClick = {
                when {
                    email.isBlank() -> errorMessage = "Email cannot be empty"
                    password.isBlank() -> errorMessage = "Password cannot be empty"
                    password.length < 6 -> errorMessage = "Password must be at least 6 characters"
                    else -> {
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    navController.navigate("main") {
                                        popUpTo("register") { inclusive = true }
                                    }
                                } else {
                                    errorMessage = task.exception?.localizedMessage ?: "Registration failed"
                                }
                            }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Register with Email")
        }

        // Google signup button
        Button(
            onClick = { launcher.launch(googleSignInClient.signInIntent) },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Register with Google")
        }

        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = { navController.navigate("login") }) {
            Text("Already have an account? Login")
        }
    }
}
