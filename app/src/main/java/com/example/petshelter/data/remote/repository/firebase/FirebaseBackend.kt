package com.example.petshelter.data.remote.repository.firebase

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import coil.Coil
import coil.ImageLoader
import coil.request.ImageRequest
import com.example.petshelter.common.Resource
import com.example.petshelter.domain.repository.firebase.FirebaseRepository
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

const val FIREBASE_STORAGE = "gs://lost-pets-e8cae.appspot.com/"

class FirebaseBackend @Inject constructor(
) :FirebaseRepository{


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
        } catch (e: StorageException) {
            Log.e("TAG", "downloadImageCallback Storage Exception: ${e.message}")
            uri.toUri()
        }catch (e:Exception){
            Log.e("TAG", "downloadImageCallback: ${e.message}")
            "error".toUri()
        }
    }

    override suspend fun uploadImage(uri: Uri):Resource<Uri?>{
        return try {
            val id = System.currentTimeMillis()
            val uriRef = FirebaseStorage.getInstance(FIREBASE_STORAGE)
            val upLoadUri = uriRef.reference.child("/${id}.jpeg").putFile(uri).await().metadata?.path

            Resource.Success(upLoadUri?.toUri())
        } catch (e: Exception) {
            Log.e("TAG", "uploadImage: ${e.message}")
            Resource.Error(message = e.message )
        }
    }

    private fun String.parseNameUrl()=
        this.removePrefix("https://firebasestorage.googleapis.com/v0/b/lost-pets-e8cae.appspot.com/o/")

}