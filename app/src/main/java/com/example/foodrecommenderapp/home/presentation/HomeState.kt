package com.example.foodrecommenderapp.home.presentation

import com.example.foodrecommenderapp.home.data.model.Category
import com.example.foodrecommenderapp.home.data.model.Meal
import com.example.foodrecommenderapp.preference.data.pojo.Hit

data class HomeState(
    val isLoading: Boolean = false,
    val isPreferencesLoading: Boolean = false,
    val meals: Meal? = null,
    val categories:Category? = null,
    val preferenceMealList:List<Hit>? = emptyList(),
    val error: String = "",
    val showErrorDialog: Boolean = false,
    val showPreferencesDialog: Boolean = false,
    val showPreferenceLoadingDialog: Boolean = false,
    val searchTerm: String = "",
    val selectedHealthListPreferences: List<String> = emptyList(),
    val selectedDietListPreferences: List<String> = emptyList(),
    val selectedCousineListPreferences: List<String> = emptyList(),
    val selectedMealTypePreferences: List<String> = emptyList(),
    val selectedDishTypePreferences: List<String> = emptyList(),
    val isHealthPreferenceExpanded: Boolean = false,
    val isDietPreferenceExpanded: Boolean = false,
    val isCousinePreferenceExpanded: Boolean = false,
    val isMealTypePreferenceExpanded: Boolean = false,
    val isDishTypePreferenceExpanded: Boolean = false


) {
}