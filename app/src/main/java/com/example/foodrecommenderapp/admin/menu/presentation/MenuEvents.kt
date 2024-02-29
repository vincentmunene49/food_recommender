package com.example.foodrecommenderapp.admin.menu.presentation

import android.net.Uri


sealed class MenuEvents {
    object OnClickSubmit: MenuEvents()

    data class OnClickAddIngredient( val ingredient:String): MenuEvents()

    object OnDismissSuccessDialog: MenuEvents()

    object OnDismissErrorDialog: MenuEvents()

    data class OnAddMealName(val name: String): MenuEvents()

    data class OnAddCategory(val category: String): MenuEvents()

    data class OnImageSelected(val imageUri:Uri): MenuEvents()

    data class OnAddIngredient(val index:Int, val ingredient: String): MenuEvents()

    object OnClickHealthDoneAction : MenuEvents()

    object OnClickDietDoneAction : MenuEvents()

    object OnClickCousineDoneAction : MenuEvents()

    object OnClickMealTypeDoneAction : MenuEvents()

    object OnClickDishTypeDoneAction : MenuEvents()

    data class OnSelectHealthPreference(val healthItem: String) : MenuEvents()

    data class OnDeselectHealthPreference(val healthItem: String) : MenuEvents()

    data class OnSelectDietPreference(val dietItem: String) : MenuEvents()

    data class OnDeselectDietPreference(val dietItem: String) : MenuEvents()

    data class OnSelectCousinePreference(val cousineItem: String) : MenuEvents()

    data class OnDeselectCousinePreference(val cousineItem: String) : MenuEvents()

    data class OnSelectMealTypePreference(val mealTypeItem: String) : MenuEvents()

    data class OnDeselectMealTypePreference(val mealTypeItem: String) : MenuEvents()

    data class OnSelectDishTypePreference(val dishTypeItem: String) : MenuEvents()

    data class OnDeselectDishTypePreference(val dishTypeItem: String) : MenuEvents()

    object OnDismissHealthDropDown : MenuEvents()

    object OnDismissDietDropDown : MenuEvents()

    object OnDismissCousineDropDown : MenuEvents()

    object OnDismissMealTypeDropDown : MenuEvents()

    object OnDismissDishTypeDropDown : MenuEvents()

    object OnHealthExpandedStateChange: MenuEvents()

    object OnDietExpandedStateChange: MenuEvents()

    object OnCousineExpandedStateChange: MenuEvents()

    object OnMealTypeExpandedStateChange: MenuEvents()

    object OnDishTypeExpandedStateChange: MenuEvents()


}