package com.example.foodrecommenderapp.preference.domain

import com.example.foodrecommenderapp.common.Resource
import com.example.foodrecommenderapp.preference.data.pojo.GenerateMeal
import com.example.foodrecommenderapp.preference.data.pojo.Hit
import com.example.foodrecommenderapp.report.model.Reports
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
}
