package com.example.foodrecommenderapp.home.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecommenderapp.admin.menu.model.Menu
import com.example.foodrecommenderapp.common.Resource
import com.example.foodrecommenderapp.common.UiEvent
import com.example.foodrecommenderapp.home.domain.HomeRepository
import com.example.foodrecommenderapp.preference.domain.PreferenceRepository
import com.example.foodrecommenderapp.admin.report.model.Reports
import com.example.foodrecommenderapp.order.data.model.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeSharedViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val preferenceRepository: PreferenceRepository
) : ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getMeals()
        getMealCategories()
        getOrder()
    }

    fun onEvent(event: HomeScreenEvents) {
        when (event) {
            is HomeScreenEvents.OnClickLaunch -> {
                state = state.copy(
                    showPreferencesDialog = true
                )
            }

            is HomeScreenEvents.OnSearchTermChanged -> {
                state = state.copy(
                    searchTerm = event.searchTerm
                )
                if(event.searchTerm.isNotEmpty() || event.searchTerm.isNotBlank()){
                    getMealByFirstLetter(state.searchTerm)
                }else{
                    getMeals()
                }
            }

            HomeScreenEvents.OnClickPreferencesCard -> {
                state = state.copy(
                    showAddToOrderDialog = true
                )
            }


            HomeScreenEvents.OnDismissAddToOrderDialog -> {
                state = state.copy(
                    showAddToOrderDialog = false
                )
            }

            HomeScreenEvents.OnDismissShowPreferencesDialog -> {
                state = state.copy(
                    showPreferencesDialog = false
                )
            }

            HomeScreenEvents.OnClickOk -> {
                state = state.copy(
                    showPreferencesDialog = false,
                    totalSearches = state.totalSearches + 1
                )
                getMealByPreference().also {
                    saveReports()
                }
            }

            is HomeScreenEvents.OnDeselectCousinePreference -> {
                val list = state.selectedCousineListPreferences.toMutableList()
                list.remove(event.cousineItem)
                state = state.copy(
                    selectedCousineListPreferences = list
                )
            }

            is HomeScreenEvents.OnDeselectDietPreference -> {
                val list = state.selectedDietListPreferences.toMutableList()
                list.remove(event.dietItem)
                state = state.copy(
                    selectedDietListPreferences = list
                )
            }

            is HomeScreenEvents.OnDeselectDishTypePreference -> {
                val list = state.selectedDishTypePreferences.toMutableList()
                list.remove(event.dishTypeItem)
                state = state.copy(
                    selectedDishTypePreferences = list
                )
            }

            is HomeScreenEvents.OnDeselectHealthPreference -> {
                val list = state.selectedHealthListPreferences.toMutableList()
                list.remove(event.healthItem)
                state = state.copy(
                    selectedHealthListPreferences = list
                )
            }

            is HomeScreenEvents.OnDeselectMealTypePreference -> {
                val list = state.selectedMealTypePreferences.toMutableList()
                list.remove(event.mealTypeItem)
                state = state.copy(
                    selectedMealTypePreferences = list
                )
            }

            is HomeScreenEvents.OnSelectCousinePreference -> {
                val list = state.selectedCousineListPreferences.toMutableList()
                list.add(event.cousineItem)
                state = state.copy(
                    selectedCousineListPreferences = list
                )
            }

            is HomeScreenEvents.OnSelectDietPreference -> {
                val list = state.selectedDietListPreferences.toMutableList()
                list.add(event.dietItem)
                state = state.copy(
                    selectedDietListPreferences = list
                )
            }

            is HomeScreenEvents.OnSelectDishTypePreference -> {
                val list = state.selectedDishTypePreferences.toMutableList()
                list.add(event.dishTypeItem)
                state = state.copy(
                    selectedDishTypePreferences = list
                )
            }

            is HomeScreenEvents.OnSelectHealthPreference -> {
                val list = state.selectedHealthListPreferences.toMutableList()
                list.add(event.healthItem)
                state = state.copy(
                    selectedHealthListPreferences = list
                )
            }

            is HomeScreenEvents.OnSelectMealTypePreference -> {
                val list = state.selectedMealTypePreferences.toMutableList()
                list.add(event.mealTypeItem)
                state = state.copy(
                    selectedMealTypePreferences = list
                )

            }

            HomeScreenEvents.OnClickCousineDoneAction -> {
                state = state.copy(
                    isCousinePreferenceExpanded = false
                )
            }

            HomeScreenEvents.OnClickHealthDoneAction -> {
                state = state.copy(isHealthPreferenceExpanded = false)
            }

            HomeScreenEvents.OnClickDietDoneAction -> {
                state = state.copy(
                    isDietPreferenceExpanded = false
                )
            }

            HomeScreenEvents.OnClickDishTypeDoneAction -> {
                state = state.copy(
                    isDishTypePreferenceExpanded = false
                )
            }

            HomeScreenEvents.OnClickMealTypeDoneAction -> {
                state = state.copy(
                    isMealTypePreferenceExpanded = false
                )

            }

            HomeScreenEvents.OnCousineExpandedStateChange -> {
                state = state.copy(
                    isCousinePreferenceExpanded = !state.isCousinePreferenceExpanded
                )
            }

            HomeScreenEvents.OnDietExpandedStateChange -> {
                state = state.copy(
                    isDietPreferenceExpanded = !state.isDietPreferenceExpanded
                )
            }

            HomeScreenEvents.OnDishTypeExpandedStateChange -> {
                state = state.copy(
                    isDishTypePreferenceExpanded = !state.isDishTypePreferenceExpanded
                )
            }

            HomeScreenEvents.OnDismissCousineDropDown -> {
                state = state.copy(
                    isCousinePreferenceExpanded = false
                )
            }

            HomeScreenEvents.OnDismissDietDropDown -> {
                state = state.copy(
                    isDietPreferenceExpanded = false
                )
            }

            HomeScreenEvents.OnDismissDishTypeDropDown -> {
                state = state.copy(
                    isDishTypePreferenceExpanded = false
                )
            }

            HomeScreenEvents.OnDismissHealthDropDown -> {
                state = state.copy(
                    isHealthPreferenceExpanded = false
                )
            }

            HomeScreenEvents.OnDismissMealTypeDropDown -> {
                state = state.copy(
                    isMealTypePreferenceExpanded = false
                )
            }

            HomeScreenEvents.OnHealthExpandedStateChange -> {
                state = state.copy(
                    isHealthPreferenceExpanded = !state.isHealthPreferenceExpanded
                )
            }

            HomeScreenEvents.OnMealTypeExpandedStateChange -> {
                state = state.copy(
                    isMealTypePreferenceExpanded = !state.isMealTypePreferenceExpanded
                )
            }

            HomeScreenEvents.OnDismissPreferencesDialog -> {
                state = state.copy(
                    showPreferenceLoadingDialog = false
                )

            }

            is HomeScreenEvents.OnClickCategory ->{
                state = state.copy(
                    selectedCategory = event.categoryName
                )
                getMealByCategories(state.selectedCategory)
            }

            is HomeScreenEvents.OnClickMenu -> {
                state = state.copy(
                    menu = event.menu,
                    showAddToOrderDialog = true
                )

            }

            HomeScreenEvents.OnClickCancelAddToOrder -> {
                state = state.copy(
                    showAddToOrderDialog = false
                )
            }
            HomeScreenEvents.OnClickConfirmAddToOrder -> {
                addOrder()
                state = state.copy(
                    showAddToOrderDialog = false
                )
            }

            is HomeScreenEvents.DeleteOrder -> {
                deleteOrder(event.order)
            }
        }
    }


    private fun getMealByFirstLetter(searchTerm: String) {
        viewModelScope.launch {
            repository.searchMeal(searchTerm.lowercase()).onEach {
                when (it) {
                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                            meals = it.data
                        )

                        Timber.tag("HomeViewModel").d("getMealByFirstLetter: ${it.data}")
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            error = it.message ?: "An unexpected error occurred",
                            showErrorDialog = true
                        )

                        Timber.tag("HomeViewModel").d("getMealByFirstLetterError: ${it.message}")
                    }

                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = true
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    private fun getMealByCategories(category:String){
        viewModelScope.launch {
            repository.getMealsByCategory(category.lowercase()).onEach {
                when (it) {
                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                            meals = it.data
                        )

                        Timber.tag("HomeViewModel").d("getMealByCategories: ${it.data}")
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            error = it.message ?: "An unexpected error occurred",
                            showErrorDialog = true
                        )
                    }

                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = true
                        )
                    }
                }
            }.launchIn(this)
        }
    }


    private fun getMealCategories() {
        viewModelScope.launch {
            repository.getMealCategories().onEach {
                when (it) {
                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                            categories = it.data
                        )

                        Timber.tag("HomeViewModel").d("getMealCategories: ${it.data}")


                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            error = it.message ?: "An unexpected error occurred",
                            showErrorDialog = true
                        )
                    }

                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = true
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    private fun getMeals(){
        viewModelScope.launch {
            repository.getMeals().onEach {
                when (it) {
                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                            meals = it.data
                        )

                        Timber.tag("HomeViewModel").d("getMeals: ${it.data}")
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            error = it.message ?: "An unexpected error occurred",
                            showErrorDialog = true
                        )
                        Timber.tag("HomeViewModel").d("getMealsError: ${it.message}")
                    }

                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = true
                        )
                    }
                }
            }.launchIn(this)
        }
    }
    private fun getMealByPreference() {
        viewModelScope.launch {
            preferenceRepository.getMealByPreferences(
                cuisineType = state.selectedCousineListPreferences,
                diet = state.selectedDietListPreferences,
                dishType = state.selectedDishTypePreferences,
                health = state.selectedHealthListPreferences,
                mealType = state.selectedMealTypePreferences
            ).onEach {
                when (it) {
                    is Resource.Success -> {
                        state = state.copy(
                            isPreferencesLoading = false,
                            showPreferenceLoadingDialog = false,
                            meals = it.data?: emptyList()
                        )
                        Timber.tag("HomeViewModel").d("getMealByPreference: ${it.data}")
                        _uiEvent.send(UiEvent.OnSuccess("Navigate to preference meal screen"))
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isPreferencesLoading = false,
                            error = it.message ?: "An unexpected error occurred",
                            showErrorDialog = true,
                            showPreferenceLoadingDialog = false
                        )
                        Timber.tag("HomeViewModel").d("getMealByPreferenceError: ${it.message}")

                    }

                    is Resource.Loading -> {
                        state = state.copy(
                            isPreferencesLoading = true,
                            showPreferenceLoadingDialog = true
                        )
                        Timber.tag("HomeViewModel").d("getMealByPreference: loading...")
                    }
                }
            }.launchIn(
                this
            )
        }
    }

    private fun saveReports() {
        val reports = Reports(
            date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
            preferences = mapOf(
                "cuisineType" to state.selectedCousineListPreferences.groupingBy { it }.eachCount(),
                "diet" to state.selectedDietListPreferences.groupingBy { it }.eachCount(),
                "dishType" to state.selectedDishTypePreferences.groupingBy { it }.eachCount(),
                "health" to state.selectedHealthListPreferences.groupingBy { it }.eachCount(),
                "mealType" to state.selectedMealTypePreferences.groupingBy { it }.eachCount()
            ),
            totalSearches = state.totalSearches
        )
        viewModelScope.launch {
            preferenceRepository.saveReports(
                reports
            ).onEach {
                when (it) {
                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false
                        )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            error = it.message ?: "An unexpected error occurred",
                            showErrorDialog = true
                        )
                    }

                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = true,
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    private fun addOrder() {
        val order = Order(
            menu = state.menu ?: Menu()
        )
        viewModelScope.launch {
            repository.addOrder(order).onEach {
                when (it) {
                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false
                        )
                        getOrder()
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            error = it.message ?: "An unexpected error occurred",
                            showErrorDialog = true
                        )
                    }

                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = true
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    private fun getOrder(){
        viewModelScope.launch {
            repository.getOrders().onEach {
                when (it) {
                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                            orders = it.data,
                            totalPrice = it.data?.sumOf { order -> order.menu.price ?: 0.0 } ?: 0.0
                        )

                        Timber.tag("HomeViewModel").d("getOrders: ${it.data}")
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            error = it.message ?: "An unexpected error occurred",
                            showErrorDialog = true
                        )
                        Timber.tag("HomeViewModel").d("getOrdersError: ${it.message}")
                    }

                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = true
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    private fun deleteOrder(order: Order){
        viewModelScope.launch {
            repository.deleteOrder(order).onEach {
                when (it) {
                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false
                        ).also {
                            getOrder()
                        }
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            error = it.message ?: "An unexpected error occurred",
                            showErrorDialog = true
                        )
                    }

                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = true
                        )
                    }
                }
            }.launchIn(this)
        }
    }

}