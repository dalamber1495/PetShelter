package com.example.petshelter.data.remote.repository.backendAnimals

import com.example.petshelter.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.*

interface PetShelterApi {

    @POST("/login/email")
    suspend fun authUser(
        @Body body: LoginData
    ): RefreshTokenData

    @POST("/register/email")
    suspend fun registerUser(
        @Body body: RegisterData
    ): RefreshTokenData

    @GET("/announcements")
    suspend fun getAnnouncements(
        @Query("petType") pet: String = ""
    ): List<AnnouncementDto>

    @POST("/announcements")
    suspend fun postAnnouncements(
        @Header("Authorization") auth: String,
        @Body body: AnnouncementDtoPost
    ): AnnouncementDto
}