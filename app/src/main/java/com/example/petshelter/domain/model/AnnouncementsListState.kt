package com.example.petshelter.domain.model

import com.example.petshelter.tabScreens.announcementTab.model.AnnouncementState


data class AnnouncementsListState(
    val isLoading:Boolean = false,
    val announcements:List<AnnouncementState> = emptyList(),
    val error:String = ""

)
