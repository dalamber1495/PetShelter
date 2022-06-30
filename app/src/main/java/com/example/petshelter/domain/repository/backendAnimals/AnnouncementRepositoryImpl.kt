package com.example.petshelter.domain.repository.backendAnimals

import com.example.petshelter.data.remote.PetShelterApi
import com.example.petshelter.data.remote.dto.AnnouncementDto
import retrofit2.Response
import javax.inject.Inject

class AnnouncementRepositoryImpl @Inject constructor(
   val api: PetShelterApi
): AnnouncementsRepository {
    override suspend fun getAnnouncements(): List<AnnouncementDto> {
        return api.getAnnouncements()
    }

    override suspend fun postAnnouncement(announcementDto: AnnouncementDto): Response<AnnouncementDto> {
        TODO("Not yet implemented")
    }
}