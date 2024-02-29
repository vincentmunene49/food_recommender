package com.example.foodrecommenderapp.auth.login.domain

import com.example.foodrecommenderapp.common.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun login(
        email: String,
        password: String,
        isAdmin: Boolean
    ): Flow<Resource<FirebaseUser>>
}