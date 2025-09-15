package com.WTAK.WebTerm

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    var parentalEmail by remember { mutableStateOf("") }

    // DOB dropdown
    var selectedDay by remember { mutableStateOf("Day") }
    var selectedMonth by remember { mutableStateOf("Month") }
    var selectedYear by remember { mutableStateOf("Year") }

    var errorMessage by remember { mutableStateOf<String?>(null) }

    val activity = LocalContext.current as Activity

    // Google Sign-in setup
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(activity.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()
    val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(activity, gso)
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

    // Helper: calculate age
    fun calculateAge(day: Int, month: Int, year: Int): Int {
        val today = java.util.Calendar.getInstance()
        val birth = java.util.Calendar.getInstance().apply { set(year, month - 1, day) }
        var age = today.get(java.util.Calendar.YEAR) - birth.get(java.util.Calendar.YEAR)
        if (today.get(java.util.Calendar.DAY_OF_YEAR) < birth.get(java.util.Calendar.DAY_OF_YEAR)) age--
        return age
    }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(username, { username = it }, label = { Text("Username") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(firstName, { firstName = it }, label = { Text("First Name") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(lastName, { lastName = it }, label = { Text("Last Name") }, modifier = Modifier.fillMaxWidth())

        // DOB dropdowns
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            DropdownMenuBox("Day", (1..31).map { it.toString() }, selectedDay) { selectedDay = it }
            DropdownMenuBox("Month", (1..12).map { it.toString() }, selectedMonth) { selectedMonth = it }
            DropdownMenuBox("Year", (2005..2025).map { it.toString() }, selectedYear) { selectedYear = it }
        }

        val age = if (selectedDay != "Day" && selectedMonth != "Month" && selectedYear != "Year")
            calculateAge(selectedDay.toInt(), selectedMonth.toInt(), selectedYear.toInt()) else 100

        if (age < 13) {
            OutlinedTextField(parentalEmail, { parentalEmail = it }, label = { Text("Parental Email") }, modifier = Modifier.fillMaxWidth())
        }

        OutlinedTextField(email, { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(password, { password = it }, label = { Text("Password") }, modifier = Modifier.fillMaxWidth(), visualTransformation = PasswordVisualTransformation())

        PasswordStrengthBar(password)

        if (errorMessage != null) {
            Text(errorMessage ?: "", color = MaterialTheme.colorScheme.error)
        }

        Button(
            onClick = {
                when {
                    email.isBlank() -> errorMessage = "Email cannot be empty"
                    password.isBlank() -> errorMessage = "Password cannot be empty"
                    password.length < 6 -> errorMessage = "Password must be at least 6 characters"
                    age < 13 && parentalEmail.isBlank() -> errorMessage = "Parental email required for users under 13"
                    else -> {
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    navController.navigate("main") { popUpTo("register") { inclusive = true } }
                                } else {
                                    errorMessage = task.exception?.localizedMessage ?: "Registration failed"
                                }
                            }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) { Text("Register with Email") }

        Button(
            onClick = { launcher.launch(googleSignInClient.signInIntent) },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) { Text("Register with Google") }

        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = { navController.navigate("login") }) { Text("Already have an account? Login") }
    }
}

@Composable
fun PasswordStrengthBar(password: String) {
    val strength = when {
        password.length < 6 -> 0f
        password.matches(Regex("^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%^&*]).*$")) -> 1f
        password.matches(Regex("^(?=.*[A-Z])(?=.*[0-9]).*$")) -> 0.7f
        else -> 0.4f
    }

    val color = when {
        strength <= 0.4f -> Color.Red
        strength <= 0.7f -> Color.Yellow
        else -> Color.Green
    }

    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(Color.LightGray)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(strength)
                    .fillMaxHeight()
                    .background(color)
            )
        }
        Text(
            text = when {
                strength <= 0.4f -> "Weak"
                strength <= 0.7f -> "Medium"
                else -> "Strong"
            },
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}

@Composable
fun DropdownMenuBox(label: String, options: List<String>, selected: String, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            modifier = Modifier.fillMaxWidth().clickable { expanded = true }
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(onClick = { onSelect(option); expanded = false }) {
                    Text(option)
                }
            }
        }
    }
}
