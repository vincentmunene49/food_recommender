package com.example.foodrecommenderapp.navigation

sealed class Route(val route: String) {
    object Login : Route(route = LOGIN_ROUTE)
    object Register : Route(route = REGISTER_ROUTE)
    object Home : Route(route = HOME_ROUTE)
}

const val LOGIN_ROUTE = "LOGIN"
const val REGISTER_ROUTE = "REGISTER"
const val HOME_ROUTE = "HOME"