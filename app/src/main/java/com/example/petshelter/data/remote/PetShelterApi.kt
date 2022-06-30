package com.example.petshelter.data.remote

import com.example.petshelter.data.remote.dto.AnnouncementDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface PetShelterApi {

    @GET("/nbaklanov-issart/PetProjectAPI/1.0.0/announcements")
    suspend fun getAnnouncements(): List<AnnouncementDto>

    @POST("/nbaklanov-issart/PetProjectAPI/1.0.0/announcements")
    suspend fun postAnnouncements(
//        @Header("Authorization") auth: String,
        @Body body: AnnouncementDto
    ): Response<AnnouncementDto>
}