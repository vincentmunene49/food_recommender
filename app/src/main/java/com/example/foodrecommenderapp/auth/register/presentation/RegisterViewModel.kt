package com.example.foodrecommenderapp.auth.register.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecommenderapp.auth.register.domain.RegisterRepository
import com.example.foodrecommenderapp.common.Resource
import com.example.foodrecommenderapp.common.UiEvent
import com.example.foodrecommenderapp.common.domain.FormValidatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: RegisterRepository,
    private val formValidator: FormValidatorRepository
) : ViewModel() {

    var state by mutableStateOf(RegisterState())
        private set

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun event(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnTypeUsername -> {
                state = state.copy(username = event.username)
                validateUsername()
            }

            is RegisterEvent.OnTypeEmail -> {
                state = state.copy(email = event.email)
                validateEmail()
            }

            is RegisterEvent.OnTypePassword -> {
                state = state.copy(password = event.password)
                validatePassword()
            }

            is RegisterEvent.OnTypeConfirmPassword -> {
                state = state.copy(confirmPassword = event.confirmPassword)
                validateConfirmPassword()
            }

            is RegisterEvent.OnClickRegister -> {
                val isEmailValid = validateEmail()
                val isPasswordValid = validatePassword()
                val isUsernameValid = validateUsername()
                val isConfirmPasswordValid = validateConfirmPassword()

                if (isEmailValid && isPasswordValid && isUsernameValid &&
                    isConfirmPasswordValid
                ) {
                    registerUser(
                        state.email,
                        state.password,
                        state.username,
                        state.switchToAdmin
                    )


                }
            }

            is RegisterEvent.OnClickToggleConfirmPasswordVisibility -> {
                state = state.copy(isConfirmPasswordVisible = event.isConfirmPasswordVisible)
            }

            is RegisterEvent.OnClickTogglePasswordVisibility -> {
                state = state.copy(isPasswordVisible = event.isPasswordVisible)
            }

            RegisterEvent.OnDismissErrorDialog -> {
                state = state.copy(
                    showErrorDialog = false
                )
            }

            RegisterEvent.OnClickSwitchToAdmin -> {
                state = state.copy(
                    switchToAdmin = !state.switchToAdmin
                )
                Timber.tag("RegisterViewModel").d("Switch to admin: ${state.switchToAdmin}")
            }
        }
    }

    private fun registerUser(
        email: String,
        password: String,
        name: String,
        isAdmin: Boolean
    ) {
        viewModelScope.launch {
            repository.register(
                email,
                password,
                name,
                isAdmin
            ).onEach {
                when (it) {
                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                        )
                        _uiEvent.send(UiEvent.OnSuccess("Registration successful"))
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            errorMessage = it.message ?: "An unexpected error occurred",
                            showErrorDialog = true
                        )
                    }

                    is Resource.Loading -> {
                        Timber.tag("RegisterViewModel").d("Loading")
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

    private fun validateUsername(): Boolean {
        val usernameResult = formValidator.validateUsername(state.username)
        state = state.copy(usernameErrorMessage = usernameResult.errorMessage)
        return usernameResult.successful
    }

    private fun validateConfirmPassword(): Boolean {
        val confirmPasswordResult = formValidator.validateConfirmPassword(
            state.password,
            state.confirmPassword
        )
        state = state.copy(confirmPasswordErrorMessage = confirmPasswordResult.errorMessage)
        return confirmPasswordResult.successful
    }


}