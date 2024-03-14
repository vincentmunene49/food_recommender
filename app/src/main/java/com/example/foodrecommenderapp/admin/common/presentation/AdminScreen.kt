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
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import com.example.foodrecommenderapp.admin.menu.presentation.MenuCreationScreen
import com.example.foodrecommenderapp.admin.report.presentation.ReportsScreen
import com.example.foodrecommenderapp.ui.theme.FoodRecommenderAppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AdminScreen(
    viewModel: AdminSharedViewModel

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
                )
            )
        },
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            TabRow(
               modifier =  Modifier.padding(vertical = 8.dp),
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