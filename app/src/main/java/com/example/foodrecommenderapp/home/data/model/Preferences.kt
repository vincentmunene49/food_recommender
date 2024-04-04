package com.example.foodrecommenderapp.home.data.model

data class Preferences(
    val id:String? = "",
    val userId:String? = "",
    val preferences: Map<String, List<String?>> = emptyMap(),
    )
