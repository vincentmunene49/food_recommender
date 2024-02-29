package com.example.foodrecommenderapp.common.data

import android.util.Patterns
import com.example.foodrecommenderapp.common.data.model.ValidationResult
import com.example.foodrecommenderapp.common.domain.FormValidatorRepository

class DefaultFormValidatorRepositoryImplementation : FormValidatorRepository{

        override fun validateEmail(email: String): ValidationResult {
            if(email.isBlank()){
                return ValidationResult(
                    successful = false,
                    errorMessage = "The email can not be blank"
                )

            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                return ValidationResult(
                    successful = false,
                    errorMessage = "That's not a valid email"
                )
            }
            return ValidationResult(
                successful = true,
                errorMessage = null
            )
        }

        override fun validatePassword(password: String): ValidationResult {
            if(password.isBlank()){
                return ValidationResult(
                    successful = false,
                    errorMessage = "The password can not be blank"
                )
            }
            if(password.length < 8){
                return ValidationResult(
                    successful = false,
                    errorMessage = "The password must be at least 8 characters long"
                )
            }
            return ValidationResult(
                successful = true,
                errorMessage = null
            )
        }

        override fun validateUsername(username: String): ValidationResult {
            if(username.isBlank()){
                return ValidationResult(
                    successful = false,
                    errorMessage = "The username can not be blank"
                )
            }
            if(username.length < 1){
                return ValidationResult(
                    successful = false,
                    errorMessage = "The username must be at least 1 characters long"
                )
            }
            return ValidationResult(
                successful = true,
                errorMessage = null
            )
        }

        override fun validateConfirmPassword(password: String, confirmPassword: String): ValidationResult {
            if(confirmPassword.isBlank()){
                return ValidationResult(
                    successful = false,
                    errorMessage = "The password can not be blank"
                )
            }
            if(password != confirmPassword){
                return ValidationResult(
                    successful = false,
                    errorMessage = "The passwords do not match"
                )
            }
            return ValidationResult(
                successful = true,
                errorMessage = null
            )
        }

    override fun validateMealField(field: String): ValidationResult {
        if(field.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = "The meal field can not be blank"
            )
        }
        if(field.isEmpty()){
            return ValidationResult(
                successful = false,
                errorMessage = "The category field must be at least 1 characters long"
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}