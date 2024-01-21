package com.example.foodrecommenderapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodrecommenderapp.auth.login.presentation.LoginScreen
import com.example.foodrecommenderapp.auth.login.presentation.LoginViewModel
import com.example.foodrecommenderapp.auth.register.presentation.RegisterScreen
import com.example.foodrecommenderapp.auth.register.presentation.RegisterViewModel
import com.example.foodrecommenderapp.home.presentation.HomeScreen


@Composable
fun NavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Login.route
    ) {

        composable(route = Route.Register.route) {
            RegisterScreen(
                navController = navController
            )
        }
        composable(route = Route.Login.route) {
           LoginScreen(
                navController = navController
           )
        }

        composable(route = Route.Home.route) {
            HomeScreen(
                navController = navController
            )
        }
    }


}
