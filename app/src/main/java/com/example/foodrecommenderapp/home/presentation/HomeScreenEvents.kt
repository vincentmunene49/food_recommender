package com.example.foodrecommenderapp.home.presentation

sealed class HomeScreenEvents {
    object OnClickLaunch: HomeScreenEvents()

    object OnDismissShowPreferencesDialog: HomeScreenEvents()

    data class OnMealTypeChanged(val mealType: String): HomeScreenEvents()

    data class OnCousineTypeChanged(val cousineType: String): HomeScreenEvents()

    data class OnDietTypeChanged(val dietType: String): HomeScreenEvents()

    data class OnAllergyTypeChanged(val allergyType: String): HomeScreenEvents()


    data class OnSearchTermChanged(val searchTerm: String): HomeScreenEvents()
}