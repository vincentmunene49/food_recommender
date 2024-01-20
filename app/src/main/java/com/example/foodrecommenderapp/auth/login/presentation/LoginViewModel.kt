package com.example.foodrecommenderapp.auth.login.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecommenderapp.auth.login.domain.LoginRepository
import com.example.foodrecommenderapp.common.Resource
import com.example.foodrecommenderapp.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
): ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun event(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnTypeEmail -> {
                state = state.copy(email = event.email)
            }

            is LoginEvent.OnTypePassword -> {
                state = state.copy(password = event.password)
            }

            is LoginEvent.OnClickLogin -> {
                login(state.email, state.password)
            }

            is LoginEvent.OnClickTogglePasswordVisibility -> {
                state = state.copy(isPasswordVisible = event.isPasswordVisible)
            }

        }
    }


    private fun login(email: String, password: String) {
        viewModelScope.launch {
            repository.login(email, password).onEach {resource ->
                when(resource) {
                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                        )
                        _uiEvent.send(UiEvent.OnSuccess("Login successful"))
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
}