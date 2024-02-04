package com.example.foodrecommenderapp.navigation

sealed class Route(val route: String) {
    object Login : Route(route = LOGIN_ROUTE)
    object Register : Route(route = REGISTER_ROUTE)

    object Content : Route(route = CONTENT_ROUTE)
    object Home : Route(route = HOME_ROUTE)

    object Preference : Route(route = PREFERENCE_ROUTE)
}

const val LOGIN_ROUTE = "LOGIN"
const val REGISTER_ROUTE = "REGISTER"
const val CONTENT_ROUTE = "CONTENT"
const val HOME_ROUTE = "HOME"
const val PREFERENCE_ROUTE = "PREFERENCE"