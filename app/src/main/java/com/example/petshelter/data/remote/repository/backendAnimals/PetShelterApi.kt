package com.example.petshelter.data.remote.repository.backendAnimals

import com.example.petshelter.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.*

interface PetShelterApi {

    @POST("/api/1.0.0/login/email")
    suspend fun authUser(
        @Body body: LoginData
    ): RefreshTokenData

    @POST("/api/1.0.0/register/email")
    suspend fun registerUser(
        @Body body: RegisterData
    ): RefreshTokenData

    @GET("/api/1.0.0/announcements")
    suspend fun getAnnouncements(
        @Query("petType") pet: String = ""
    ): List<AnnouncementDto>

    @POST("/api/1.0.0/announcements")
    suspend fun postAnnouncements(
        @Header("Authorization") auth: String,
        @Body body: AnnouncementDtoPost
    ): List<AnnouncementDto>
}