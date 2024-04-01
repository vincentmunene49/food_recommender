package com.example.foodrecommenderapp.admin.menu.presentation

import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.foodrecommenderapp.R
import com.example.foodrecommenderapp.admin.common.presentation.AdminEvents
import com.example.foodrecommenderapp.admin.common.presentation.AdminSharedViewModel
import com.example.foodrecommenderapp.admin.common.presentation.AdminState
import com.example.foodrecommenderapp.common.constants.CUISINELISTLABELS
import com.example.foodrecommenderapp.common.constants.DIETLISTLABELS
import com.example.foodrecommenderapp.common.constants.DISHTYPELIST
import com.example.foodrecommenderapp.common.constants.HEALTHLISTLABELS
import com.example.foodrecommenderapp.common.constants.MEALTYPELIST
import com.example.foodrecommenderapp.common.presentation.components.ErrorDialog
import com.example.foodrecommenderapp.common.presentation.components.RecommenderAppButton
import com.example.foodrecommenderapp.common.presentation.components.RecommenderAppTextField
import com.example.foodrecommenderapp.home.presentation.DropDownMenu
import com.example.foodrecommenderapp.ui.theme.FoodRecommenderAppTheme

@Composable
fun MenuCreationScreen(
    viewModel: AdminSharedViewModel
) {
    MenuCreationScreenContent(
        state = viewModel.state,
        onEvent = viewModel::onEvent,
    )
}

