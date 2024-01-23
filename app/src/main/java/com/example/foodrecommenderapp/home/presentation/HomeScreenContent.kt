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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.foodrecommenderapp.R
import com.example.foodrecommenderapp.ui.theme.FoodRecommenderAppTheme


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    HomeScreenContent(
        state = viewModel.state
    )

}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    state: HomeState

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
    ) { paddingValues ->

        if(state.isLoading){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            SearchBoxComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = "",
                onSearch = { /*TODO*/ },
                onValueChange = {})

            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Dish of the Day",
                style = MaterialTheme.typography.titleSmall.copy(fontSize = 18.sp),
                color = MaterialTheme.colorScheme.onSurface,
            )

            DishOfTheDayComponent(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                foodTitle = state.meals?.meals?.getOrNull(0)?.strMeal ?: "",
                foodCategory = state.meals?.meals?.getOrNull(0)?.strCategory ?: "",
                area = state.meals?.meals?.getOrNull(0)?.strArea ?: "",
                imagePath = state.meals?.meals?.getOrNull(0)?.strMealThumb ?: "",
                onClickRandomMeal = { /*TODO*/ }
            )
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Recommended for you",
                style = MaterialTheme.typography.titleSmall.copy(fontSize = 18.sp),
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
                        MealComponent(
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
    onSearch: () -> Unit,
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
                onSearch()
            }
        ),
        decorationBox = { innerTextField ->

            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f),
                        shape = CircleShape
                    )

            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(
                        onClick = {
                            onSearch()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DishOfTheDayComponent(
    modifier: Modifier = Modifier,
    foodTitle: String,
    foodCategory: String,
    area: String,
    imagePath: String,
    onClickRandomMeal: () -> Unit,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = {
            onClickRandomMeal()
        },
        colors = CardDefaults.cardColors(
            containerColor = Color(0XFF5AB45E),
        )
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp) // Adjust the size as needed
                        .background(color = Color(0XFF5AB45E))
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    AsyncImage(
                        model = imagePath,
                        contentDescription = null,
                        contentScale = ContentScale.Crop, // Use Crop to ensure the image fills the circular shape
                        placeholder = painterResource(id = R.drawable.meal)
                    )
                }


                Column(

                    verticalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    Text(
                        text = "Kick start your day with today's special",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                    Text(
                        text = "Food Name: $foodTitle",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                    Text(
                        text = "Food category: $foodCategory",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )

                }


            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "This food is mainly originates from $area",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 8.sp),
                color = MaterialTheme.colorScheme.onPrimary,
            )
            Spacer(modifier = Modifier.height(8.dp))


        }
    }

}

@Composable
fun MealComponent(
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
                    .clip(RoundedCornerShape(10.dp))
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

@Preview
@Composable
fun PreviewHomeScreen() {
    FoodRecommenderAppTheme {
        HomeScreenContent(state = HomeState())
    }

}