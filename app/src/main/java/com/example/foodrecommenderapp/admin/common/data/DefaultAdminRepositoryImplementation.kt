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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class DefaultAdminRepositoryImplementation @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val storage: FirebaseStorage

) : AdminRepository {
    override suspend fun addMenu(menu: Menu, foodImage: ByteArray, category: Category, categoryImage: ByteArray): Flow<Resource<Menu>> = flow {
        emit(Resource.Loading<Menu>())
        coroutineScope {
            try {
                val imageFood = async {
                    val imageRef = storage.reference.child("images/foodImages/${UUID.randomUUID()}")
                    imageRef.putBytes(foodImage).await()
                    imageRef.downloadUrl.await().toString()
                }
                val imageCategory = async {
                    val imageRef = storage.reference.child("images/categoryImages/${UUID.randomUUID()}")
                    imageRef.putBytes(categoryImage).await()
                    imageRef.downloadUrl.await().toString()
                }

                val imageFoodUrl = imageFood.await()
                val imageCategoryUrl = imageCategory.await()

                val menuWithImages = menu.copy(image = imageFoodUrl, categoryImage = imageCategoryUrl)
                val categoryWithImage = category.copy(image = imageCategoryUrl)

                val menuUpload = async {
                    firebaseFirestore.collection(MENU_COLLECTION).document(UUID.randomUUID().toString())
                        .set(menuWithImages).await()
                }
                val categoryUpload = async {
                    firebaseFirestore.collection(CATEGORY_COLLECTION).document(UUID.randomUUID().toString())
                        .set(categoryWithImage).await()
                }

                menuUpload.await()
                categoryUpload.await()

                emit(Resource.Success(menuWithImages))
            } catch (e: Exception) {
                if (e is CancellationException) {
                    throw e
                }
                emit(Resource.Error<Menu>("☹\uFE0F \n ${e.message}"))
            }
        }
    }

    override suspend fun getReports(date:String): Flow<Resource<Reports?>> = flow {
        emit(Resource.Loading())

        try {
            val result = firebaseFirestore.collection(REPORT_COLLECTION).document(date).get().await()
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
}