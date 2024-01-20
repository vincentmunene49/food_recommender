package com.example.foodrecommenderapp.auth.register.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecommenderapp.auth.register.domain.RegisterRepository
import com.example.foodrecommenderapp.common.Resource
import com.example.foodrecommenderapp.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: RegisterRepository
) : ViewModel() {

    var state by mutableStateOf(RegisterState())
        private set

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun event(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnTypeUsername -> {
                state = state.copy(username = event.username)
            }

            is RegisterEvent.OnTypeEmail -> {
                state = state.copy(email = event.email)
            }

            is RegisterEvent.OnTypePassword -> {
                state = state.copy(password = event.password)
            }

            is RegisterEvent.OnTypeConfirmPassword -> {
                state = state.copy(confirmPassword = event.confirmPassword)
            }

            is RegisterEvent.OnClickRegister -> {
                registerUser(
                    state.email,
                    state.password,
                    state.username,
                    state.imagePath
                )
            }

            is RegisterEvent.OnClickToggleConfirmPasswordVisibility -> {
                state = state.copy(isConfirmPasswordVisible = event.isConfirmPasswordVisible)
            }

            is RegisterEvent.OnClickTogglePasswordVisibility -> {
                state = state.copy(isPasswordVisible = event.isPasswordVisible)
            }

        }
    }

    private fun registerUser(
        email: String,
        password: String,
        name: String,
        image: String
    ) {
        viewModelScope.launch {
            repository.register(
                email,
                password,
                name,
                image,
                UUID.randomUUID().toString()
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
                        state = state.copy(
                            isLoading = true,
                        )
                    }
                }
            }.launchIn(this)
        }
    }

}