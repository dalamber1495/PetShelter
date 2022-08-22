package com.example.petshelter.data.remote.repository.backendAnimals

import com.example.petshelter.data.remote.dto.*
import com.example.petshelter.domain.repository.backendAnimals.AnnouncementsRepository
import javax.inject.Inject

class AnnouncementRepositoryImpl @Inject constructor(
    val api: PetShelterApi
) : AnnouncementsRepository {

    override suspend fun authUser(loginData: LoginData): RefreshTokenData =
        api.authUser(loginData)
    override suspend fun registerUser(registerData: RegisterData): RefreshTokenData =
        api.registerUser(registerData)
    override suspend fun getAnnouncements(petType:String): List<AnnouncementDto> =
        api.getAnnouncements(petType)
    override suspend fun postAnnouncement(
        announcementDto: AnnouncementDtoPost,
        auth: String
    ): AnnouncementDto =
        api.postAnnouncements(auth, announcementDto)

}