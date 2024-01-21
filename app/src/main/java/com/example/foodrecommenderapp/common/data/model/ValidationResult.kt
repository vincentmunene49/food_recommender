package com.example.foodrecommenderapp.common.data.model

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
