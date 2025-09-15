package com.example.webterminalnative

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

object Routes {
    const val Login = "login"
    const val Register = "register"
    const val Forgot = "forgot"
    const val Admin2FA = "admin2fa"
    const val Home = "home"
    const val CheckEmail = "check_email"
    const val AdminLogin = "admin_login"
    const val Tos = "tos"
    const val Privacy = "privacy"
    const val Cookies = "cookies"
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    viewModel: AuthViewModel
) {
    NavHost(navController = navController, startDestination = Routes.Login) {

        composable(Routes.Login) {
            LoginScreen(
                onLogin = { user, pass -> viewModel.login(user, pass, navController) },
                onLoginWithGitHub = { },
                onLoginWithGoogle = { },
                onRegister = { navController.navigate(Routes.Register) },
                onForgotPassword = { navController.navigate(Routes.Forgot) },
                onOpenTos = { navController.navigate(Routes.Tos) },
                errorMessage = viewModel.uiState.error
            )
        }

        composable(Routes.Register) {
            RegisterScreen(
                onRegister = { u, e, p -> viewModel.register(u, e, p, navController) },
                onBack = { navController.popBackStack() },
                errorMessage = viewModel.uiState.error
            )
        }

        composable(Routes.Forgot) {
            ForgotPasswordScreen(
                onRequestReset = { email -> viewModel.requestReset(email, navController) },
                onBack = { navController.popBackStack() },
                infoMessage = viewModel.uiState.info,
                errorMessage = viewModel.uiState.error
            )
        }

        composable(Routes.Admin2FA) {
            AdminTwoFAScreen(
                qrCodeUrl = viewModel.uiState.qrCodeUrl,
                secret = viewModel.uiState.secret,
                errorMessage = viewModel.uiState.error,
                onVerify = { token -> viewModel.verifyAdmin2FA(token, navController) }
            )
        }

        composable(Routes.Home) {
            HomeScreen(onLogout = {
                viewModel.logout()
                navController.navigate(Routes.Login) {
                    popUpTo(Routes.Login) { inclusive = true }
                }
            })
        }

        composable(Routes.AdminLogin) {
            AdminLoginScreen(
                onLogin = { user, pass -> viewModel.login(user, pass, navController) },
                errorMessage = viewModel.uiState.error
            )
        }

        composable("${Routes.CheckEmail}/{email}/{under13}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val under13 = backStackEntry.arguments?.getString("under13")?.toBoolean() ?: false
            CheckEmailScreen(
                email = email,
                under13 = under13,
                onGoToLogin = {
                    navController.navigate(Routes.Login) {
                        popUpTo(Routes.Login) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.Tos) {
            TermsOfServiceScreen(onBack = { navController.popBackStack() })
        }
        composable(Routes.Privacy) {
            PrivacyPolicyScreen(onBack = { navController.popBackStack() })
        }
        composable(Routes.Cookies) {
            CookiesPolicyScreen(onBack = { navController.popBackStack() })
        }
    }
}
