package com.example.foodrecommenderapp.admin.common.data

import android.net.Uri
import androidx.core.net.toUri
import com.example.foodrecommenderapp.admin.common.domain.AdminRepository
import com.example.foodrecommenderapp.admin.menu.model.Category
import com.example.foodrecommenderapp.admin.menu.model.Menu
import com.example.foodrecommenderapp.common.Resource
import com.example.foodrecommenderapp.common.constants.MENU_COLLECTION
import com.example.foodrecommenderapp.common.constants.REPORT_COLLECTION
import com.example.foodrecommenderapp.admin.report.model.Reports
import com.example.foodrecommenderapp.common.constants.CATEGORY_COLLECTION
import com.example.foodrecommenderapp.common.constants.ORDER_COLLECTION
import com.example.foodrecommenderapp.order.data.model.Order
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class DefaultAdminRepositoryImplementation @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val firebaseAuth: FirebaseAuth

) : AdminRepository {
    override suspend fun addMenu(
        menu: Menu, foodImage: ByteArray,
        category: Category,
        categoryImage: ByteArray
    ): Flow<Resource<Menu>> = flow {
        emit(Resource.Loading<Menu>())
        coroutineScope {
            try {
                val imageFood = async {
                    val imageRef = storage.reference.child("images/foodImages/${UUID.randomUUID()}")
                    imageRef.putBytes(foodImage).await()
                    imageRef.downloadUrl.await().toString()
                }
                val imageCategory = async {
                    val imageRef =
                        storage.reference.child("images/categoryImages/${UUID.randomUUID()}")
                    imageRef.putBytes(categoryImage).await()
                    imageRef.downloadUrl.await().toString()
                }

                val imageFoodUrl = imageFood.await()
                val imageCategoryUrl = imageCategory.await()

                val menuWithImages =
                    menu.copy(image = imageFoodUrl, categoryImage = imageCategoryUrl, adminId = firebaseAuth.currentUser?.uid ?: "")
                val categoryWithImage = category.copy(image = imageCategoryUrl)

                val menuUpload = async {
                    val result =
                        firebaseFirestore.collection(MENU_COLLECTION).add(menuWithImages).await()
                    val menuId = result.id
                    val menuWithId = menuWithImages.copy(id = menuId)
                    result.set(menuWithId).await()
                    menuWithId
                }
                val categoryUpload = async {
                    val result =
                        firebaseFirestore.collection(CATEGORY_COLLECTION).add(categoryWithImage)
                            .await()
                    val categoryId = result.id
                    val categoryWithId = categoryWithImage.copy(id = categoryId)
                    result.set(categoryWithId).await()
                    categoryWithId
                }

                val menuWithId = menuUpload.await()
                categoryUpload.await()

                emit(Resource.Success(menuWithId))
            } catch (e: Exception) {
                if (e is CancellationException) {
                    throw e
                }
                emit(Resource.Error<Menu>("☹\uFE0F \n ${e.message}"))
            }
        }
    }

    override suspend fun getReports(date: String): Flow<Resource<Reports?>> = flow {
        emit(Resource.Loading())

        try {
            val result =
                firebaseFirestore.collection(REPORT_COLLECTION).document(date).get().await()
            val reports = result.toObject(Reports::class.java)
            if (reports != null) {
                emit(Resource.Success(reports))
            } else {
                emit(Resource.Success(Reports())) // Emit an empty Reports object when data is null
            }
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            emit(Resource.Error("☹\uFE0F \n ${e.message}"))
        }
    }

    override suspend fun getOrderReport(): Flow<Resource<List<Order>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val adminId = firebaseAuth.currentUser?.uid ?: ""
                val result = firebaseFirestore.collection(ORDER_COLLECTION)
                    .whereEqualTo("menu.adminId", adminId)
                    .get()
                    .await()
                val orders = result.toObjects(Order::class.java)
                if (orders.isNotEmpty()) {
                    emit(Resource.Success(orders))
                } else {
                    emit(Resource.Success(emptyList()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An error occurred"))
            }
        }
    }

    override suspend fun deleteOrder(order: Order): Flow<Resource<Order>> {
        return flow {
            emit(Resource.Loading())
            try {
                firebaseFirestore.collection(ORDER_COLLECTION).document(order.id).delete().await()
                emit(Resource.Success(order))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An error occurred"))
            }
        }
    }

    override suspend fun logOut(): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading())
            try {
                firebaseAuth.signOut()
                emit(Resource.Success(true))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An error occurred"))
            }
        }
    }
}