package com.example.foodrecommenderapp.home.presentation

import com.example.foodrecommenderapp.home.data.model.Meal

data class HomeState(
    val isLoading: Boolean = false,
    val meals: Meal? = null,
    val error: String = "",
    val showErrorDialog: Boolean = false,
) {
}