package com.example.webterminalnative

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class AuthUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val info: String? = null,
    val qrCodeUrl: String? = null,
    val secret: String? = null
)

class AuthRepository {
    suspend fun login(username: String, password: String): Result<Boolean> {
        delay(500)
        return if (username == "admin") Result.success(false) else Result.success(true)
    }
    suspend fun needsAdmin2FA(username: String): Boolean {
        delay(200)
        return username == "admin"
    }
    suspend fun getAdmin2FASetup(): Pair<String, String> {
        delay(200)
        return "https://api.qrserver.com/v1/create-qr-code/?size=180x180&data=otpauth://totp/WebTerm:admin?secret=ABC123&issuer=WebTerm" to "ABC123"
    }
    suspend fun verifyAdminToken(token: String): Boolean {
        delay(400)
        return token == "123456"
    }
    suspend fun register(username: String, email: String, password: String): Result<Boolean> {
        delay(700)
        return Result.success(true)
    }
    suspend fun requestReset(email: String): Result<Boolean> {
        delay(500)
        return Result.success(true)
    }
    fun logout() { }
}

class AuthViewModel(
    private val repo: AuthRepository = AuthRepository()
) : ViewModel() {
    var uiState = AuthUiState()
        private set
    private fun setState(reducer: (AuthUiState) -> AuthUiState) {
        uiState = reducer(uiState)
    }

    fun login(username: String, password: String, nav: NavHostController) {
        viewModelScope.launch {
            setState { it.copy(loading = true, error = null, info = null) }
            val needs2FA = repo.needsAdmin2FA(username)
            val result = repo.login(username, password)
            if (result.isSuccess) {
                if (needs2FA) {
                    val (qr, secret) = repo.getAdmin2FASetup()
                    setState { it.copy(loading = false, qrCodeUrl = qr, secret = secret) }
                    nav.navigate(Routes.Admin2FA)
                } else {
                    setState { it.copy(loading = false) }
                    nav.navigate(Routes.Home) { popUpTo(Routes.Login) { inclusive = true } }
                }
            } else {
                setState { it.copy(loading = false, error = "Login failed") }
            }
        }
    }

    fun verifyAdmin2FA(token: String, nav: NavHostController) {
        viewModelScope.launch {
            setState { it.copy(loading = true, error = null) }
            val ok = repo.verifyAdminToken(token)
            if (ok) {
                setState { it.copy(loading = false, qrCodeUrl = null, secret = null) }
                nav.navigate(Routes.Home) { popUpTo(Routes.Login) { inclusive = true } }
            } else {
                setState { it.copy(loading = false, error = "Invalid token") }
            }
        }
    }

    fun register(username: String, email: String, password: String, nav: NavHostController, under13: Boolean = false) {
        viewModelScope.launch {
            setState { it.copy(loading = true, error = null) }
            val result = repo.register(username, email, password)
            if (result.isSuccess) {
                setState { it.copy(loading = false, info = "Registered. Please check your email.") }
                nav.navigate("${Routes.CheckEmail}/$email/$under13")
            } else {
                setState { it.copy(loading = false, error = "Registration failed") }
            }
        }
    }

    fun requestReset(email: String, nav: NavHostController) {
        viewModelScope.launch {
            setState { it.copy(loading = true, error = null, info = null) }
            val result = repo.requestReset(email)
            if (result.isSuccess) {
                setState { it.copy(loading = false, info = "Reset email sent") }
                nav.navigate("${Routes.CheckEmail}/$email/false")
            } else {
                setState { it.copy(loading = false, error = "Reset failed") }
            }
        }
    }

    fun logout() {
        repo.logout()
        setState { AuthUiState() }
    }
}
