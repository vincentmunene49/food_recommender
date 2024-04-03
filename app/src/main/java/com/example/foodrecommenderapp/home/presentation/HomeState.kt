package com.example.foodrecommenderapp.home.presentation

import com.example.foodrecommenderapp.admin.menu.model.Category
import com.example.foodrecommenderapp.admin.menu.model.Menu
import com.example.foodrecommenderapp.order.data.model.Order
import com.example.foodrecommenderapp.preference.data.pojo.Hit

data class HomeState(
    val isLoading: Boolean = false,
    val userPreferences: Map<String, List<String?>> = emptyMap(),
    val isPreferencesLoading: Boolean = false,
    val meals: List<Menu>? = null,
    val menu: Menu? = null,
    val orders: List<Order>? = null,
    val quantity:Int = 0,
    val totalPrice:Double? = 0.0,
    val categories:List<Category>? = null,
    val preferenceMealList:List<Hit>? = emptyList(),
    val error: String = "",
    val showErrorDialog: Boolean = false,
    val showPreferencesDialog: Boolean = false,
    val showPreferenceLoadingDialog: Boolean = false,
    val searchTerm: String = "",
    val selectedCategory: String = "",
    val selectedHealthListPreferences: List<String?> = emptyList(),
    val selectedDietListPreferences: List<String?> = emptyList(),
    val selectedCousineListPreferences: List<String?> = emptyList(),
    val selectedMealTypePreferences: List<String?> = emptyList(),
    val selectedDishTypePreferences: List<String?> = emptyList(),
    val isHealthPreferenceExpanded: Boolean = false,
    val isDietPreferenceExpanded: Boolean = false,
    val isCousinePreferenceExpanded: Boolean = false,
    val isMealTypePreferenceExpanded: Boolean = false,
    val isDishTypePreferenceExpanded: Boolean = false,
    val totalSearches: Long = 0L,
    val showAddToOrderDialog: Boolean = false,


) {

}