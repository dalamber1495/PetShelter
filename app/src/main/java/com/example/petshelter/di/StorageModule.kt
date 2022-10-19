package com.example.petshelter.di

import android.content.Context
import android.content.SharedPreferences
import com.example.petshelter.data.local.repository.PreferencesUserData
import com.example.petshelter.data.remote.repository.backendAnimals.AnnouncementRepositoryImpl
import com.example.petshelter.data.remote.repository.backendAnimals.PetShelterApi
import com.example.petshelter.data.remote.repository.firebase.FirebaseBackend
import com.example.petshelter.domain.repository.backendAnimals.AnnouncementsRepository
import com.example.petshelter.domain.repository.localRepository.UserDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

const val PREFS_NAME = "petshelter_prefs"

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {

    @Provides
    @Singleton
    fun getSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideUserDataRepository(sharedPref:SharedPreferences): UserDataRepository =
        PreferencesUserData(sharedPref)

    @Provides
    @Singleton
    fun provideAnnouncementRepository(api: PetShelterApi): AnnouncementsRepository {
        return AnnouncementRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideFirebaseBackend(@ApplicationContext context: Context): FirebaseBackend =
        FirebaseBackend()


}