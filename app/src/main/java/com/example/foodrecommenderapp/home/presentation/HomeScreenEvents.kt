package com.example.foodrecommenderapp.home.presentation

import com.example.foodrecommenderapp.admin.menu.model.Menu
import com.example.foodrecommenderapp.order.data.model.Order

sealed class HomeScreenEvents {
    object OnClickLaunch : HomeScreenEvents()

    object OnDismissShowPreferencesDialog : HomeScreenEvents()

    object OnClickPreferencesCard : HomeScreenEvents()


    object OnDismissAddToOrderDialog : HomeScreenEvents()
    data class OnSearchTermChanged(val searchTerm: String) : HomeScreenEvents()

    data class OnClickMenu(val menu: Menu) : HomeScreenEvents()

    object OnClickConfirmAddToOrder : HomeScreenEvents()

    object OnClickCancelAddToOrder : HomeScreenEvents()

    object OnClickOk : HomeScreenEvents()

    object OnDismissPreferencesDialog : HomeScreenEvents()

    object OnClickHealthDoneAction : HomeScreenEvents()

    object OnClickDietDoneAction : HomeScreenEvents()

    object OnClickCousineDoneAction : HomeScreenEvents()

    object OnClickMealTypeDoneAction : HomeScreenEvents()

    object OnClickDishTypeDoneAction : HomeScreenEvents()

    data class OnSelectHealthPreference(val healthItem: String) : HomeScreenEvents()

    data class OnDeselectHealthPreference(val healthItem: String) : HomeScreenEvents()

    data class OnSelectDietPreference(val dietItem: String) : HomeScreenEvents()

    data class OnDeselectDietPreference(val dietItem: String) : HomeScreenEvents()

    data class OnSelectCousinePreference(val cousineItem: String) : HomeScreenEvents()

    data class OnDeselectCousinePreference(val cousineItem: String) : HomeScreenEvents()

    data class OnSelectMealTypePreference(val mealTypeItem: String) : HomeScreenEvents()

    data class OnDeselectMealTypePreference(val mealTypeItem: String) : HomeScreenEvents()

    data class OnSelectDishTypePreference(val dishTypeItem: String) : HomeScreenEvents()

    data class OnDeselectDishTypePreference(val dishTypeItem: String) : HomeScreenEvents()

    data class OnClickCategory(val categoryName: String) : HomeScreenEvents()

    object OnDismissHealthDropDown : HomeScreenEvents()

    object OnDismissDietDropDown : HomeScreenEvents()

    object OnDismissCousineDropDown : HomeScreenEvents()

    object OnDismissMealTypeDropDown : HomeScreenEvents()

    object OnDismissDishTypeDropDown : HomeScreenEvents()

    object OnHealthExpandedStateChange : HomeScreenEvents()

    object OnDietExpandedStateChange : HomeScreenEvents()

    object OnCousineExpandedStateChange : HomeScreenEvents()

    object OnMealTypeExpandedStateChange : HomeScreenEvents()

    object OnDishTypeExpandedStateChange : HomeScreenEvents()
    data class DeleteOrder(val order: Order) : HomeScreenEvents()


}