package com.example.petshelter.data.remote.dto

data class AnnouncementDtoPost(
    val description: String,
    val geoPosition: GeoPosition,
    val imageUrl: String,
    val petType: String,
    val title: String
)
