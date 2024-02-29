package com.example.foodrecommenderapp.auth.login.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecommenderapp.auth.login.domain.LoginRepository
import com.example.foodrecommenderapp.common.Resource
import com.example.foodrecommenderapp.common.UiEvent
import com.example.foodrecommenderapp.common.domain.FormValidatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
    private val formValidator: FormValidatorRepository

) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun event(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnTypeEmail -> {
                state = state.copy(email = event.email)
                validateEmail()
            }

            is LoginEvent.OnTypePassword -> {
                state = state.copy(password = event.password)
                validatePassword()
            }

            is LoginEvent.OnClickLogin -> {
                val isEmailValid = validateEmail()
                val isPasswordValid = validatePassword()
                if (isEmailValid && isPasswordValid) {
                    login(
                        state.email,
                        state.password,
                        state.switchToAdmin
                    )

                }
            }

            is LoginEvent.OnClickTogglePasswordVisibility -> {
                state = state.copy(isPasswordVisible = event.isPasswordVisible)
            }

            LoginEvent.OnDismissErrorDialog -> {
                state = state.copy(
                    showErrorDialog = false
                )

            }

            LoginEvent.OnClickSwitchToAdmin -> {
                state = state.copy(
                    switchToAdmin = !state.switchToAdmin
                )
            }
        }
    }


    private fun login(email: String, password: String, isAdmin:Boolean) {
        viewModelScope.launch {
            repository.login(email, password,isAdmin).onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                        )
                        if (state.switchToAdmin) {
                            _uiEvent.send(UiEvent.NavigateToAdminScreen("Login admin success"))
                        } else {
                            _uiEvent.send(UiEvent.OnSuccess("Login successful"))
                        }

                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            errorMessage = resource.message ?: "An unexpected error occurred",
                            showErrorDialog = true
                        )
                    }

                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = true,
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    private fun validateEmail(): Boolean {
        val emailResult = formValidator.validateEmail(state.email)
        state = state.copy(emailErrorMessage = emailResult.errorMessage)
        return emailResult.successful
    }

    private fun validatePassword(): Boolean {
        val passwordResult = formValidator.validatePassword(state.password)
        state = state.copy(passwordErrorMessage = passwordResult.errorMessage)
        return passwordResult.successful
    }
}