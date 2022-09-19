package com.example.petshelter.domain.repository.backendAnimals

import com.example.petshelter.common.Resource
import com.example.petshelter.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AnnouncementsRepository {
    suspend fun authUser(loginData: LoginData): RefreshTokenData
    suspend fun registerUser(registerData: RegisterData): RefreshTokenData
    suspend fun getAnnouncements(petType:String = ""): List<AnnouncementDto>
    suspend fun postAnnouncement(announcementDto: AnnouncementDtoPost, auth: String): List<AnnouncementDto>
}