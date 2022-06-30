package com.example.petshelter.domain.model


data class AnnouncementsListState(
    val isLoading:Boolean = false,
    val announcements:List<Announcement> = emptyList(),
    val error:String = ""

)
