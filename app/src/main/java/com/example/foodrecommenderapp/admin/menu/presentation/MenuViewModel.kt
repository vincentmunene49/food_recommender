package com.example.foodrecommenderapp.admin.menu.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecommenderapp.admin.menu.domain.MenuRepository
import com.example.foodrecommenderapp.admin.menu.model.Menu
import com.example.foodrecommenderapp.common.Resource
import com.example.foodrecommenderapp.common.domain.FormValidatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val menuRepository: MenuRepository,
    private val formValidator: FormValidatorRepository
) : ViewModel() {

    var state by mutableStateOf(MenuState())
        private set

    fun onEvent(event: MenuEvents) {

        when (event) {
            is MenuEvents.OnAddMealName -> {
                state = state.copy(mealName = event.name)
                validateMealName()
            }

            is MenuEvents.OnAddCategory -> {
                state = state.copy(mealCategory = event.category)
                validateCategory()
            }

            is MenuEvents.OnImageSelected -> {
                state = state.copy(image = event.imageUri)
                Timber.tag("MenuViewModel").d("Image uri: ${event.imageUri}")
            }

            is MenuEvents.OnClickAddIngredient -> {
                val newIngredientList = state.ingredients
                newIngredientList.add(event.ingredient)
                state = state.copy(ingredients = newIngredientList)
            }

            is MenuEvents.OnClickSubmit -> {
                val isMealNameValid = validateMealName()
                val isCategoryValid = validateCategory()
                if (isMealNameValid && isCategoryValid) {
                    createMenu()
                }
            }

            is MenuEvents.OnDismissErrorDialog -> {
                state = state.copy(
                    showErrorDialog = false,
                    errorMessage = ""
                )
            }

            is MenuEvents.OnDismissSuccessDialog -> {
                state = state.copy(
                    showSuccessDialog = false
                )
            }

            is MenuEvents.OnAddIngredient -> {
                val newIngredient = state.ingredients
                newIngredient[event.index] = event.ingredient
                state = state.copy(ingredients = newIngredient)
            }

            MenuEvents.OnClickCousineDoneAction -> {
                state = state.copy(
                    isCousinePreferenceExpanded = false
                )
            }

            MenuEvents.OnClickDietDoneAction -> {
                state = state.copy(
                    isDietPreferenceExpanded = false
                )
            }

            MenuEvents.OnClickDishTypeDoneAction -> {
                state = state.copy(
                    isDishTypePreferenceExpanded = false
                )
            }

            MenuEvents.OnClickHealthDoneAction -> {
                state = state.copy(
                    isHealthPreferenceExpanded = false
                )
            }

            MenuEvents.OnClickMealTypeDoneAction -> {
                state = state.copy(
                    isMealTypePreferenceExpanded = false
                )
            }

            MenuEvents.OnCousineExpandedStateChange -> {
                state = state.copy(
                    isCousinePreferenceExpanded = !state.isCousinePreferenceExpanded
                )
            }

            is MenuEvents.OnDeselectCousinePreference -> {
                val list = state.selectedCousineListPreferences.toMutableList()
                list.remove(event.cousineItem)
                state = state.copy(
                    selectedCousineListPreferences = list
                )
            }

            is MenuEvents.OnDeselectDietPreference -> {
                val list = state.selectedDietListPreferences.toMutableList()
                list.remove(event.dietItem)
                state = state.copy(
                    selectedDietListPreferences = list
                )
            }

            is MenuEvents.OnDeselectDishTypePreference -> {
                val list = state.selectedDishTypePreferences.toMutableList()
                list.remove(event.dishTypeItem)
                state = state.copy(
                    selectedDishTypePreferences = list
                )
            }

            is MenuEvents.OnDeselectHealthPreference -> {
                val list = state.selectedHealthListPreferences.toMutableList()
                list.remove(event.healthItem)
                state = state.copy(
                    selectedHealthListPreferences = list
                )
            }

            is MenuEvents.OnDeselectMealTypePreference -> {
                val list = state.selectedMealTypePreferences.toMutableList()
                list.remove(event.mealTypeItem)
                state = state.copy(
                    selectedMealTypePreferences = list
                )
            }

            MenuEvents.OnDietExpandedStateChange -> {
                state = state.copy(
                    isDietPreferenceExpanded = !state.isDietPreferenceExpanded
                )
            }

            MenuEvents.OnDishTypeExpandedStateChange -> {
                state = state.copy(
                    isDishTypePreferenceExpanded = !state.isDishTypePreferenceExpanded
                )
            }

            MenuEvents.OnDismissCousineDropDown -> {
                state = state.copy(
                    isCousinePreferenceExpanded = false
                )
            }

            MenuEvents.OnDismissDietDropDown -> {
                state = state.copy(
                    isDietPreferenceExpanded = false
                )
            }

            MenuEvents.OnDismissDishTypeDropDown -> {
                state = state.copy(
                    isDishTypePreferenceExpanded = false
                )
            }

            MenuEvents.OnDismissHealthDropDown -> {
                state = state.copy(
                    isHealthPreferenceExpanded = false
                )
            }

            MenuEvents.OnDismissMealTypeDropDown -> {
                state = state.copy(
                    isMealTypePreferenceExpanded = false
                )
            }

            MenuEvents.OnHealthExpandedStateChange -> {
                state = state.copy(
                    isHealthPreferenceExpanded = !state.isHealthPreferenceExpanded
                )
            }

            MenuEvents.OnMealTypeExpandedStateChange -> {
                state = state.copy(
                    isMealTypePreferenceExpanded = !state.isMealTypePreferenceExpanded
                )
            }

            is MenuEvents.OnSelectCousinePreference -> {
                val list = state.selectedCousineListPreferences.toMutableList()
                list.add(event.cousineItem)
                state = state.copy(
                    selectedCousineListPreferences = list
                )
            }

            is MenuEvents.OnSelectDietPreference -> {
                val list = state.selectedDietListPreferences.toMutableList()
                list.add(event.dietItem)
                state = state.copy(
                    selectedDietListPreferences = list
                )
            }

            is MenuEvents.OnSelectDishTypePreference -> {
                val list = state.selectedDishTypePreferences.toMutableList()
                list.add(event.dishTypeItem)
                state = state.copy(
                    selectedDishTypePreferences = list
                )
            }

            is MenuEvents.OnSelectHealthPreference -> {
                val list = state.selectedHealthListPreferences.toMutableList()
                list.add(event.healthItem)
                state = state.copy(
                    selectedHealthListPreferences = list
                )
            }

            is MenuEvents.OnSelectMealTypePreference -> {
                val list = state.selectedMealTypePreferences.toMutableList()
                list.add(event.mealTypeItem)
                state = state.copy(
                    selectedMealTypePreferences = list
                )

            }
        }
    }

    private fun createMenu() {
        val menu = Menu(
            name = state.mealName ?: "",
            category = state.mealCategory ?: "",
            image = state.image,
            ingredients = state.ingredients.filter {
                it.isNotEmpty()
            },
            preferences = mapOf(
                "health" to state.selectedHealthListPreferences,
                "diet" to state.selectedDietListPreferences,
                "cousine" to state.selectedCousineListPreferences,
                "mealType" to state.selectedMealTypePreferences,
                "dishType" to state.selectedDishTypePreferences
            )
        )
        viewModelScope.launch {
            state.image?.let {
                menuRepository.addMenu(menu, it).onEach { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            state = state.copy(isLoading = true)
                        }

                        is Resource.Success -> {
                            state = state.copy(
                                isLoading = false,
                                showSuccessDialog = true,
                                mealName = "",
                                mealCategory = "",
                                image = null,
                                ingredients = mutableStateListOf(),
                                selectedHealthListPreferences = emptyList(),
                                selectedDietListPreferences = emptyList(),
                                selectedCousineListPreferences = emptyList(),
                                selectedMealTypePreferences = emptyList(),
                                selectedDishTypePreferences = emptyList()
                            )

                        }

                        is Resource.Error -> {
                            state = state.copy(
                                isLoading = false,
                                errorMessage = resource.message ?: "",
                                showErrorDialog = true
                            )
                            Timber.tag("MenuViewModel")
                                .e("Error creating menu: ${resource.message}")
                        }
                    }

                }.launchIn(this)
            }
        }


    }

    private fun validateMealName(): Boolean {
        val mealNameResult = formValidator.validateMealField(state.mealName ?: "")
        state = state.copy(mealNameErrorMessage = mealNameResult.errorMessage ?: "")
        return mealNameResult.successful
    }

    private fun validateCategory(): Boolean {
        val categoryResult = formValidator.validateMealField(state.mealCategory ?: "")
        state = state.copy(categoryErrorMessage = categoryResult.errorMessage ?: "")
        return categoryResult.successful
    }
}