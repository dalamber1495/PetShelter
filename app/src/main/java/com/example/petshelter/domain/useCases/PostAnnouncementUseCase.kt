package com.example.petshelter.domain.useCases

import androidx.core.net.toUri
import com.example.petshelter.common.Resource
import com.example.petshelter.data.remote.dto.AnnouncementDto
import com.example.petshelter.data.remote.dto.AnnouncementDtoPost
import com.example.petshelter.data.remote.repository.firebase.FirebaseBackend
import com.example.petshelter.domain.model.Announcement
import com.example.petshelter.domain.repository.backendAnimals.AnnouncementsRepository
import com.example.petshelter.domain.repository.localRepository.UserDataRepository
import retrofit2.HttpException
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostAnnouncementUseCase @Inject constructor(
    private val repository: AnnouncementsRepository,
    private val firebaseBackend: FirebaseBackend,
    private val userDataRepository: UserDataRepository
) {
    operator fun invoke(announcement: Announcement): Flow<Resource<Announcement>> = flow {
        try {
            emit(Resource.Loading())
            firebaseBackend.uploadImage(announcement.imageUrl!!, announcement.id)
            val announcementsDto = repository.postAnnouncement(announcement.toDtoPost(),userDataRepository.getLoggedUserTokens().accessToken)
            emit(Resource.Success(announcementsDto.toAnnouncement()))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }

    private fun Announcement.toDtoPost(): AnnouncementDtoPost =
        AnnouncementDtoPost(
            this.description,
            this.geoPosition,
            this.imageUrl.toString(),
            this.petType,
            this.title
        )

    private fun AnnouncementDto.toAnnouncement() =
        Announcement(
            this.description,
            this.geoPosition,
            this.id,
            this.imageUrl.toUri(),
            this.petType,
            this.title

        )

}