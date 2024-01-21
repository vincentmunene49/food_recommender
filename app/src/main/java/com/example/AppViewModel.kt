package com.example

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor() : ViewModel() {


    init {
        viewModelScope.launch {
            setSplashCondition()

        }
    }

    var splashCondition by mutableStateOf(true)
        private set

    private suspend fun setSplashCondition() {
        delay(300)
        splashCondition = false
    }
}