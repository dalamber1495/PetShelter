package com.example.petshelter.data.remote.repository.firebase

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.example.petshelter.common.Resource
import com.example.petshelter.domain.repository.firebase.FirebaseRepository
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

const val FIREBASE_STORAGE = "gs://lost-pets-e8cae.appspot.com/"

class FirebaseBackend :FirebaseRepository{


        override suspend fun downloadImage(uri:String):Uri?{
        return try {
            //                val storage = FirebaseStorage.getInstance("https://firebasestorage.googleapis.com/v0/b/lost-pets-e8cae.appspot.com/o/IMG_4A981326FDC2-1.jpeg")
            //                storage.reference
            //                val maxDownloadSize = 5L * 1024L * 1024L
            //                val bytes = imageRef.child("v0/b/lost-pets-e8cae.appspot.com/o/IMG_4A981326FDC2-1.jpeg").getBytes(maxDownloadSize).await()
            //                val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            val uriRef = FirebaseStorage.getInstance(FIREBASE_STORAGE)
            val uriImg = uriRef.reference.child(uri.parseNameUrl()).downloadUrl.await()
            Log.d("TAG", "downloadImageCallback: Successfully uploaded image")
            uriImg
        } catch (e: Exception) {
            Log.e("TAG", "downloadImageCallback: ${e.message}")
            "error".toUri()
        }
    }

    override suspend fun uploadImage(uri: Uri, id:String):Resource<Uri?>{
        return try {
            val uriRef = FirebaseStorage.getInstance(FIREBASE_STORAGE)
            uriRef.reference.child("/${id}/1.jpeg").putFile(uri).await()
            Resource.Success(uri)
        } catch (e: Exception) {
            Log.e("TAG", "uploadImage: ${e.message}")
            Resource.Error(message = e.message )
        }
    }

    private fun String.parseNameUrl()=
        this.removePrefix("https://firebasestorage.googleapis.com/v0/b/lost-pets-e8cae.appspot.com/o/")

}