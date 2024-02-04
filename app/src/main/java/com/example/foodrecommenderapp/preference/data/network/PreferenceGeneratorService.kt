package com.example.foodrecommenderapp.preference.data.network

import com.example.foodrecommenderapp.common.constants.secrets.API_KEY
import com.example.foodrecommenderapp.common.constants.secrets.APP_ID
import com.example.foodrecommenderapp.preference.data.pojo.GenerateMeal
import com.example.foodrecommenderapp.preference.data.pojo.Hit
import retrofit2.http.GET
import retrofit2.http.Query

interface PreferenceGeneratorService {

    @GET("/api/recipes/v2")
    suspend fun getFoodFromPreferences(
        @Query("type") type: String = "public",
        @Query("app_key") appId: String = API_KEY,
        @Query("app_id") appKey: String = APP_ID,
        @Query("health") health: List<String>,
        @Query("diet") diet: List<String>,
        @Query("cuisineType") cuisineType: List<String>,
        @Query("mealType") mealType: List<String>,
        @Query("dishType") dishType: List<String>
    ): GenerateMeal
}