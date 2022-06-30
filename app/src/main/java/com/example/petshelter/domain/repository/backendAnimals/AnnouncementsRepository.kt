package com.example.petshelter.domain.repository.backendAnimals

import com.example.petshelter.common.Resource
import com.example.petshelter.data.remote.dto.AnnouncementDto
import retrofit2.Response

interface AnnouncementsRepository {
    suspend fun getAnnouncements():List<AnnouncementDto>
    suspend fun postAnnouncement(announcementDto: AnnouncementDto):Response<AnnouncementDto>
}