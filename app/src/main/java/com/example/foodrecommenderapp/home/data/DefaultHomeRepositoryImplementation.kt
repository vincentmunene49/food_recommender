package com.example.foodrecommenderapp.home.data

import com.example.foodrecommenderapp.admin.menu.model.Category
import com.example.foodrecommenderapp.admin.menu.model.Menu
import com.example.foodrecommenderapp.common.Resource
import com.example.foodrecommenderapp.common.constants.CATEGORY_COLLECTION
import com.example.foodrecommenderapp.common.constants.MENU_COLLECTION
import com.example.foodrecommenderapp.common.constants.ORDER_COLLECTION
import com.example.foodrecommenderapp.home.domain.HomeRepository
import com.example.foodrecommenderapp.order.data.model.Order
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DefaultHomeRepositoryImplementation @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : HomeRepository {


    override suspend fun searchMeal(searchTerm: String): Flow<Resource<List<Menu>>> {

        return flow {
            emit(Resource.Loading())
            try {
                val startAt = if (searchTerm.isNotEmpty()) searchTerm[0].toString() else ""
                val result = firebaseFirestore.collection(MENU_COLLECTION)
                    .orderBy("name")
                    .startAt(startAt)
                    .get()
                    .await()
                val meals = result.toObjects(Menu::class.java)
                if (meals.isNotEmpty()) {
                    emit(Resource.Success(meals))

                } else {
                    emit(Resource.Success(emptyList()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An error occurred"))
            }
        }
    }

    override suspend fun getMealCategories(): Flow<Resource<List<Category>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val result = firebaseFirestore.collection(CATEGORY_COLLECTION).get().await()
                val categories = result.toObjects(Category::class.java)
                if (categories.isNotEmpty()) {
                    emit(Resource.Success(categories))
                } else {
                    emit(Resource.Success(emptyList()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An error occurred"))
            }
        }
    }

    override suspend fun getMeals(): Flow<Resource<List<Menu>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val result = firebaseFirestore.collection(MENU_COLLECTION).get().await()
                val meals = result.toObjects(Menu::class.java)
                if (meals.isNotEmpty()) {
                    emit(Resource.Success(meals))
                } else {
                    emit(Resource.Success(emptyList()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An error occurred"))
            }
        }
    }

    override suspend fun getMealsByCategory(category: String): Flow<Resource<List<Menu>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val result = firebaseFirestore.collection(MENU_COLLECTION).whereEqualTo("category", category)
                    .get().await()
                val meals = result.toObjects(Menu::class.java)
                if (meals.isNotEmpty()) {
                    emit(Resource.Success(meals))
                } else {
                    emit(Resource.Success(emptyList()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An error occurred"))
            }
        }
    }

    override suspend fun addOrder(order: Order): Flow<Resource<Order>> {
        return flow {
            emit(Resource.Loading())
            try {
                val user = firebaseAuth.currentUser
                val userId = user?.uid ?: ""
                val userEmail = user?.email ?: ""
                val updatedOrder = order.copy(userId = userId, userEmail = userEmail)

                val orderCollection = firebaseFirestore.collection(ORDER_COLLECTION)
                val existingOrderQuery = orderCollection
                    .whereEqualTo("menu.id", order.menu.id)
                    .get()
                    .await()

                if (existingOrderQuery.documents.isNotEmpty()) {
                    // The order already exists, so we update the quantity
                    val existingOrder = existingOrderQuery.documents[0].toObject(Order::class.java)
                    if (existingOrder != null) {
                        val newQuantity = existingOrder.quantity + order.quantity
                        val newPrice = existingOrder.menu.price?.times(newQuantity)
                        val newOrder = existingOrder.copy(quantity = newQuantity, menu = existingOrder.menu.copy(price = newPrice))
                        orderCollection.document(existingOrder.id).set(newOrder).await()
                        emit(Resource.Success(newOrder))
                    } else {
                        emit(Resource.Error("An error occurred"))
                    }
                } else {
                    // The order does not exist, so we add it
                    val result = orderCollection.add(updatedOrder).await()
                    val orderId = result.id
                    val orderWithId = updatedOrder.copy(id = orderId)
                    result.set(orderWithId).await()

                    val menu = result.get().await().toObject(Order::class.java)
                    if (menu != null) {
                        emit(Resource.Success(menu))
                    } else {
                        emit(Resource.Error("An error occurred"))
                    }
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An error occurred"))
            }
        }
    }
    override suspend fun getOrders(): Flow<Resource<List<Order>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val result = firebaseFirestore.collection(ORDER_COLLECTION).get().await()
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

    override suspend fun logout(): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading())
            try {
                firebaseAuth.signOut()
                emit(Resource.Success(Unit))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An error occurred"))
            }
        }
    }
}