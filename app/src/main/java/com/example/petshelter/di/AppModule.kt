package com.example.petshelter.di

import android.content.Context
import android.location.Geocoder
import com.eql.repositories.backendUserData.authenticator.RemoteServerBackendAuthenticator
import com.example.petshelter.BuildConfig
import com.example.petshelter.common.Constants.BASE_URL
import com.example.petshelter.data.remote.repository.authenticator.AuthApiService
import com.example.petshelter.data.remote.repository.backendAnimals.PetShelterApi
import com.example.petshelter.data.remote.useCases.SetAuthorizationHeaderUseCase
import com.example.petshelter.domain.repository.localRepository.UserDataRepository
import com.example.petshelter.geo.LocationLiveData
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

const val connectionTimeOut = 30L

const val CLIENT_WITHOUT_AUTH = "client without auth"
const val CLIENT_WITH_AUTH = "client with auth"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Named(CLIENT_WITHOUT_AUTH)
    @Singleton
    fun provideClientOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .callTimeout(connectionTimeOut, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }
    @Provides
    @Singleton
    fun provideAuthentificator(
        userDataRepository: UserDataRepository,
        api: AuthApiService,
        setAuthorizationHeaderUseCase: SetAuthorizationHeaderUseCase
    ): RemoteServerBackendAuthenticator =
        RemoteServerBackendAuthenticator(userDataRepository, api, setAuthorizationHeaderUseCase)

    @Provides
    @Named(CLIENT_WITH_AUTH)
    @Singleton
    fun provideClientWithAuthOkHttp(remoteServerBackendAuthenticator: RemoteServerBackendAuthenticator): OkHttpClient {
        return OkHttpClient.Builder()
            .authenticator(remoteServerBackendAuthenticator)
            .callTimeout(connectionTimeOut, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun providePetShelterApi(@Named(CLIENT_WITH_AUTH) client: OkHttpClient): PetShelterApi =
        Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PetShelterApi::class.java)

    @Provides
    @Singleton
    fun provideAuthApi(@Named(CLIENT_WITHOUT_AUTH) client: OkHttpClient): AuthApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(AuthApiService::class.java)


    @Provides
    @Singleton
    fun provideLocationLiveData(@ApplicationContext context: Context) =
        LocationLiveData(context)


    @Provides
    @Singleton
    fun provideGeocoding(@ApplicationContext context: Context):Geocoder{
        return Geocoder(context, Locale.getDefault())
    }

}