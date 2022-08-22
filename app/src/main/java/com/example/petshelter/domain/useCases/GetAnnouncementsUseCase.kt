package com.example.petshelter.domain.useCases

import com.example.petshelter.common.Resource
import com.example.petshelter.domain.model.Announcement
import com.example.petshelter.domain.repository.backendAnimals.AnnouncementsRepository
import com.example.petshelter.data.remote.repository.firebase.FirebaseBackend
import com.example.petshelter.domain.repository.localRepository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAnnouncementsUseCase @Inject constructor(
    private val repository: AnnouncementsRepository,
    private val firebaseBackend: FirebaseBackend,
    private val userDataRepository: UserDataRepository
) {
    operator fun invoke(petType:String): Flow<Resource<List<Announcement>>> = flow {
        try {
            emit(Resource.Loading())
            val announcementsDto = repository.getAnnouncements(petType)
            val announcements = announcementsDto.map {
                Announcement(
                    it.description,
                    it.geoPosition,
                    it.id,
                    firebaseBackend.downloadImage(it.imageUrl),
                    it.petType,
                    it.title
                )
            }
            userDataRepository.saveAnnouncements(announcements)
            emit(Resource.Success(announcements))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}