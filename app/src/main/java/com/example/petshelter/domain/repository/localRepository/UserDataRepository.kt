package com.example.petshelter.domain.repository.localRepository

import com.example.petshelter.data.remote.dto.AnnouncementDto
import com.example.petshelter.data.remote.dto.RefreshTokenData
import com.example.petshelter.domain.model.Announcement

interface UserDataRepository {

    fun getAnnouncements(): List<Announcement>
    fun saveAnnouncements(data: List<Announcement>)

    fun saveLoggedUserTokens(tokens: RefreshTokenData)
    fun getLoggedUserTokens(): RefreshTokenData

    fun isUserLoggedIn(): Boolean
    fun clearLocalData()

    fun isAnimalDataFirstScreenFilled():Boolean
    fun isAnimalDataSecondScreenFilled():Boolean
}