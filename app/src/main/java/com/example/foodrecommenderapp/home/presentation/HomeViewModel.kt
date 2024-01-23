package com.example.foodrecommenderapp.home.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecommenderapp.common.Resource
import com.example.foodrecommenderapp.home.domain.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel(){

    var state by mutableStateOf(HomeState())
        private set

    init {
        getRandomMeal()
        getMealByFirstLetter("c")
        getMealCategories()
    }

    fun onEvent(event: HomeScreenEvents){
        when(event){
            is HomeScreenEvents.OnClickLaunch -> {
                state = state.copy(
                    showPreferencesDialog = true
                )
            }
            is HomeScreenEvents.OnMealTypeChanged -> {
                state = state.copy(
                    mealType = event.mealType
                )
            }
            is HomeScreenEvents.OnCousineTypeChanged -> {
                state = state.copy(
                    cousineType = event.cousineType
                )
            }
            is HomeScreenEvents.OnDietTypeChanged -> {
                state = state.copy(
                    dietType = event.dietType
                )
            }
            is HomeScreenEvents.OnAllergyTypeChanged -> {
                state = state.copy(
                    allergyType = event.allergyType
                )
            }
            is HomeScreenEvents.OnSearchTermChanged -> {
                state = state.copy(
                    searchTerm = event.searchTerm
                )
                getMealByFirstLetter(event.searchTerm)
            }

            HomeScreenEvents.OnDismissShowPreferencesDialog ->{
                state = state.copy(
                    showPreferencesDialog = false
                )

            }
        }
    }


    private fun getRandomMeal() {
        viewModelScope.launch {
            repository.getRandomMeal().onEach {
                when(it){
                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                            meals = it.data
                        )
                        Timber.tag("HomeViewModel").d("getRandomMeal: ${it.data}")
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            error = it.message ?: "An unexpected error occurred",
                            showErrorDialog = true
                        )
                        Timber.tag("HomeViewmodel").d("getRandomMealError: ${it.message}")
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

    private fun getMealByFirstLetter(searchTerm: String) {
        viewModelScope.launch {
            repository.getMealByFirstLetter(searchTerm).onEach {
                when(it){
                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                            meals = it.data
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
                when(it){
                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                            categories = it.data
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
                            isLoading = true
                        )
                    }
                }
            }.launchIn(this)
        }
    }
}