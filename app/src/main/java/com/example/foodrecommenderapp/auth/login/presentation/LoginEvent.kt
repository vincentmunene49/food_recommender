package com.example.foodrecommenderapp.auth.login.presentation

sealed class LoginEvent {
    data class OnTypeEmail(val email: String) : LoginEvent()
    data class OnTypePassword(val password: String) : LoginEvent()
    object OnClickLogin : LoginEvent()
    data class OnClickTogglePasswordVisibility(val isPasswordVisible: Boolean) : LoginEvent()
    object OnDismissErrorDialog : LoginEvent()

    object OnClickSwitchToAdmin : LoginEvent()


}