package com.example.petshelter.data.remote.dto

data class AnnouncementDto(
    val description: String,
    val geoPosition: GeoPosition,
    val id: String,
    val imageUrl: String,
    val petType: String,
    val title: String
)