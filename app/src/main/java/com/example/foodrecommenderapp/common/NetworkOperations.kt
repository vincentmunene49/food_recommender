package com.example.foodrecommenderapp.common

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.cancellation.CancellationException

inline fun <T> safeApiCall(
    crossinline operation: suspend () -> T
): Flow<Resource<T>> = flow {
    try {
        coroutineScope {
            emit(Resource.Loading())
            val result = operation.invoke()
            emit(Resource.Success(result))
        }
    } catch (e: Throwable) {
        if (e is CancellationException) {
            throw e
        }
        emit(
            Resource.Error(
                e.message
            )
        )
    }
}
