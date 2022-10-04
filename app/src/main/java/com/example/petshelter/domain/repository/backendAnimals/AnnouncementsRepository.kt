package com.example.petshelter.domain.repository.backendAnimals

import com.example.petshelter.data.remote.dto.*


interface AnnouncementsRepository {
    suspend fun authUser(loginData: LoginData): RefreshTokenData
    suspend fun registerUser(registerData: RegisterData): RefreshTokenData
    suspend fun getAnnouncements(petType:String = ""): List<AnnouncementDto>
    suspend fun getAnnouncement(id: String): AnnouncementDto
    suspend fun postAnnouncement(announcementDto: AnnouncementDtoPost, auth: String): List<AnnouncementDto>

}