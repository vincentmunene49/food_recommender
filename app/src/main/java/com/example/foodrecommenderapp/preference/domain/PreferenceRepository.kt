package com.example.foodrecommenderapp.preference.domain

import com.example.foodrecommenderapp.admin.menu.model.Menu
import com.example.foodrecommenderapp.common.Resource
import com.example.foodrecommenderapp.preference.data.pojo.GenerateMeal
import com.example.foodrecommenderapp.preference.data.pojo.Hit
import com.example.foodrecommenderapp.admin.report.model.Reports
import com.example.foodrecommenderapp.home.data.model.Meal
import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {

    suspend fun getMealByPreferences(
        health: List<String>,
        diet: List<String>,
        cuisineType: List<String>,
        mealType: List<String>,
        dishType: List<String>
    ): Flow<Resource<GenerateMeal>>

    suspend fun saveReports(
        reports: Reports
    ): Flow<Resource<Unit>>


    suspend fun getAllMenus(): Flow<Resource<List<Menu>>>
}
