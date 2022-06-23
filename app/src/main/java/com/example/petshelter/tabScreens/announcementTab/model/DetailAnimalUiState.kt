package com.example.petshelter.tabScreens.announcementTab.model

import android.net.Uri
import androidx.lifecycle.LiveData

data class DetailAnimalUiState(
    val title:LiveData<String>,
    val description:LiveData<String>,
    val photo:LiveData<List<Uri?>>,
    val address:LiveData<String>,
    val locatePhoto:LiveData<LocateData>,
)
