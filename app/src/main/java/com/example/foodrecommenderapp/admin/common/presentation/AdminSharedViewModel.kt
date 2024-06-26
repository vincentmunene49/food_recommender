package com.example.foodrecommenderapp.admin.common.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.toLowerCase
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecommenderapp.admin.common.domain.AdminRepository
import com.example.foodrecommenderapp.admin.menu.model.Category
import com.example.foodrecommenderapp.admin.menu.model.Menu
import com.example.foodrecommenderapp.common.Resource
import com.example.foodrecommenderapp.common.UiEvent
import com.example.foodrecommenderapp.common.domain.FormValidatorRepository
import com.example.foodrecommenderapp.order.data.model.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AdminSharedViewModel @Inject constructor(
    private val adminRepository: AdminRepository,
    private val formValidator: FormValidatorRepository
) : ViewModel() {

    var state by mutableStateOf(AdminState())
        private set


    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getReports()
        getOrderReport()
    }

    fun onEvent(event: AdminEvents) {

        when (event) {
            is AdminEvents.OnAddMealName -> {
                state = state.copy(mealName = event.name)
                validateMealName()
            }

            is AdminEvents.OnAddCategory -> {
                state = state.copy(categoryName = event.category)
                validateCategory()
            }

            is AdminEvents.OnAddMealPrice -> {
                state = state.copy(mealPrice = event.price.toDouble())
                validatePrice()
            }

            is AdminEvents.OnCategoryImageSelected -> {
                state = state.copy(
                    categoryImage = event.imageByteArray,
                    categoryImageUri = event.imageUri
                )
                Timber.tag("MenuViewModel").d("Category uri: ${event.imageUri}")


            }

            is AdminEvents.OnFoodImageSelected -> {
                state = state.copy(foodImageUri = event.imageUri, foodImage = event.imageByteArray)
                Timber.tag("MenuViewModel").d("Image uri: ${event.imageUri}")
            }

            is AdminEvents.OnClickAddIngredient -> {
                val newIngredientList = state.ingredients
                newIngredientList.add(event.ingredient)
                state = state.copy(ingredients = newIngredientList)
            }

            is AdminEvents.OnClickSubmit -> {
                val isMealNameValid = validateMealName()
                val isCategoryValid = validateCategory()
                if (isMealNameValid && isCategoryValid) {
                    createMenu()
                }
            }

            is AdminEvents.OnDismissErrorDialog -> {
                state = state.copy(
                    showErrorDialog = false,
                    errorMessage = ""
                )
            }

            is AdminEvents.OnDismissSuccessDialog -> {
                state = state.copy(
                    showSuccessDialog = false
                )
            }

            is AdminEvents.OnAddIngredient -> {
                val newIngredient = state.ingredients
                newIngredient[event.index] = event.ingredient
                state = state.copy(ingredients = newIngredient)
            }

            AdminEvents.OnClickCousineDoneAction -> {
                state = state.copy(
                    isCousinePreferenceExpanded = false
                )
            }

            AdminEvents.OnClickDietDoneAction -> {
                state = state.copy(
                    isDietPreferenceExpanded = false
                )
            }

            AdminEvents.OnClickDishTypeDoneAction -> {
                state = state.copy(
                    isDishTypePreferenceExpanded = false
                )
            }

            AdminEvents.OnClickHealthDoneAction -> {
                state = state.copy(
                    isHealthPreferenceExpanded = false
                )
            }

            AdminEvents.OnClickMealTypeDoneAction -> {
                state = state.copy(
                    isMealTypePreferenceExpanded = false
                )
            }

            AdminEvents.OnCousineExpandedStateChange -> {
                state = state.copy(
                    isCousinePreferenceExpanded = !state.isCousinePreferenceExpanded
                )
            }

            is AdminEvents.OnDeselectCousinePreference -> {
                val list = state.selectedCousineListPreferences.toMutableList()
                list.remove(event.cousineItem)
                state = state.copy(
                    selectedCousineListPreferences = list
                )
            }

            is AdminEvents.OnDeselectDietPreference -> {
                val list = state.selectedDietListPreferences.toMutableList()
                list.remove(event.dietItem)
                state = state.copy(
                    selectedDietListPreferences = list
                )
            }

            is AdminEvents.OnDeselectDishTypePreference -> {
                val list = state.selectedDishTypePreferences.toMutableList()
                list.remove(event.dishTypeItem)
                state = state.copy(
                    selectedDishTypePreferences = list
                )
            }

            is AdminEvents.OnDeselectHealthPreference -> {
                val list = state.selectedHealthListPreferences.toMutableList()
                list.remove(event.healthItem)
                state = state.copy(
                    selectedHealthListPreferences = list
                )
            }

            is AdminEvents.OnDeselectMealTypePreference -> {
                val list = state.selectedMealTypePreferences.toMutableList()
                list.remove(event.mealTypeItem)
                state = state.copy(
                    selectedMealTypePreferences = list
                )
            }

            AdminEvents.OnDietExpandedStateChange -> {
                state = state.copy(
                    isDietPreferenceExpanded = !state.isDietPreferenceExpanded
                )
            }

            AdminEvents.OnDishTypeExpandedStateChange -> {
                state = state.copy(
                    isDishTypePreferenceExpanded = !state.isDishTypePreferenceExpanded
                )
            }

            AdminEvents.OnDismissCousineDropDown -> {
                state = state.copy(
                    isCousinePreferenceExpanded = false
                )
            }

            AdminEvents.OnDismissDietDropDown -> {
                state = state.copy(
                    isDietPreferenceExpanded = false
                )
            }

            AdminEvents.OnDismissDishTypeDropDown -> {
                state = state.copy(
                    isDishTypePreferenceExpanded = false
                )
            }

            AdminEvents.OnDismissHealthDropDown -> {
                state = state.copy(
                    isHealthPreferenceExpanded = false
                )
            }

            AdminEvents.OnDismissMealTypeDropDown -> {
                state = state.copy(
                    isMealTypePreferenceExpanded = false
                )
            }

            AdminEvents.OnHealthExpandedStateChange -> {
                state = state.copy(
                    isHealthPreferenceExpanded = !state.isHealthPreferenceExpanded
                )
            }

            AdminEvents.OnMealTypeExpandedStateChange -> {
                state = state.copy(
                    isMealTypePreferenceExpanded = !state.isMealTypePreferenceExpanded
                )
            }

            is AdminEvents.OnSelectCousinePreference -> {
                val list = state.selectedCousineListPreferences.toMutableList()
                list.add(event.cousineItem)
                state = state.copy(
                    selectedCousineListPreferences = list
                )
            }

            is AdminEvents.OnSelectDietPreference -> {
                val list = state.selectedDietListPreferences.toMutableList()
                list.add(event.dietItem)
                state = state.copy(
                    selectedDietListPreferences = list
                )
            }

            is AdminEvents.OnSelectDishTypePreference -> {
                val list = state.selectedDishTypePreferences.toMutableList()
                list.add(event.dishTypeItem)
                state = state.copy(
                    selectedDishTypePreferences = list
                )
            }

            is AdminEvents.OnSelectHealthPreference -> {
                val list = state.selectedHealthListPreferences.toMutableList()
                list.add(event.healthItem)
                state = state.copy(
                    selectedHealthListPreferences = list
                )
            }

            is AdminEvents.OnSelectMealTypePreference -> {
                val list = state.selectedMealTypePreferences.toMutableList()
                list.add(event.mealTypeItem)
                state = state.copy(
                    selectedMealTypePreferences = list
                )

            }

            is AdminEvents.OnSelectDate -> {
                state = state.copy(
                    date = event.date
                )
                getReports()
            }

            is AdminEvents.OnDeleteOrder -> {
                deleteOrder(event.order)
            }

            AdminEvents.OClickLogOut -> {
                logOut()
            }

        }
    }

    private fun createMenu() {
        val menu = Menu(
            name = state.mealName?.lowercase() ?: "",
            category = state.categoryName?.lowercase() ?: "",
            ingredients = state.ingredients.filter {
                it.isNotEmpty()
            },
            preferences = mapOf(
                "health" to state.selectedHealthListPreferences,
                "diet" to state.selectedDietListPreferences,
                "cousine" to state.selectedCousineListPreferences,
                "mealType" to state.selectedMealTypePreferences,
                "dishType" to state.selectedDishTypePreferences
            ),
            price = state.mealPrice,
            adminId = UUID.randomUUID().toString()
        )

        val category = Category(
            name = state.categoryName?.lowercase() ?: "",
            image = state.categoryImage.toString()
        )

        viewModelScope.launch {
            adminRepository.addMenu(
                menu = menu,
                foodImage = state.foodImage ?: byteArrayOf(),
                category = category,
                categoryImage = state.categoryImage ?: byteArrayOf()
            )
                .onEach { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            state = state.copy(isLoading = true)
                        }

                        is Resource.Success -> {
                            state = state.copy(
                                isLoading = false,
                                showSuccessDialog = true,
                                mealName = "",
                                categoryName = "",
                                foodImage = null,
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
                                .d("Error creating menu: ${resource.message}")
                        }
                    }

                }.launchIn(this)

        }


    }

    private fun getReports() {
        viewModelScope.launch {
            adminRepository.getReports(state.date).onEach { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        state = state.copy(
                            reports = resource.data!!,
                            isLoading = false,
                            showEmptyScreen = false
                        )
                        if (state.reports?.totalSearches == 0L) { // Check if reports is empty
                            state = state.copy(
                                showEmptyScreen = true
                            )
                        }

                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            errorMessage = resource.message ?: "",
                            showErrorDialog = true,
                            showEmptyScreen = false
                        )
                        Timber.tag("MenuViewModel")
                            .e("Get Reports ${resource.message}")
                    }
                }

            }.launchIn(this)
        }
    }

    private fun getOrderReport() {
        viewModelScope.launch {
            adminRepository.getOrderReport().onEach { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        state = state.copy(
                            orders = resource.data!!,
                            isLoading = false,
                            showEmptyScreen = false
                        )
                        Timber.tag("MenuViewModel")
                            .e("Get Reports ${resource.data}")
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            errorMessage = resource.message ?: "",
                            showErrorDialog = true,
                            showEmptyScreen = false
                        )
                        Timber.tag("MenuViewModel")
                            .e("Get Reports ${resource.message}")
                    }
                }

            }.launchIn(this)
        }
    }

    private fun deleteOrder(order: Order) {
        viewModelScope.launch {
            adminRepository.deleteOrder(order).onEach { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false
                        ).also {
                            getOrderReport()
                        }
                        Timber.tag("MenuViewModel")
                            .e("Deleted")
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            errorMessage = resource.message ?: "",
                            showErrorDialog = true
                        )
                        Timber.tag("MenuViewModel")
                            .e("Get Reports ${resource.message}")
                    }
                }

            }.launchIn(this)
        }
    }

    private fun logOut() {
        viewModelScope.launch {
            adminRepository.logOut().onEach { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false
                        )
                        _uiEvent.send(UiEvent.LogoutFromAdmin)
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            errorMessage = resource.message ?: "",
                            showErrorDialog = true
                        )
                        Timber.tag("MenuViewModel")
                            .e("Get Reports ${resource.message}")
                    }
                }

            }.launchIn(this)
        }
    }

    private fun validateMealName(): Boolean {
        val mealNameResult = formValidator.validateMealField(state.mealName ?: "")
        state = state.copy(mealNameErrorMessage = mealNameResult.errorMessage ?: "")
        return mealNameResult.successful
    }

    private fun validateCategory(): Boolean {
        val categoryResult = formValidator.validateMealField(state.categoryName ?: "")
        state = state.copy(categoryErrorMessage = categoryResult.errorMessage ?: "")
        return categoryResult.successful
    }

    private fun validatePrice(): Boolean {
        val priceResult = formValidator.validateMealPrice(state.mealPrice.toString())
        state = state.copy(mealPriceErrorMessage = priceResult.errorMessage ?: "")
        return priceResult.successful
    }
}