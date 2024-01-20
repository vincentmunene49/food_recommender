package com.example.foodrecommenderapp.auth.login.data

import com.example.foodrecommenderapp.auth.login.domain.LoginRepository
import com.example.foodrecommenderapp.common.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class DefaultLoginRepositoryImplementation @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : LoginRepository {

    override suspend fun login(email: String, password: String): Flow<Resource<FirebaseUser>>  = flow{

        emit(Resource.Loading())
        try{
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()

            if(result.user != null){
                emit(Resource.Success(result.user!!))
            } else {

                emit(Resource.Error("☹\uFE0F \nYou do not have an account. Please consider registering"))

            }
        } catch (e: Exception){
            if (e is CancellationException) {
                throw e
            }
            emit(Resource.Error(e.message))
        }
    }

    }