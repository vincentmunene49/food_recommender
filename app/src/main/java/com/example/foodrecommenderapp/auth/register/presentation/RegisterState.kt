package com.example.foodrecommenderapp.auth.register.presentation

data class RegisterState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val isEmailError: Boolean = false,
    val isUsernameError: Boolean = false,
    val isPasswordError: Boolean = false,
    val isConfirmPasswordError: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val showErrorDialog: Boolean = false,
    val imagePath:String = "",
)
