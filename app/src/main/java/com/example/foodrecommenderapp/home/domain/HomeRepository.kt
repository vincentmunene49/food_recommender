package com.example.foodrecommenderapp.home.domain

import com.example.foodrecommenderapp.admin.menu.model.Category
import com.example.foodrecommenderapp.admin.menu.model.Menu
import com.example.foodrecommenderapp.common.Resource
import com.example.foodrecommenderapp.home.data.model.Preferences
import com.example.foodrecommenderapp.order.data.model.Order
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun searchMeal(searchTerm: String): Flow<Resource<List<Menu>>>

    suspend fun getMealCategories(): Flow<Resource<List<Category>>>

    suspend fun getMeals():Flow<Resource<List<Menu>>>

    suspend fun getMealsByCategory(category: String): Flow<Resource<List<Menu>>>

    suspend fun addOrder(order:Order): Flow<Resource<Order>>

    suspend fun getOrders(): Flow<Resource<List<Order>>>

    suspend fun deleteOrder(order: Order): Flow<Resource<Order>>

    suspend fun logout(): Flow<Resource<Unit>>

    suspend fun savePreferences(preferences: Map<String, List<String?>>): Flow<Resource<Preferences>>

    suspend fun getPreferences(): Flow<Resource<Preferences>>

}