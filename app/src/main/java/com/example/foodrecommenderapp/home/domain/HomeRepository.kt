package com.example.foodrecommenderapp.home.domain

import com.example.foodrecommenderapp.common.Resource
import com.example.foodrecommenderapp.home.data.model.Meal
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getRandomMeal():Flow<Resource<Meal>>

    suspend fun getMealByFirstLetter(searchTerm: String): Flow<Resource<Meal>>

}