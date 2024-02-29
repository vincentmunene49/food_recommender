package com.example.foodrecommenderapp.auth.register.presentation

data class RegisterState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val emailErrorMessage: String? = "",
    val usernameErrorMessage: String? = "",
    val passwordErrorMessage: String? = "",
    val confirmPasswordErrorMessage: String? = "",
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val showErrorDialog: Boolean = false,
    val imagePath:String = "",
    val switchToAdmin:Boolean = false

)
