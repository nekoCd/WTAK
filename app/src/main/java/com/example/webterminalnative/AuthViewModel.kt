package com.example.webterminalnative

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        _isLoading.value = true
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    _errorMessage.value = null
                    onSuccess()
                } else {
                    val msg = task.exception?.message ?: "Login failed"
                    _errorMessage.value = msg
                    onFailure(msg)
                }
            }
    }

    fun register(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        _isLoading.value = true
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    _errorMessage.value = null
                    onSuccess()
                } else {
                    val msg = task.exception?.message ?: "Registration failed"
                    _errorMessage.value = msg
                    onFailure(msg)
                }
            }
    }

    fun logout(onComplete: () -> Unit) {
        viewModelScope.launch {
            auth.signOut()
            _errorMessage.value = null
            onComplete()
        }
    }

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}
