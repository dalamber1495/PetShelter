package com.eql.repositories.localUserData

import android.content.SharedPreferences
import android.util.Log
import com.example.petshelter.data.remote.dto.AnnouncementDto
import com.example.petshelter.data.remote.dto.RefreshTokenData
import com.example.petshelter.domain.model.Announcement
import com.example.petshelter.domain.repository.localRepository.UserDataRepository
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
        private const val tempAvatarUri = "file:///data/user/0/com.petshelter/files/images/avatar.png"
    }

    private val listAnnouncements = object :TypeToken<List<Announcement>>(){}.type
    private val gson = Gson()


    override fun getAnnouncements(): List<Announcement> {
        val dataJson = sharedPreferences.getString(animalFieldName, null)
        return when (dataJson == null) {
            true -> emptyList()
            false -> gson.fromJson(dataJson, listAnnouncements)
        }
    }

    override fun saveAnnouncements(data: List<Announcement>) {
        val userJson = gson.toJson(data,listAnnouncements)
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

    override fun isAnimalDataFirstScreenFilled(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isAnimalDataSecondScreenFilled(): Boolean {
        TODO("Not yet implemented")
    }


}