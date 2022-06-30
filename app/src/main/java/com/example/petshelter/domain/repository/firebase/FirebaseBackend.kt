package com.example.petshelter.domain.repository.firebase

import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.example.petshelter.data.remote.dto.AnnouncementDto
import com.example.petshelter.domain.model.Announcement
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseBackend {


    suspend fun downloadImageCallback(announcements: List<AnnouncementDto>):List<Announcement> {
       return announcements.map { Announcement(
            it.description,
            it.geoPosition,
            it.id,
            downloadImage(it.imageUrl),
            it.petType,
            it.title
        ) }
    }

    suspend fun downloadImage(url:String):Uri?{
        return try {
            //                val storage = FirebaseStorage.getInstance("https://firebasestorage.googleapis.com/v0/b/lost-pets-e8cae.appspot.com/o/IMG_4A981326FDC2-1.jpeg")
            //                storage.reference
            //                val maxDownloadSize = 5L * 1024L * 1024L
            //                val bytes = imageRef.child("v0/b/lost-pets-e8cae.appspot.com/o/IMG_4A981326FDC2-1.jpeg").getBytes(maxDownloadSize).await()
            //                val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            val uriRef = FirebaseStorage.getInstance("gs://lost-pets-e8cae.appspot.com/")
            val uri = uriRef.reference.child("IMG_4A981326FDC2-1.jpeg").downloadUrl.await()
            Log.d("TAG", "downloadImageCallback: Successfully uploaded image")
            uri
        } catch (e: Exception) {
            Log.e("TAG", "downloadImageCallback: ${e.message}")
            null
        }
    }
}