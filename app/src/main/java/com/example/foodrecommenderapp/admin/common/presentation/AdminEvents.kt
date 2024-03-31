package com.example.foodrecommenderapp.admin.common.presentation

import android.net.Uri


sealed class AdminEvents {
    object OnClickSubmit : AdminEvents()

    data class OnClickAddIngredient(val ingredient: String) : AdminEvents()

    object OnDismissSuccessDialog : AdminEvents()

    object OnDismissErrorDialog : AdminEvents()

    data class OnAddMealName(val name: String) : AdminEvents()

    data class OnAddCategory(val category: String) : AdminEvents()

    data class OnFoodImageSelected(val imageUri: Uri) : AdminEvents()

    data class OnCategoryImageSelected(val imageUri: Uri) : AdminEvents()

    data class OnAddIngredient(val index: Int, val ingredient: String) : AdminEvents()

    object OnClickHealthDoneAction : AdminEvents()

    object OnClickDietDoneAction : AdminEvents()

    object OnClickCousineDoneAction : AdminEvents()

    object OnClickMealTypeDoneAction : AdminEvents()

    object OnClickDishTypeDoneAction : AdminEvents()

    data class OnSelectHealthPreference(val healthItem: String) : AdminEvents()

    data class OnDeselectHealthPreference(val healthItem: String) : AdminEvents()

    data class OnSelectDietPreference(val dietItem: String) : AdminEvents()

    data class OnDeselectDietPreference(val dietItem: String) : AdminEvents()

    data class OnSelectCousinePreference(val cousineItem: String) : AdminEvents()

    data class OnDeselectCousinePreference(val cousineItem: String) : AdminEvents()

    data class OnSelectMealTypePreference(val mealTypeItem: String) : AdminEvents()

    data class OnDeselectMealTypePreference(val mealTypeItem: String) : AdminEvents()

    data class OnSelectDishTypePreference(val dishTypeItem: String) : AdminEvents()

    data class OnDeselectDishTypePreference(val dishTypeItem: String) : AdminEvents()

    object OnDismissHealthDropDown : AdminEvents()

    object OnDismissDietDropDown : AdminEvents()

    object OnDismissCousineDropDown : AdminEvents()

    object OnDismissMealTypeDropDown : AdminEvents()

    object OnDismissDishTypeDropDown : AdminEvents()

    object OnHealthExpandedStateChange : AdminEvents()

    object OnDietExpandedStateChange : AdminEvents()

    object OnCousineExpandedStateChange : AdminEvents()

    object OnMealTypeExpandedStateChange : AdminEvents()

    object OnDishTypeExpandedStateChange : AdminEvents()

    data class OnSelectDate(val date: String) : AdminEvents()
}