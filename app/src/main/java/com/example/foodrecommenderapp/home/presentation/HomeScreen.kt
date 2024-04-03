package com.example.foodrecommenderapp.home.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.foodrecommenderapp.admin.common.presentation.TabItem
import com.example.foodrecommenderapp.common.UiEvent
import com.example.foodrecommenderapp.navigation.Route
import com.example.foodrecommenderapp.order.presentation.OrderScreen
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CommonHomeScreen(
    viewModel: HomeSharedViewModel,
    navController: NavController
) {

    val tabList = listOf(
        TabItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        TabItem(
            title = "Your Orders",
            selectedIcon = Icons.Filled.ShoppingCart,
            unselectedIcon = Icons.Outlined.ShoppingCart
        )
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    val pagerState = rememberPagerState {
        tabList.size
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        selectedIndex = pagerState.currentPage
    }

    LaunchedEffect(key1 = viewModel) {
        viewModel.uiEvent.collect {
            when (it) {
                is UiEvent.NavigateToLoginScreen -> {
                    Timber.tag("CommonHomeScreen").d("Navigate to login screen")
                    navController.navigate(route = Route.Login.route) {
                        popUpTo(Route.Content.route) {
                            inclusive = true
                        }
                    }
                }

                is UiEvent.OnNavigateToPreferenceScreen -> {
                    navController.navigate(route = Route.Preference.route)
                }

                else -> {}
            }
        }

    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Food Recommender",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = { viewModel.onEvent(HomeScreenEvents.OnClickLogout) }) {
                        Icon(
                            imageVector = Icons.Outlined.Logout,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            TabRow(
                modifier = Modifier
                    .padding(bottom = 8.dp),
                selectedTabIndex = selectedIndex
            ) {
                tabList.forEachIndexed { index, tabItem ->
                    Tab(
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        icon = {
                            val icon = if (selectedIndex == index) {
                                tabItem.selectedIcon
                            } else {
                                tabItem.unselectedIcon
                            }
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = if (selectedIndex == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(
                                    alpha = 0.5f
                                )
                            )
                        },
                        text = {
                            Text(text = tabItem.title)
                        }
                    )
                }
            }
            HorizontalPager(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                state = pagerState
            ) {
                when (pagerState.currentPage) {
                    0 -> {
                        HomeScreen(viewModel, navController)
                    }

                    1 -> {
                        OrderScreen(viewModel)
                    }
                }

            }
        }
    }


}