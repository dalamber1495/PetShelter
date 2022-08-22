package com.example.petshelter.domain.model

import android.net.Uri
import com.example.petshelter.data.remote.dto.GeoPosition

data class Announcement(
    val description: String,
    val geoPosition: GeoPosition,
    val id: String,
    val imageUrl: Uri?,
    val petType: String,
    val title: String

)
