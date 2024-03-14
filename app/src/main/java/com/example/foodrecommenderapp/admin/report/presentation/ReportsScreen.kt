package com.example.foodrecommenderapp.admin.report.presentation

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodrecommenderapp.admin.common.presentation.AdminEvents
import com.example.foodrecommenderapp.admin.common.presentation.AdminSharedViewModel
import com.example.foodrecommenderapp.admin.common.presentation.AdminState
import com.example.foodrecommenderapp.admin.report.model.Reports
import com.example.foodrecommenderapp.common.UiEvent
import com.example.foodrecommenderapp.common.presentation.components.ErrorDialog
import com.example.foodrecommenderapp.ui.theme.FoodRecommenderAppTheme
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber
import kotlin.random.Random

@Composable
fun ReportsScreen(
    viewModel: AdminSharedViewModel
) {
    ReportsScreenContent(
        state = viewModel.state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreenContent(
    modifier: Modifier = Modifier,
    state: AdminState = AdminState(),
    onEvent: (AdminEvents) -> Unit,

    ) {

    val calenderState = rememberUseCaseState()
    CalendarDialog(
        state = calenderState,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            style = CalendarStyle.MONTH,
        ),
        selection = CalendarSelection.Date { newDate ->
            onEvent(AdminEvents.OnSelectDate(newDate.toString()))
        },
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        }

        if (state.showErrorDialog) {
            ErrorDialog(
                errorMessage = state.errorMessage,
                onDismissDialog = { onEvent(AdminEvents.OnDismissErrorDialog) }
            )

        }
        if (state.showEmptyScreen) {
            EmptyDataScreen()

        }

        Column(
            modifier = modifier
                .fillMaxSize()
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = state.date,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 18.sp),
                    color = MaterialTheme.colorScheme.onBackground

                )

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Total Searches",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 18.sp),
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),

                        )
                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = state.reports?.totalSearches.toString(),
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 20.sp),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                IconButton(onClick = {
                    calenderState.show()
                }) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(state.reports?.preferences?.keys?.toList() ?: emptyList()) { key ->
                    val map = state.reports?.preferences?.get(key)
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = key.uppercase(),
                            style = MaterialTheme.typography.titleSmall.copy(fontSize = 20.sp),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                            maxItemsInEachRow = 3
                        ) {
                            map?.forEach {
                                val randomColor = Color(
                                    red = Random.nextFloat(),
                                    green = Random.nextFloat(),
                                    blue = Random.nextFloat()
                                )

                                Card(
                                    modifier = Modifier.padding(8.dp),
                                ) {
                                    Row(
                                        modifier = Modifier.padding(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .background(
                                                    color = randomColor,
                                                    shape = CircleShape
                                                )
                                                .size(20.dp)
                                        )
                                        Text(
                                            modifier = Modifier.padding(start = 8.dp),
                                            text = "${it.key}:",
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                fontSize = 18.sp
                                            ),
                                        )
                                        Text(
                                            modifier = Modifier.padding(start = 4.dp),
                                            text = it.value.toString(),
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                fontSize = 18.sp
                                            ),
                                        )
                                    }

                                }
                            }
                        }

                    }
                }

            }
        }
    }
}

@Composable
fun EmptyDataScreen(
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.ErrorOutline,
                contentDescription = "No data",
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                modifier = Modifier.size(100.dp)
            )
            Text(
                text = "No Searches for this day",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                modifier = Modifier.padding(top = 16.dp)
            )
        }

    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
private fun PreviewReports() {
    FoodRecommenderAppTheme {
        ReportsScreenContent(
            state = AdminState(
                date = "14,march 2024",
                reports = Reports(
                    totalSearches = 100,
                    preferences = mapOf(
                        "Preference 1" to mapOf(
                            "Item 1" to 10,
                            "Item 2" to 20,
                            "Item 3" to 30
                        ),
                        "Preference 2" to mapOf(
                            "Item 1" to 10,
                            "Item 2" to 20,
                            "Item 3" to 30
                        ),
                        "Preference 3" to mapOf(
                            "Item 1" to 10,
                            "Item 2" to 20,
                            "Item 3" to 30
                        )
                    )
                )
            ),
            onEvent = {}
        )
    }
}
