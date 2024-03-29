package com.example.petshelter.di

import com.example.petshelter.data.remote.useCases.SetAuthorizationHeaderUseCase
import com.example.petshelter.domain.repository.backendAnimals.AnnouncementsRepository
import com.example.petshelter.domain.repository.firebase.FirebaseRepository
import com.example.petshelter.domain.repository.localRepository.UserDataRepository
import com.example.petshelter.domain.useCases.PostAnnouncementUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Provides
    @Singleton
    fun provideSetAuthHeaderUseCase(userDataRepository: UserDataRepository) =
        SetAuthorizationHeaderUseCase(userDataRepository)

}