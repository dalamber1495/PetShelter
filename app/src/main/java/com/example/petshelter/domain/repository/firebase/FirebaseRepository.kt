package com.example.petshelter.domain.repository.firebase

import android.net.Uri
import com.example.petshelter.common.Resource

interface FirebaseRepository {
    suspend fun downloadImage(uri:String):Uri?
    suspend fun uploadImage(uri:Uri):Resource<Uri?>
}