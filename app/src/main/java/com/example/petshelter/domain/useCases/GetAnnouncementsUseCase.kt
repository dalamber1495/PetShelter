package com.example.petshelter.domain.useCases

import android.location.Geocoder
import com.example.petshelter.common.Resource
import com.example.petshelter.data.remote.dto.GeoPosition
import com.example.petshelter.domain.repository.backendAnimals.AnnouncementsRepository
import com.example.petshelter.data.remote.repository.firebase.FirebaseBackend
import com.example.petshelter.domain.repository.localRepository.UserDataRepository
import com.example.petshelter.tabScreens.announcementTab.model.AnnouncementState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.lang.IndexOutOfBoundsException
import javax.inject.Inject

class GetAnnouncementsUseCase @Inject constructor(
    private val repository: AnnouncementsRepository,
    private val firebaseBackend: FirebaseBackend,
    private val userDataRepository: UserDataRepository,
    private val geocoder: Geocoder
) {
    operator fun invoke(petType:String): Flow<Resource<List<AnnouncementState>>> = flow {
        try {
            emit(Resource.Loading())
            val announcementsDto = repository.getAnnouncements(petType)
            val announcements = announcementsDto.map {
                AnnouncementState(
                    description = it.description,
                    geoPosition = it.geoPosition,
                    address = getAddress(it.geoPosition),
                    id = it.id,
                    imageUrl = firebaseBackend.downloadImage(it.imageUrl),
                    petType = it.petType,
                    title = it.title
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

    private fun getAddress(geoPosition: GeoPosition): String? {
        val address = geocoder.getFromLocation(geoPosition.lat,geoPosition.lng,1)
        return try {
            address?.get(0)?.getAddressLine(0)
        }catch (e:IndexOutOfBoundsException){
            null
        }
    }
}