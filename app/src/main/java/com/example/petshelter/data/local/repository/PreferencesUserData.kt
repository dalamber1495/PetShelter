package com.eql.repositories.localUserData

import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.example.petshelter.data.remote.dto.RefreshTokenData
import com.example.petshelter.domain.model.Announcement
import com.example.petshelter.domain.repository.localRepository.UserDataRepository
import com.example.petshelter.tabScreens.createAnnouncementTab.model.SecondStepLocateData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import javax.inject.Inject

class PreferencesUserData @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : UserDataRepository {
    companion object {
        private const val TAG = "PreferencesUserData"

        const val loggedTokenFieldName = "logged_user_token"
        const val animalFieldName = "animal_data"
        const val noLoggedUserFoundMessage = "No logged user token found"
        const val locateDataName = "locate_data_name"
        const val photoUriName = "photo_uri_name"
        private const val tempAvatarUri =
            "file:///data/user/0/com.petshelter/files/images/avatar.png"
    }

    private val listAnnouncements = object : TypeToken<List<Announcement>>() {}.type
    private val gson = Gson()


    override fun getAnnouncements(): List<Announcement> {
        val dataJson = sharedPreferences.getString(animalFieldName, null)
        return when (dataJson == null) {
            true -> emptyList()
            false -> gson.fromJson(dataJson, listAnnouncements)
        }
    }

    override fun saveAnnouncements(data: List<Announcement>) {
        val userJson = gson.toJson(data, listAnnouncements)
        sharedPreferences.edit()
            .putString(animalFieldName, userJson)
            .apply()
    }

    override fun isUserLoggedIn(): Boolean =
        sharedPreferences.getString(loggedTokenFieldName, null) != null

    override fun saveLoggedUserTokens(tokens: RefreshTokenData) {
        val userJson = gson.toJson(tokens)
        sharedPreferences.edit()
            .putString(loggedTokenFieldName, userJson)
            .apply()
    }

    override fun getLoggedUserTokens(): RefreshTokenData {
        val tokensJson = sharedPreferences.getString(loggedTokenFieldName, null)
        return when (tokensJson == null) {
            true -> throw Exception(noLoggedUserFoundMessage)
            false -> gson.fromJson(tokensJson, RefreshTokenData::class.java)
        }
    }

    override fun clearLocalData() {
        Log.i(TAG, "Clearing local data!")
        sharedPreferences.edit()
            .clear()
            .apply()
    }

    override fun saveFirstScreenPhotoUri(uri: Uri) {
        sharedPreferences.edit()
            .putString(photoUriName, uri.toString())
            .apply()
    }

    override fun getFirstScreenPhotoUri(): Uri? {
        return sharedPreferences.getString(photoUriName, null)?.toUri()
    }

    override fun saveSecondScreenLocate(locateData: SecondStepLocateData?) {
        val locateJson = gson.toJson(locateData)
        sharedPreferences.edit().putString(locateDataName, locateJson).apply()
    }

    override fun getSecondScreenLocate(): SecondStepLocateData? {
        val locateJson = sharedPreferences.getString(locateDataName, null)
        return if (locateJson == null)
            null
        else gson.fromJson(
            locateJson,
            SecondStepLocateData::class.java
        )
    }
}