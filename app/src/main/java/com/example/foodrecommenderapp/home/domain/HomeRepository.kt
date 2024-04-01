package com.example.foodrecommenderapp.home.domain

import com.example.foodrecommenderapp.admin.menu.model.Category
import com.example.foodrecommenderapp.admin.menu.model.Menu
import com.example.foodrecommenderapp.common.Resource
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun searchMeal(searchTerm: String): Flow<Resource<List<Menu>>>

    suspend fun getMealCategories(): Flow<Resource<List<Category>>>

    suspend fun getMeals():Flow<Resource<List<Menu>>>

    suspend fun getMealsByCategory(category: String): Flow<Resource<List<Menu>>>

}