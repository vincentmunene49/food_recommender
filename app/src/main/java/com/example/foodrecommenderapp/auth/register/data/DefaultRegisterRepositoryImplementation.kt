package com.example.foodrecommenderapp.auth.register.data

import com.example.foodrecommenderapp.auth.model.User
import com.example.foodrecommenderapp.auth.register.domain.RegisterRepository
import com.example.foodrecommenderapp.common.Resource
import com.example.foodrecommenderapp.common.constants.USER_COLLECTION
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class DefaultRegisterRepositoryImplementation @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : RegisterRepository {
    override suspend fun register(
        email: String,
        password: String,
        name: String,
        uid: String
    ): Flow<Resource<User>> = flow {

        emit(Resource.Loading())
        try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = User(userName = name, email = email)
            if (result.user != null) {
                firebaseFirestore.collection(USER_COLLECTION)
                    .document(uid)
                    .set(user)
                    .await()
                emit(Resource.Success(user))
            } else {
                emit(Resource.Error("☹\uFE0F \n Error creating account"))
            }

        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            emit(Resource.Error("☹\uFE0F \n ${e.message}"))
        }

    }

}