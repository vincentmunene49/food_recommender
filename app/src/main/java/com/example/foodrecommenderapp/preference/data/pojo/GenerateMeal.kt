package com.example.foodrecommenderapp.preference.data.pojo

data class GenerateMeal(
    val links: Links,
    val count: Int,
    val from: Int,
    val hits: List<Hit>,
    val to: Int
)