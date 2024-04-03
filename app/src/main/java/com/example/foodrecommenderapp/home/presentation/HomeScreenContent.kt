package com.example.foodrecommenderapp.home.presentation

import android.net.Uri
import androidx.compose.animation.AnimatedContent
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.foodrecommenderapp.R
import com.example.foodrecommenderapp.common.UiEvent
import com.example.foodrecommenderapp.common.constants.CUISINELISTLABELS
import com.example.foodrecommenderapp.common.constants.DIETLISTLABELS
import com.example.foodrecommenderapp.common.constants.DISHTYPELIST
import com.example.foodrecommenderapp.common.constants.HEALTHLISTLABELS
import com.example.foodrecommenderapp.common.constants.MEALTYPELIST
import com.example.foodrecommenderapp.common.presentation.components.RecommenderAppButton
import com.example.foodrecommenderapp.navigation.Route
import com.example.foodrecommenderapp.order.presentation.AddToOrderDialog
import com.example.foodrecommenderapp.ui.theme.FoodRecommenderAppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber


@Composable
fun HomeScreen(
    viewModel: HomeSharedViewModel,
    navController: NavController
) {
    HomeScreenContent(
        state = viewModel.state,
        onEvent = viewModel::onEvent,
        navController = navController,
        uiEvent = viewModel.uiEvent
    )

}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    state: HomeState,
    uiEvent: Flow<UiEvent>,
    onEvent: (HomeScreenEvents) -> Unit = { },
    navController: NavController
) {

    Scaffold(
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

        if (state.showAddToOrderDialog) {
            AddToOrderDialog(
                onDismissDialog = { onEvent(HomeScreenEvents.OnDismissAddToOrderDialog) },
                onClickCancel = { onEvent(HomeScreenEvents.OnClickCancelAddToOrder) },
                onClickConfirm = { onEvent(HomeScreenEvents.OnClickConfirmAddToOrder) },
                title = "Are you sure you want to order this meal?",
                confirmTitle = "Order",
                cancelTitle = "Cancel"
            )
        }


        if (state.showPreferencesDialog) {
            PreferencesComponent(
                onDismiss = { onEvent(HomeScreenEvents.OnDismissShowPreferencesDialog) },
                onClickOk = { onEvent(HomeScreenEvents.OnClickOk) },
                onClickHealthDoneAction = { onEvent(HomeScreenEvents.OnClickHealthDoneAction) },
                onClickDietDoneAction = { onEvent(HomeScreenEvents.OnClickDietDoneAction) },
                onClickCuisineDoneAction = { onEvent(HomeScreenEvents.OnClickCousineDoneAction) },
                onClickDishTypeDoneAction = { onEvent(HomeScreenEvents.OnClickDishTypeDoneAction) },
                onClickMealTypeDoneAction = { onEvent(HomeScreenEvents.OnClickMealTypeDoneAction) },
                selectedHealthList = state.selectedHealthListPreferences,
                selectedDietList = state.selectedDietListPreferences,
                selectedCuisineList = state.selectedCousineListPreferences,
                selectedDishTypeList = state.selectedDishTypePreferences,
                selectedMealTypeList = state.selectedMealTypePreferences,
                onSelectHealthMenuItem = { onEvent(HomeScreenEvents.OnSelectHealthPreference(it)) },
                onDeselectHealthMenuItem = { onEvent(HomeScreenEvents.OnDeselectHealthPreference(it)) },
                onSelectDietMenuItem = { onEvent(HomeScreenEvents.OnSelectDietPreference(it)) },
                onDeselectDietMenuItem = { onEvent(HomeScreenEvents.OnDeselectDietPreference(it)) },
                onSelectCuisineMenuItem = { onEvent(HomeScreenEvents.OnSelectCousinePreference(it)) },
                onDeselectCuisineMenuItem = {
                    onEvent(
                        HomeScreenEvents.OnDeselectCousinePreference(
                            it
                        )
                    )
                },
                onSelectDishTypeMenuItem = { onEvent(HomeScreenEvents.OnSelectDishTypePreference(it)) },
                onDeselectDishTypeMenuItem = {
                    onEvent(
                        HomeScreenEvents.OnDeselectDishTypePreference(
                            it
                        )
                    )
                },
                onSelectMealTypeMenuItem = { onEvent(HomeScreenEvents.OnSelectMealTypePreference(it)) },
                onDeselectMealTypeMenuItem = {
                    onEvent(
                        HomeScreenEvents.OnDeselectMealTypePreference(
                            it
                        )
                    )
                },
                onDismissHealthDropDown = { onEvent(HomeScreenEvents.OnDismissHealthDropDown) },
                onDismissDietDropDown = { onEvent(HomeScreenEvents.OnDismissDietDropDown) },
                onDismissCuisineDropDown = { onEvent(HomeScreenEvents.OnDismissCousineDropDown) },
                onDismissDishTypeDropDown = { onEvent(HomeScreenEvents.OnDismissDishTypeDropDown) },
                onDismissMealTypeDropDown = { onEvent(HomeScreenEvents.OnDismissMealTypeDropDown) },
                onHealthExpandedChange = { onEvent(HomeScreenEvents.OnHealthExpandedStateChange) },
                onDietExpandedChange = { onEvent(HomeScreenEvents.OnDietExpandedStateChange) },
                onCuisineExpandedChange = { onEvent(HomeScreenEvents.OnCousineExpandedStateChange) },
                onDishTypeExpandedChange = { onEvent(HomeScreenEvents.OnDishTypeExpandedStateChange) },
                onMealTypeExpandedChange = { onEvent(HomeScreenEvents.OnMealTypeExpandedStateChange) },
                isHealthPreferenceExpanded = state.isHealthPreferenceExpanded,
                isDietPreferenceExpanded = state.isDietPreferenceExpanded,
                isCuisinePreferenceExpanded = state.isCousinePreferenceExpanded,
                isDishTypePreferenceExpanded = state.isDishTypePreferenceExpanded,
                isMealTypePreferenceExpanded = state.isMealTypePreferenceExpanded

            )
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
            if (state.isPreferencesLoading) {
                LoadingComponent(
                    onDismiss = { onEvent(HomeScreenEvents.OnDismissShowPreferencesDialog) }
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
                items(state.categories ?: emptyList()) { category ->
                    category.image?.let { image ->
                        MealComponent(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            foodCategory = category.name,
                            foodImage = image,
                            onCLick = { onEvent(HomeScreenEvents.OnClickCategory(category.name)) }
                        )
                    }
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
                    items(mealList) { meal ->
                        meal.image?.let { image ->
                            AllMealsComponent(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                onClickFood = { onEvent(HomeScreenEvents.OnClickMenu(meal)) },
                                imagePath = image,
                                foodTitle = meal.name,
                                foodCategory = meal.category,
                                price = meal.price.toString()
                            )
                        }
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
    foodCategory: String,
    price: String
) {
    Card(
        modifier = modifier
            .clickable { onClickFood() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
        )
    ) {

        Column {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (imagePath.isNotBlank() && imagePath.isNotEmpty()) {
                    AsyncImage(
                        model = imagePath,
                        contentDescription = null,
                        contentScale = ContentScale.Crop, // Use Crop to ensure the image fills the circular shape
                        placeholder = painterResource(id = R.drawable.food)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Fastfood,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(
            ) {
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = foodTitle,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row {

                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = foodCategory,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                    Spacer(modifier = Modifier.width(18.dp))
                    Text(
                        text = "Ksh $price",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }


            }


        }
    }


}

@Composable
fun MealComponent(
    modifier: Modifier = Modifier,
    foodCategory: String,
    foodImage: String,
    onCLick: (String) -> Unit = {}
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(color = Color(0XFFF7F7F7))
                .clickable { onCLick(foodCategory) }
                .clip(RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (foodImage.isNotEmpty() && foodImage.isNotBlank()) {
                AsyncImage(
                    modifier = Modifier.size(60.dp),
                    model = foodImage,
                    contentDescription = null,
                    placeholder = painterResource(id = R.drawable.food),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Fastfood,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }


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
    onClickHealthDoneAction: () -> Unit,
    onClickDietDoneAction: () -> Unit,
    onClickCuisineDoneAction: () -> Unit,
    onClickDishTypeDoneAction: () -> Unit,
    onClickMealTypeDoneAction: () -> Unit,
    selectedHealthList: List<String>,
    selectedDietList: List<String>,
    selectedCuisineList: List<String>,
    selectedDishTypeList: List<String>,
    selectedMealTypeList: List<String>,
    onSelectHealthMenuItem: (String) -> Unit,
    onDeselectHealthMenuItem: (String) -> Unit,
    onSelectDietMenuItem: (String) -> Unit,
    onDeselectDietMenuItem: (String) -> Unit,
    onSelectCuisineMenuItem: (String) -> Unit,
    onDeselectCuisineMenuItem: (String) -> Unit,
    onSelectDishTypeMenuItem: (String) -> Unit,
    onDeselectDishTypeMenuItem: (String) -> Unit,
    onSelectMealTypeMenuItem: (String) -> Unit,
    onDeselectMealTypeMenuItem: (String) -> Unit,
    onDismissHealthDropDown: () -> Unit,
    onDismissDietDropDown: () -> Unit,
    onDismissCuisineDropDown: () -> Unit,
    onDismissDishTypeDropDown: () -> Unit,
    onDismissMealTypeDropDown: () -> Unit,
    onHealthExpandedChange: (Boolean) -> Unit,
    onDietExpandedChange: (Boolean) -> Unit,
    onCuisineExpandedChange: (Boolean) -> Unit,
    onDishTypeExpandedChange: (Boolean) -> Unit,
    onMealTypeExpandedChange: (Boolean) -> Unit,
    isHealthPreferenceExpanded: Boolean,
    isDietPreferenceExpanded: Boolean,
    isCuisinePreferenceExpanded: Boolean,
    isDishTypePreferenceExpanded: Boolean,
    isMealTypePreferenceExpanded: Boolean

) {

    val dropDownBoxList = listOf(
        HEALTHLISTLABELS.map { it.lowercase() },
        DIETLISTLABELS.map { it.lowercase() },
        CUISINELISTLABELS.map { it.lowercase() },
        DISHTYPELIST.map { it.lowercase() },
        MEALTYPELIST.map { it.lowercase() }
    )

    val placeHolderList = listOf("Health", "Diet", "Cousine", "Dish Type", "Meal Type")

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = modifier
                .fillMaxSize(),
            shape = RoundedCornerShape(16.dp),
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {

                    DropDownMenu(
                        modifier = Modifier.padding(top = 16.dp),
                        menuItems = dropDownBoxList[0],
                        onDismiss = onDismissHealthDropDown,
                        selectedItems = selectedHealthList,
                        onSelectMenuItem = onSelectHealthMenuItem,
                        onDeselectMenuItem = onDeselectHealthMenuItem,
                        onClickDone = { onClickHealthDoneAction() },
                        placeHolder = placeHolderList[0],
                        onExpandedChange = onHealthExpandedChange,
                        isExpanded = isHealthPreferenceExpanded

                    )

                    //fill drop down for diet

                    DropDownMenu(
                        menuItems = dropDownBoxList[1],
                        onDismiss = onDismissDietDropDown,
                        selectedItems = selectedDietList,
                        onSelectMenuItem = onSelectDietMenuItem,
                        onDeselectMenuItem = onDeselectDietMenuItem,
                        onClickDone = { onClickDietDoneAction() },
                        placeHolder = placeHolderList[1],
                        onExpandedChange = onDietExpandedChange,
                        isExpanded = isDietPreferenceExpanded

                    )

                    //fill drop down for cuisine

                    DropDownMenu(
                        menuItems = dropDownBoxList[2],
                        onDismiss = onDismissCuisineDropDown,
                        selectedItems = selectedCuisineList,
                        onSelectMenuItem = onSelectCuisineMenuItem,
                        onDeselectMenuItem = onDeselectCuisineMenuItem,
                        onClickDone = { onClickCuisineDoneAction() },
                        placeHolder = placeHolderList[2],
                        onExpandedChange = onCuisineExpandedChange,
                        isExpanded = isCuisinePreferenceExpanded

                    )

                    //fill drop down for dish type

                    DropDownMenu(
                        menuItems = dropDownBoxList[3],
                        onDismiss = onDismissDishTypeDropDown,
                        selectedItems = selectedDishTypeList,
                        onSelectMenuItem = onSelectDishTypeMenuItem,
                        onDeselectMenuItem = onDeselectDishTypeMenuItem,
                        onClickDone = { onClickDishTypeDoneAction() },
                        placeHolder = placeHolderList[3],
                        onExpandedChange = onDishTypeExpandedChange,
                        isExpanded = isDishTypePreferenceExpanded

                    )

                    //fill drop down for meal type

                    DropDownMenu(
                        menuItems = dropDownBoxList[4],
                        onDismiss = onDismissMealTypeDropDown,
                        selectedItems = selectedMealTypeList,
                        onSelectMenuItem = onSelectMealTypeMenuItem,
                        onDeselectMenuItem = onDeselectMealTypeMenuItem,
                        onClickDone = { onClickMealTypeDoneAction() },
                        placeHolder = placeHolderList[4],
                        onExpandedChange = onMealTypeExpandedChange,
                        isExpanded = isMealTypePreferenceExpanded

                    )


                }
            }

            RecommenderAppButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = { onClickOk() },
                text = "Submit"
            )
        }
    }


}

@Composable
fun LoadingComponent(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Text(
                text = "Loading...",
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                textAlign = TextAlign.Center,
            )
        }
    }

}

//exposed drop down menu
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenu(
    modifier: Modifier = Modifier,
    menuItems: List<String>,
    selectedItems: List<String>?,
    onSelectMenuItem: (String) -> Unit,
    onDeselectMenuItem: (String) -> Unit,
    onClickDone: () -> Unit,
    placeHolder: String,
    isExpanded: Boolean = false,
    onExpandedChange: (Boolean) -> Unit,
    onDismiss: () -> Unit
) {

    ExposedDropdownMenuBox(
        modifier = modifier.fillMaxWidth(),
        expanded = isExpanded,
        onExpandedChange = onExpandedChange
    ) {

        OutlinedTextField(
            value = selectedItems?.joinToString(",") ?: placeHolder,
            onValueChange = {},
            placeholder = {
                Text(
                    text = placeHolder,
                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                )
            },
            singleLine = true,
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { onDismiss() })
        {
            menuItems.forEach { menuItem ->
                AnimatedContent(
                    targetState = selectedItems?.contains(menuItem),
                    label = "Animate the selected item"
                ) { isSelected ->
                    if (isSelected == true) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = menuItem,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            },
                            onClick = {
                                onDeselectMenuItem(menuItem)
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        )
                    } else {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = menuItem,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            },
                            onClick = {
                                onSelectMenuItem(menuItem)
                            },
                        )
                    }
                }


            }
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = { onClickDone() }) {
                Text(text = "Done", color = MaterialTheme.colorScheme.onPrimary)
            }

        }


    }

    Spacer(modifier = Modifier.height(20.dp))
}


@Preview
@Composable
fun PreviewHomeScreen() {
    FoodRecommenderAppTheme {
        AllMealsComponent(
            onClickFood = { /*TODO*/ },
            imagePath = "",
            foodTitle = "Chicken Curry",
            foodCategory = " BEEF",
            price = "200"
        )
    }

}