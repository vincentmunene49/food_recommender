package com.example.foodrecommenderapp.admin.menu.model

import android.net.Uri


data class Menu(
    val id: String = "",
    val name: String = "",
    val category: String = "",
    val categoryImage: String? = null,
    val image: String? = null,
    val ingredients: List<String> = emptyList(),
    val preferences: Map<String, List<String>> = emptyMap(),
    val price:Double? = null,
    val adminId: String = "",
) {
}
