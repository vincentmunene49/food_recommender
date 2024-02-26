package com.example.foodrecommenderapp.admin.menu.presentation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.foodrecommenderapp.R
import com.example.foodrecommenderapp.common.constants.CUISINELISTLABELS
import com.example.foodrecommenderapp.common.constants.DIETLISTLABELS
import com.example.foodrecommenderapp.common.constants.DISHTYPELIST
import com.example.foodrecommenderapp.common.constants.HEALTHLISTLABELS
import com.example.foodrecommenderapp.common.constants.MEALTYPELIST
import com.example.foodrecommenderapp.common.presentation.components.RecommenderAppButton
import com.example.foodrecommenderapp.common.presentation.components.RecommenderAppTextField
import com.example.foodrecommenderapp.home.presentation.DropDownMenu
import com.example.foodrecommenderapp.ui.theme.FoodRecommenderAppTheme

@Composable
fun MenuCreationScreen() {

}

@Composable
fun MenuCreationScreenContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    onClickOpenImagePicker: () -> Unit = {},
    selectedImageUri: String
) {

    val focusManager = LocalFocusManager.current

    var textFieldValues by remember { mutableStateOf(emptyList<String>()) }

    val dropDownBoxList = listOf(
        HEALTHLISTLABELS.map{it.lowercase()},
        DIETLISTLABELS.map { it.lowercase() },
        CUISINELISTLABELS.map { it.lowercase() },
        DISHTYPELIST.map { it.lowercase() },
        MEALTYPELIST.map { it.lowercase() }
    )

    val placeHolderList = listOf("Health", "Diet", "Cousine", "Dish Type", "Meal Type")


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
                text = "Create Menu",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp)
            )

        }
    }) { paddingValues ->

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {

                    Box(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = CircleShape
                            )
                            .clickable { onClickOpenImagePicker() }
                            .clip(CircleShape)
                    ) {
                        AsyncImage(
                            modifier = Modifier.padding(30.dp),
                            model = selectedImageUri,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            placeholder = painterResource(id = R.drawable.add_photo)
                        )

                    }
                }
            }

            item {
                RecommenderAppTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    value = "meal Name",
                    onValueChange = {},
                    label = {
                        Text(text = "Meal Name")
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
                RecommenderAppTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    value = "meal category",
                    onValueChange = {},
                    label = {
                        Text(text = "Meal Category")
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

            items(textFieldValues.size) { index ->
                RecommenderAppTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    value = textFieldValues[index],
                    onValueChange = { newValue ->
                        textFieldValues = textFieldValues.toMutableList().also {
                            it[index] = newValue
                        }
                    },
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
                        .clickable { textFieldValues = textFieldValues + "" }
                        .clip(CircleShape),
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
                    onDismiss = {  },
                    selectedItems = emptyList(),
                    onSelectMenuItem = {},
                    onDeselectMenuItem = {},
                    onClickDone = {  },
                    placeHolder = placeHolderList[0],
                    onExpandedChange = {},
                    isExpanded = false

                )

                //fill drop down for diet

                DropDownMenu(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    menuItems = dropDownBoxList[1],
                    onDismiss = {  },
                    selectedItems = emptyList(),
                    onSelectMenuItem = {},
                    onDeselectMenuItem = {},
                    onClickDone = {  },
                    placeHolder = placeHolderList[1],
                    onExpandedChange = {},
                    isExpanded = false

                )

                //fill drop down for cuisine
                DropDownMenu(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    menuItems = dropDownBoxList[2],
                    onDismiss = {  },
                    selectedItems = emptyList(),
                    onSelectMenuItem = {},
                    onDeselectMenuItem = {},
                    onClickDone = {  },
                    placeHolder = placeHolderList[1],
                    onExpandedChange = {},
                    isExpanded = false

                )

                //fill drop down for dish type

                DropDownMenu(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    menuItems = dropDownBoxList[3],
                    onDismiss = {  },
                    selectedItems = emptyList(),
                    onSelectMenuItem = {},
                    onDeselectMenuItem = {},
                    onClickDone = {  },
                    placeHolder = placeHolderList[1],
                    onExpandedChange = {},
                    isExpanded = false

                )

                //fill drop down for meal type

                DropDownMenu(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    menuItems = dropDownBoxList[4],
                    onDismiss = {  },
                    selectedItems = emptyList(),
                    onSelectMenuItem = {},
                    onDeselectMenuItem = {},
                    onClickDone = {  },
                    placeHolder = placeHolderList[4],
                    onExpandedChange = {},
                    isExpanded = false

                )

                RecommenderAppButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onClick = {  },
                    text = "Submit"
                )
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
            navController = rememberNavController(),
            selectedImageUri = ""
        )
    }

}