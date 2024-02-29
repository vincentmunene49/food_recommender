package com.example.foodrecommenderapp.auth.login.presentation

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val emailErrorMessage: String? = "",
    val passwordErrorMessage: String? = "",
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val showErrorDialog: Boolean = false,
    val switchToAdmin:Boolean = false
) {
}