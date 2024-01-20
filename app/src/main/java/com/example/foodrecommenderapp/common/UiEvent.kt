package com.example.foodrecommenderapp.common

sealed class UiEvent {
    data class OnSuccess(val message:String): UiEvent()

}