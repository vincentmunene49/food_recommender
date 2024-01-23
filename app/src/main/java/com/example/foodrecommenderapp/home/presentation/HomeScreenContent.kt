package com.example.foodrecommenderapp.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.foodrecommenderapp.R
import com.example.foodrecommenderapp.common.presentation.components.RecommenderAppTextField
import com.example.foodrecommenderapp.home.data.model.Preferences
import com.example.foodrecommenderapp.ui.theme.FoodRecommenderAppTheme


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    HomeScreenContent(
        state = viewModel.state,
        onEvent = viewModel::onEvent
        )

}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    state: HomeState,
    onEvent: (HomeScreenEvents) -> Unit = { },
) {

    Scaffold(
        topBar = {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                text = "Explore",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 24.sp)
            )

        },
        floatingActionButton = {
            IconButton(
                onClick = { onEvent(HomeScreenEvents.OnClickLaunch) }
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.rocket_launch),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

        },

        ) { paddingValues ->


        if (state.showPreferencesDialog) {
            PreferencesComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onDismiss = { onEvent(HomeScreenEvents.OnDismissShowPreferencesDialog) },
                onClickOk = { onEvent(HomeScreenEvents.OnDismissShowPreferencesDialog) },
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                ) {

                    RecommenderAppTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        value = state.allergyType,
                        onValueChange = { onEvent(HomeScreenEvents.OnAllergyTypeChanged(it)) },
                        placeholder = {
                            Text(
                                text = "peanut",
                                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                            )
                        },
                        label = {
                            Text(
                                text = "Allergy",
                                color = MaterialTheme.colorScheme.secondary
                            )
                        },
                    )
                    RecommenderAppTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        value = state.dietType,
                        onValueChange = { onEvent(HomeScreenEvents.OnDietTypeChanged(it)) },
                        placeholder = {
                            Text(
                                text = "balanced",
                                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                            )
                        },
                        label = {
                            Text(
                                text = "Diet Type",
                                color = MaterialTheme.colorScheme.secondary
                            )
                        },
                    )
                    RecommenderAppTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        value = state.cousineType,
                        onValueChange = { onEvent(HomeScreenEvents.OnCousineTypeChanged(it)) },
                        placeholder = {
                            Text(
                                text = "American",
                                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                            )
                        },
                        label = {
                            Text(
                                text = "Cousine",
                                color = MaterialTheme.colorScheme.secondary
                            )
                        },
                    )
                    RecommenderAppTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        value = state.mealType,
                        onValueChange = { onEvent(HomeScreenEvents.OnMealTypeChanged(it)) },
                        placeholder = {
                            Text(
                                text = "lunch",
                                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                            )
                        },
                        label = {
                            Text(
                                text = "Meal Type",
                                color = MaterialTheme.colorScheme.secondary
                            )
                        },
                    )
                }

            }
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            if (state.isLoading) {

                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 30.dp),
                    color = MaterialTheme.colorScheme.primary
                )

            }
            SearchBoxComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = state.searchTerm,
                onValueChange = { onEvent(HomeScreenEvents.OnSearchTermChanged(it)) },
            )

            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "All Categories",
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp),
                color = MaterialTheme.colorScheme.onSurface,
            )

            LazyRow {
                items(state.categories?.categories ?: emptyList()) { category ->
                    MealComponent(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        foodCategory = category.strCategory,
                        foodImage = category.strCategoryThumb
                    )
                }

            }

            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Popular Dishes",
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp),
                color = MaterialTheme.colorScheme.onSurface,
            )

            LazyVerticalGrid(
                modifier = modifier
                    .fillMaxWidth(),
                columns = GridCells.Fixed(count = 2),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {

                state.meals?.let { mealList ->
                    items(mealList.meals) { meal ->
                        AllMealsComponent(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            onClickFood = { /*TODO*/ },
                            imagePath = meal.strMealThumb,
                            foodTitle = meal.strMeal,
                            foodCategory = meal.strCategory
                        )
                    }
                }

            }


        }

    }
}

@Composable
fun SearchBoxComponent(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.bodySmall,
        enabled = true,
        readOnly = false,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
        maxLines = 1,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                focusManager.clearFocus()

            }
        ),
        decorationBox = { innerTextField ->

            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(10.dp)
                    )

            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = Color.Black.copy(alpha = 0.5f)
                        )
                    }
                    innerTextField()
                }
            }

        }

    )

}

@Composable
fun AllMealsComponent(
    modifier: Modifier = Modifier,
    onClickFood: () -> Unit,
    imagePath: String,
    foodTitle: String,
    foodCategory: String
) {
    Card(
        modifier = modifier
            .clickable { onClickFood() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        )
    ) {

        Column {
            Box(
                modifier = Modifier
                    .size(150.dp) // Adjust the size as needed
                    .clip(RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = imagePath,
                    contentDescription = null,
                    contentScale = ContentScale.Crop, // Use Crop to ensure the image fills the circular shape
                    placeholder = painterResource(id = R.drawable.meal)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = foodTitle,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = foodCategory,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )

        }
    }


}

@Composable
fun MealComponent(
    modifier: Modifier = Modifier,
    foodCategory: String,
    foodImage: String,
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(color = Color(0XFFF7F7F7)),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                modifier = Modifier.size(60.dp),
                model = foodImage,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.meal),
                contentScale = ContentScale.Crop
            )

        }
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = foodCategory,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun PreferencesComponent(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onClickOk: () -> Unit,
    content: @Composable () -> Unit = {}

) {

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally


            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    text = "Preferences",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                content()
                Button(onClick = { onClickOk() }) {
                    Text(
                        text = "OK",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

        }
    }


}


@Preview
@Composable
fun PreviewHomeScreen() {
    FoodRecommenderAppTheme {
        HomeScreenContent(state = HomeState(), onEvent = {})
    }

}