package com.example.foodrecommenderapp.home.presentation

import com.example.foodrecommenderapp.home.data.model.Category
import com.example.foodrecommenderapp.home.data.model.Meal

data class HomeState(
    val isLoading: Boolean = false,
    val meals: Meal? = null,
    val categories:Category? = null,
    val error: String = "",
    val showErrorDialog: Boolean = false,
    val showPreferencesDialog: Boolean = false,
    val mealType: String = "",
    val cousineType: String = "",
    val dietType: String = "",
    val allergyType: String = "",
    val searchTerm: String = ""

) {
}