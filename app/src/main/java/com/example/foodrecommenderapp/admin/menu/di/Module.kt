package com.example.foodrecommenderapp.admin.menu.di

import com.example.foodrecommenderapp.admin.menu.data.DefaultMenuRepositoryImplementation
import com.example.foodrecommenderapp.admin.menu.domain.MenuRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideMenuRepository( firebaseFirestore: FirebaseFirestore, storage: FirebaseStorage):
            MenuRepository = DefaultMenuRepositoryImplementation(firebaseFirestore,storage)
}