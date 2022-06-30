package com.example.petshelter.domain.useCases

import com.example.petshelter.common.Resource
import com.example.petshelter.data.remote.dto.AnnouncementDto
import com.example.petshelter.domain.model.Announcement
import com.example.petshelter.domain.repository.backendAnimals.AnnouncementsRepository
import com.example.petshelter.domain.repository.firebase.FirebaseBackend
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetAnnouncementsUseCase @Inject constructor(
    private val repository: AnnouncementsRepository,
    private val firebaseBackend: FirebaseBackend
) {
    operator fun invoke(): Flow<Resource<List<Announcement>>> = flow {
        try {
            emit(Resource.Loading())
            val announcements = firebaseBackend.downloadImageCallback(repository.getAnnouncements())
            emit(Resource.Success(announcements))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}