package com.example.foodrecommenderapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.foodrecommenderapp.admin.common.presentation.AdminScreen
import com.example.foodrecommenderapp.admin.menu.presentation.MenuCreationScreen
import com.example.foodrecommenderapp.admin.common.presentation.AdminSharedViewModel
import com.example.foodrecommenderapp.auth.login.presentation.LoginScreen
import com.example.foodrecommenderapp.auth.register.presentation.RegisterScreen
import com.example.foodrecommenderapp.home.presentation.CommonHomeScreen
import com.example.foodrecommenderapp.home.presentation.HomeScreen
import com.example.foodrecommenderapp.home.presentation.HomeSharedViewModel
import com.example.foodrecommenderapp.preference.presentation.PreferenceScreen


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

        navigation(
            route = Route.Content.route,
            startDestination = Route.Home.route
        ) {
            composable(route = Route.Home.route) {
                val viewModel =
                    it.sharedViewModel<HomeSharedViewModel>(navController = navController)

                CommonHomeScreen(
                    viewModel = viewModel,
                    navController = navController
                )
            }

            composable(route = Route.Preference.route) {
                val viewModel =
                    it.sharedViewModel<HomeSharedViewModel>(navController = navController)
                PreferenceScreen(
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }

        navigation(
            route = Route.AdminStart.route,
            startDestination = Route.Admin.route
        ) {
            composable(route = Route.Admin.route) {
                val viewModel =
                    it.sharedViewModel<AdminSharedViewModel>(navController = navController)
                AdminScreen(
                    viewModel = viewModel
                )
            }
        }


    }


}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController,
): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}
