package com.example.foodrecommenderapp.admin.common.di

import com.example.foodrecommenderapp.admin.common.data.DefaultAdminRepositoryImplementation
import com.example.foodrecommenderapp.admin.common.domain.AdminRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideMenuRepository( firebaseFirestore: FirebaseFirestore, storage: FirebaseStorage):
            AdminRepository = DefaultAdminRepositoryImplementation(firebaseFirestore,storage)
}