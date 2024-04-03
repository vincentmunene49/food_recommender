package com.example.foodrecommenderapp.admin.common.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.DeliveryDining
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.foodrecommenderapp.admin.menu.presentation.MenuCreationScreen
import com.example.foodrecommenderapp.admin.report.presentation.OrderReportsScreen
import com.example.foodrecommenderapp.admin.report.presentation.ReportsScreen
import com.example.foodrecommenderapp.common.UiEvent
import com.example.foodrecommenderapp.navigation.Route
import com.example.foodrecommenderapp.ui.theme.FoodRecommenderAppTheme
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AdminScreen(
    viewModel: AdminSharedViewModel,
    navController: NavController
) {
    val tabList = listOf(
        TabItem(
            title = "Reports",
            selectedIcon = Icons.Filled.Analytics,
            unselectedIcon = Icons.Outlined.Analytics
        ),
        TabItem(
            title = "Create Menu",
            selectedIcon = Icons.Filled.Create,
            unselectedIcon = Icons.Outlined.Create
        ),

        TabItem(
            title = "Orders",
            selectedIcon = Icons.Filled.DeliveryDining,
            unselectedIcon = Icons.Outlined.DeliveryDining
        )
    )
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
                is UiEvent.LogoutFromAdmin -> {
                    Timber.tag("CommonHomeScreen").d("Navigate to login screen")
                    navController.navigate(route = Route.Login.route) {
                        popUpTo(Route.AdminStart.route) {
                            inclusive = true
                        }
                    }
                }

                else -> {}
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Admin Dashboard",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = { viewModel.onEvent(AdminEvents.OClickLogOut) }) {
                        Icon(
                            imageVector = Icons.Outlined.Logout,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        },
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            TabRow(
                modifier = Modifier.padding(vertical = 8.dp),
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
                        ReportsScreen(viewModel)
                    }

                    1 -> {
                        MenuCreationScreen(viewModel)
                    }

                    2 -> {
                        OrderReportsScreen(viewModel)
                    }
                }

            }
        }

    }
}

@Preview
@Composable
private fun PreviewAdmin() {
    FoodRecommenderAppTheme {
    }

}


data class TabItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val icon: @Composable () -> Unit = {}
)