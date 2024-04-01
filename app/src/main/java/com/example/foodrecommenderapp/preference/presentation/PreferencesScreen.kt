package com.example.foodrecommenderapp.preference.presentation

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.foodrecommenderapp.R
import com.example.foodrecommenderapp.common.constants.DIETLISTLABELS
import com.example.foodrecommenderapp.common.constants.HEALTHLISTLABELS
import com.example.foodrecommenderapp.home.presentation.HomeScreenEvents
import com.example.foodrecommenderapp.home.presentation.HomeSharedViewModel
import com.example.foodrecommenderapp.home.presentation.HomeState
import com.example.foodrecommenderapp.order.presentation.AddToOrderDialog
import com.example.foodrecommenderapp.ui.theme.FoodRecommenderAppTheme

@Composable
fun PreferenceScreen(
    viewModel: HomeSharedViewModel,
    navController: NavController
) {
    PreferencesScreenContent(
        state = viewModel.state,
        navController = navController,
        onEvent = viewModel::onEvent
    )

}

@Composable
fun PreferencesScreenContent(
    state: HomeState,
    onEvent: (HomeScreenEvents) -> Unit,
    navController: NavController
) {

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(modifier = Modifier.padding(start = 16.dp),
                onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )

            }

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Recommended For You",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp)
            )

        }
    }) { paddingValues ->
        if(state.showAddToOrderDialog){
            AddToOrderDialog(
                onDismissDialog = { onEvent(HomeScreenEvents.OnDismissAddToOrderDialog) },
                onClickCancel = { onEvent(HomeScreenEvents.OnClickCancelAddToOrder) },
                onClickConfirm = { onEvent(HomeScreenEvents.OnClickConfirmAddToOrder) },
                title = "Are you sure you want to order this meal?",
                confirmTitle = "Order",
                cancelTitle = "Cancel"
            )
        }

        if (state.meals.isNullOrEmpty()) {
            EmptyResponse(modifier = Modifier.padding(paddingValues))
        } else {

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
            ) {
                items(state.meals) { menu ->
                    MealCard(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        imagePath = menu.image.toString(),
                        mealName = menu.name,
                        healthList = menu.preferences["health"],
                        dietList = menu.preferences["diet"],
                        onClick = {
                            onEvent(HomeScreenEvents.OnClickPreferencesCard)
                        }
                    )
                }
            }

        }
    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MealCard(
    modifier: Modifier = Modifier,
    imagePath: String,
    mealName: String,
    healthList: List<String>?,
    dietList: List<String>?,
    price: String? = null,
    onClick: () -> Unit = {}
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .clip(RoundedCornerShape(10.dp)),
        colors = CardDefaults.cardColors(
            containerColor = if (isSystemInDarkTheme()) Color(0xFF4D4D4D) else Color.White,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSystemInDarkTheme()) 2.dp else 4.dp
        )
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .clip(RoundedCornerShape(10.dp)), contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = imagePath,
                    contentDescription = null,
                    contentScale = ContentScale.Crop, // Use Crop to ensure the image fills the circular shape
                    placeholder = painterResource(id = R.drawable.meal)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 3.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),

                ) {

                Text(
                    text = mealName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )

                FlowRow {
                    if (healthList?.isNotEmpty() == true && healthList.size > 8) {
                        for (i in 0..8) {
                            Text(
                                text = if (healthList[i] == healthList.lastOrNull()) healthList[i] else "${healthList[i]},",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.Light,
                                    fontSize = 10.sp
                                ),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    } else {
                        healthList?.forEach {
                            Text(
                                text = if (it == healthList.lastOrNull()) it else "$it,",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Light),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }


                }

                FlowRow {
                    if (dietList?.isNotEmpty() == true && dietList.size > 8) {
                        for (i in 0..8) {
                            Text(
                                text = if (dietList[i] == dietList.lastOrNull()) dietList[i] else "${dietList[i]},",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Light),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    } else {
                        dietList?.forEach {
                            Text(
                                text = if (it == dietList.lastOrNull()) it else "$it,",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.Light,
                                    fontSize = 10.sp
                                ),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                    }

                }
                Spacer(modifier = Modifier.height(5.dp))


            }
            Text(
                modifier = Modifier
                    .padding(end = 8.dp, top = 8.dp)
                    .align(Alignment.Top),
                text = "Ksh $price",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }

}

@Composable
fun EmptyResponse(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "Unfortunately,No Suggestions With those Specifics \uD83D\uDE1E",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
        )

    }

}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun PreviewMealCard() {
    FoodRecommenderAppTheme {
        Surface {
            MealCard(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                imagePath = "",
                mealName = "BBQ and Sour Cream & Onion Chips",
                healthList = HEALTHLISTLABELS,
                dietList = DIETLISTLABELS,
                price = "500"
            )
        }

    }

}