@Composable
fun MenuCreationScreenContent(
    modifier: Modifier = Modifier,
    state: AdminState = AdminState(),
    onEvent: (AdminEvents) -> Unit = {}
) {

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    val dropDownBoxList = listOf(
        HEALTHLISTLABELS.map { it.lowercase() },
        DIETLISTLABELS.map { it.lowercase() },
        CUISINELISTLABELS.map { it.lowercase() },
        DISHTYPELIST.map { it.lowercase() },
        MEALTYPELIST.map { it.lowercase() }
    )

    val placeHolderList = listOf("Health", "Diet", "Cousine", "Dish Type", "Meal Type")
    val mealNameInteractionSource = remember { MutableInteractionSource() }
    val isMealName by mealNameInteractionSource.collectIsFocusedAsState()
    val categoryInteractionSource = remember { MutableInteractionSource() }
    val isCategoryFocused by categoryInteractionSource.collectIsFocusedAsState()

    val imagePicker =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            val byteArray:ByteArray? = uri?.let {
                context.contentResolver.openInputStream(it)?.use {inputStream->
                    inputStream.readBytes()
                }
            }
            onEvent(AdminEvents.OnFoodImageSelected(uri,byteArray))
        }

    val categoryImagePicker = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
        val byteArray:ByteArray? = uri?.let {
            context.contentResolver.openInputStream(it)?.use {inputStream->
                inputStream.readBytes()
            }
        }
        onEvent(AdminEvents.OnCategoryImageSelected(byteArray,uri))
    }

    Box(
        modifier = modifier
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

        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center) {

                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = CircleShape
                            )
                            .clickable {
                                imagePicker.launch("image/*")
                            }
                            .size(120.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (mutableStateOf(state.foodImageUri).value == null) {
                            Image(
                                painter = painterResource(id = R.drawable.add_photo),
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            AsyncImage(
                                model = mutableStateOf(state.foodImage).value ?: "",
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                placeholder = painterResource(id = R.drawable.add_photo)
                            )
                        }

                    }
                }
            }

            item {
                RecommenderAppTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    value = state.mealName ?: "",
                    onValueChange = {
                        onEvent(AdminEvents.OnAddMealName(it))
                    },
                    label = {
                        Text(text = "Meal Name")
                    },
                    isError = isMealName && state.mealNameErrorMessage != null,
                    supportingText = {
                        state.mealNameErrorMessage?.let {
                            Text(text = it, color = MaterialTheme.colorScheme.error)
                        }

                    },
                    textStyle = MaterialTheme.typography.bodyMedium,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Down) }
                    ),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.primary
                    )
                )
            }



            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = CircleShape
                            )
                            .clickable {
                                categoryImagePicker.launch("image/*")
                                //call viewmodle to upload image
                            }
                            .size(50.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (mutableStateOf(state.categoryImageUri).value == null) {
                            Image(
                                painter = painterResource(id = R.drawable.add_photo),
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            AsyncImage(
                                model = mutableStateOf(state.foodImage).value ?: "",
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                placeholder = painterResource(id = R.drawable.add_photo)
                            )
                        }

                    }

                    RecommenderAppTextField(
                        modifier = Modifier.weight(1f),
                        value = state.categoryName ?: "",
                        onValueChange = {
                            onEvent(AdminEvents.OnAddCategory(it))
                        },
                        label = {
                            Text(text = "Meal Category")
                        },
                        isError = isCategoryFocused && state.categoryErrorMessage != null,
                        supportingText = {
                            state.categoryErrorMessage?.let {
                                Text(text = it, color = MaterialTheme.colorScheme.error)
                            }

                        },
                        textStyle = MaterialTheme.typography.bodyMedium,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }
                        ),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            errorContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }

            }

            item {
                Divider()
            }

            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                    text = "Ingredients",
                    style = MaterialTheme.typography.titleSmall.copy(fontSize = 18.sp)
                )
            }

            itemsIndexed(state.ingredients) { index, textField ->
                RecommenderAppTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    value = textField,
                    onValueChange = { newValue ->
                        onEvent(AdminEvents.OnAddIngredient(index, newValue))
                    },
                    label = { Text(text = "Ingredient ${index + 1}") },
                    textStyle = MaterialTheme.typography.bodyMedium,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.primary
                    )
                )
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                        .clip(CircleShape)
                        .clickable { onEvent(AdminEvents.OnClickAddIngredient("")) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                    ) {
                        Icon(
                            modifier = Modifier.padding(5.dp),
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Add Ingredient",
                        color = MaterialTheme.colorScheme.primary
                    )
                }

            }

            item {
                Divider()
            }

            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                    text = "Preferences",
                    style = MaterialTheme.typography.titleSmall.copy(fontSize = 18.sp)
                )
            }
            item {

                DropDownMenu(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    menuItems = dropDownBoxList[0],
                    onDismiss = { onEvent(AdminEvents.OnDismissHealthDropDown) },
                    selectedItems = state.selectedHealthListPreferences,
                    onSelectMenuItem = { onEvent(AdminEvents.OnSelectHealthPreference(it)) },
                    onDeselectMenuItem = { onEvent(AdminEvents.OnDeselectHealthPreference(it)) },
                    onClickDone = { onEvent(AdminEvents.OnClickHealthDoneAction) },
                    placeHolder = placeHolderList[0],
                    onExpandedChange = { onEvent(AdminEvents.OnHealthExpandedStateChange) },
                    isExpanded = state.isHealthPreferenceExpanded

                )
            }

            //fill drop down for diet
            item {
                DropDownMenu(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    menuItems = dropDownBoxList[1],
                    onDismiss = { onEvent(AdminEvents.OnDismissDietDropDown) },
                    selectedItems = state.selectedDietListPreferences,
                    onSelectMenuItem = { onEvent(AdminEvents.OnSelectDietPreference(it)) },
                    onDeselectMenuItem = { onEvent(AdminEvents.OnDeselectDietPreference(it)) },
                    onClickDone = { onEvent(AdminEvents.OnClickDietDoneAction) },
                    placeHolder = placeHolderList[1],
                    onExpandedChange = { onEvent(AdminEvents.OnDietExpandedStateChange) },
                    isExpanded = state.isDietPreferenceExpanded

                )
            }

            //fill drop down for cuisine
            item {
                DropDownMenu(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    menuItems = dropDownBoxList[2],
                    onDismiss = { onEvent(AdminEvents.OnDismissCousineDropDown) },
                    selectedItems = state.selectedCousineListPreferences,
                    onSelectMenuItem = { onEvent(AdminEvents.OnSelectCousinePreference(it)) },
                    onDeselectMenuItem = { onEvent(AdminEvents.OnDeselectCousinePreference(it)) },
                    onClickDone = { onEvent(AdminEvents.OnClickCousineDoneAction) },
                    placeHolder = placeHolderList[2],
                    onExpandedChange = { onEvent(AdminEvents.OnCousineExpandedStateChange) },
                    isExpanded = state.isCousinePreferenceExpanded

                )
            }

            //fill drop down for dish type
            item {
                DropDownMenu(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    menuItems = dropDownBoxList[3],
                    onDismiss = { onEvent(AdminEvents.OnDismissDishTypeDropDown) },
                    selectedItems = state.selectedDishTypePreferences,
                    onSelectMenuItem = { onEvent(AdminEvents.OnSelectDishTypePreference(it)) },
                    onDeselectMenuItem = { onEvent(AdminEvents.OnDeselectDishTypePreference(it)) },
                    onClickDone = { onEvent(AdminEvents.OnClickDishTypeDoneAction) },
                    placeHolder = placeHolderList[3],
                    onExpandedChange = { onEvent(AdminEvents.OnDishTypeExpandedStateChange) },
                    isExpanded = state.isDishTypePreferenceExpanded

                )
            }

            //fill drop down for meal type
            item {
                DropDownMenu(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    menuItems = dropDownBoxList[4],
                    onDismiss = { onEvent(AdminEvents.OnDismissMealTypeDropDown) },
                    selectedItems = state.selectedMealTypePreferences,
                    onSelectMenuItem = { onEvent(AdminEvents.OnSelectMealTypePreference(it)) },
                    onDeselectMenuItem = { onEvent(AdminEvents.OnDeselectMealTypePreference(it)) },
                    onClickDone = { onEvent(AdminEvents.OnClickMealTypeDoneAction) },
                    placeHolder = placeHolderList[4],
                    onExpandedChange = { onEvent(AdminEvents.OnMealTypeExpandedStateChange) },
                    isExpanded = state.isMealTypePreferenceExpanded

                )
            }
            item {
                RecommenderAppButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onClick = { onEvent(AdminEvents.OnClickSubmit) },
                    text = "Submit"
                )
            }


            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }

    }

}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewMenuCreationScreen() {

    FoodRecommenderAppTheme {
        MenuCreationScreenContent(
        )
    }

}