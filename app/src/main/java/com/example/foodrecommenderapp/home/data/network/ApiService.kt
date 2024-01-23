package com.example.foodrecommenderapp.home.data.network

import com.example.foodrecommenderapp.home.data.model.Category
import com.example.foodrecommenderapp.home.data.model.Meal
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("random.php")
    suspend fun getRandomMeal(): Meal

    @GET("search.php")
    suspend fun getMealsByFirstLetter(
        @Query("f") category: String
    ): Meal

    @GET("categories.php")
    suspend fun getCategories(): Category
}