package com.example.foodrecommenderapp.auth.register.presentation

sealed class RegisterEvent {
    data class OnTypeUsername(val username: String) : RegisterEvent()
    data class OnTypeEmail(val email: String) : RegisterEvent()
    data class OnTypePassword(val password: String) : RegisterEvent()
    data class OnTypeConfirmPassword(val confirmPassword: String) : RegisterEvent()
    object OnClickRegister : RegisterEvent()
    data class OnClickTogglePasswordVisibility(val isPasswordVisible: Boolean) : RegisterEvent()
    data class OnClickToggleConfirmPasswordVisibility(val isConfirmPasswordVisible: Boolean) :
        RegisterEvent()
}