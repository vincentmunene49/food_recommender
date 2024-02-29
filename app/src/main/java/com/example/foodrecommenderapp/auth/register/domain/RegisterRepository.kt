package com.example.foodrecommenderapp.auth.register.domain

import com.example.foodrecommenderapp.auth.model.User
import com.example.foodrecommenderapp.common.Resource
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun register(
        email: String,
        password: String,
        name:String,
        isAdmin:Boolean
    ): Flow<Resource<User>>

}