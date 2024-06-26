package com.example.foodrecommenderapp.admin.common.presentation

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import com.example.foodrecommenderapp.admin.menu.model.Menu
import com.example.foodrecommenderapp.admin.report.model.Reports
import com.example.foodrecommenderapp.order.data.model.Order
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class AdminState(
    val menuList: List<Menu> = emptyList(),
    val isLoading: Boolean = false,
    val orders: List<Order> = emptyList(),
    val errorMessage: String = "",
    val mealNameErrorMessage:String? =null,
    val mealPriceErrorMessage:String? =null,
    val categoryErrorMessage:String? = null,
    val showErrorDialog: Boolean = false,
    val mealName:String? = null,
    val mealPrice:Double? = null,
    val categoryName: String? = null,
    val foodImageUri:Uri? = null,
    val categoryImageUri: Uri? = null,
    val foodImage:ByteArray?= null,
    val categoryImage:ByteArray? = null,
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
    val isDishTypePreferenceExpanded: Boolean = false,
    val reports: Reports? = null,
    val showEmptyScreen:Boolean = false,
    val date:String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
) {
}