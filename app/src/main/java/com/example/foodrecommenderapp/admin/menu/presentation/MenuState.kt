package com.example.foodrecommenderapp.admin.menu.presentation

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.foodrecommenderapp.admin.menu.model.Menu

data class MenuState(
    val menuList: List<Menu> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val mealNameErrorMessage:String? =null,
    val categoryErrorMessage:String? = null,
    val showErrorDialog: Boolean = false,
    val mealName:String? = null,
    val mealCategory: String? = null,
    val image:Uri?= null,
    val showSuccessDialog: Boolean = false,
    val ingredients: MutableList<String> = mutableStateListOf(),
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