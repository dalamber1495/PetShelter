package com.example.petshelter.tabScreens.announcementTab.model

import android.net.Uri
import com.example.petshelter.data.remote.dto.GeoPosition

data class AnnouncementState(
    val description: String,
    val geoPosition: GeoPosition,
    val address: String?,
    val id: String,
    val imageUrl: Uri?,
    val petType: String,
    val title: String
)
