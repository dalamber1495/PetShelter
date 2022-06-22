package com.example.petshelter.tabScreens.profileTab.view

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.petshelter.authScreens.main.components.FormField
import com.example.petshelter.tabScreens.createAnnouncementTab.view.components.TopBarCreateAnnouncement
import com.example.petshelter.tabScreens.profileTab.model.ProfileTabUiState

@Composable
fun ChangePasswordScreen(
    addPhotoCallback: (Uri?) -> Unit,
    uiState: ProfileTabUiState,
    popBackStackCallback:(NavController)->Unit,
    navController: NavController,
){

    val screenIsBusy = uiState.screenBusy.observeAsState(false)
    val avatarUri = uiState.avatarUri.observeAsState(null)
    val nameAndSurname = uiState.nameAndSurname.observeAsState("Имя и фамилия")

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        TopBarCreateAnnouncement(
            backArrowShow = true,
            backArrowCallback = { popBackStackCallback.invoke(navController) },
            textTopBar = "Изменение пароля"
        )
        Spacer(modifier = Modifier.height(40.dp))
        FormField(modifier = Modifier.height(56.dp), placeHolder = "Старый пароль", visibleIcon = true) {}
        Spacer(modifier = Modifier.height(32.dp))
        FormField(modifier = Modifier.height(56.dp), placeHolder = "Новый пароль",visibleIcon = true) {}
        Spacer(modifier = Modifier.height(32.dp))
        FormField(modifier = Modifier.height(56.dp), placeHolder = "Повторите пароль",visibleIcon = true) {}

        }
}