package com.example.foodrecommenderapp.common.domain

import com.example.foodrecommenderapp.common.data.model.ValidationResult

interface FormValidatorRepository {
    fun validateEmail(email: String): ValidationResult
    fun validatePassword(password: String): ValidationResult
    fun validateUsername(username: String): ValidationResult
    fun validateConfirmPassword(password: String, confirmPassword: String): ValidationResult

    fun validateMealField(field: String): ValidationResult
}