package com.example.foodrecommenderapp.preference.data.pojo

data class Digest(
    val daily: Double,
    val hasRDI: Boolean,
    val label: String,
    val schemaOrgTag: String,
    val sub: List<com.example.foodrecommenderapp.preference.data.pojo.Sub>,
    val tag: String,
    val total: Double,
    val unit: String
)