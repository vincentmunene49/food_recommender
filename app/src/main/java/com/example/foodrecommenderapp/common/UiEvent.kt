package com.example.foodrecommenderapp.common

sealed class UiEvent {
    data class OnSuccess(val message: String) : UiEvent()
    data class NavigateToAdminScreen(val message: String) : UiEvent()
    data class OnNavigateToPreferenceScreen(val message: String) : UiEvent()
    object OnEmptyReports : UiEvent()

    object NavigateToLoginScreen : UiEvent()

    object LogoutFromAdmin : UiEvent()


}