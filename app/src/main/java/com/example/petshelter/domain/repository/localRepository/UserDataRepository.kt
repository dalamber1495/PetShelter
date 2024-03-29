package com.example.petshelter.domain.repository.localRepository

import android.net.Uri
import com.example.petshelter.data.remote.dto.AnnouncementDto
import com.example.petshelter.data.remote.dto.RefreshTokenData
import com.example.petshelter.domain.model.Announcement
import com.example.petshelter.tabScreens.announcementTab.model.AnnouncementState
import com.example.petshelter.tabScreens.createAnnouncementTab.model.SecondStepLocateData

interface UserDataRepository {

    fun getAnnouncements(): List<AnnouncementState>
    fun saveAnnouncements(data: List<AnnouncementState>)

    fun saveLoggedUserTokens(tokens: RefreshTokenData)
    fun getLoggedUserTokens(): RefreshTokenData

    fun isUserLoggedIn(): Boolean
    fun clearLocalData()


    fun saveFirstScreenPhotoUri(uri:Uri?)
    fun getFirstScreenPhotoUri():Uri?

    fun saveSecondScreenLocate(locateData: SecondStepLocateData?)
    fun getSecondScreenLocate():SecondStepLocateData?

}