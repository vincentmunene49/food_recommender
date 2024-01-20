package com.example.foodrecommenderapp.auth.login.presentation

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isEmailError: Boolean = false,
    val isPasswordError: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val showErrorDialog: Boolean = false,

) {
}