package com.example.petshelter.tabScreens.profileTab.view

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.eql.consts.ui.colors.petShelterBlack
import com.eql.consts.ui.colors.petShelterBlue
import com.example.petshelter.R
import com.example.petshelter.authScreens.main.consts.joinTextStyle
import com.example.petshelter.tabScreens.createAnnouncementTab.view.components.TopBarCreateAnnouncement
import com.example.petshelter.tabScreens.mainScreen.consts.ultraLightGray
import com.example.petshelter.tabScreens.profileTab.model.ProfileTabUiState
import com.example.petshelter.tabScreens.profileTab.navigation.routeObject.ProfileScreenRoute
import com.example.petshelter.tabScreens.profileTab.view.components.LogoutDialog
import com.example.petshelter.ui.styles.profileTextStyle
import com.example.petshelter.ui.theme.PetShelterTheme
import java.io.File
import java.util.*

@Composable
fun ProfileTabScreen(
    addPhotoCallback: (Uri?) -> Unit,
    uiState: ProfileTabUiState,
    navController: NavController,
    logoutBtnCallback: () -> Unit,
    navigateToCallback: (NavController, ProfileScreenRoute) -> Unit
) {

    val screenIsBusy = uiState.screenBusy.observeAsState(false)
    val avatarUri = uiState.avatarUri.observeAsState(null)
    val nameAndSurname = uiState.nameAndSurname.observeAsState("Имя и фамилия")
    val showDialogLogout = remember {
        mutableStateOf(false)
    }

    LogoutDialog(
        isDisplayed = showDialogLogout.value,
        toggleDialogDisplayCallback = { showDialogLogout.value = false },
        logoutBtnCallback = logoutBtnCallback
    )

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        TopBarCreateAnnouncement(
            backArrowShow = false,
            iconButtonRight = R.drawable.ic_edit_icon,
            iconButtonCallback = {
                navigateToCallback.invoke(
                    navController,
                    ProfileScreenRoute.EditProfile
                )
            }
        )
        Spacer(modifier = Modifier.height(48.dp))
        ProfilePhoto(
            isPhotoBusy = screenIsBusy.value,
            photoUri = avatarUri.value,
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            modifier = Modifier.padding(
                horizontal = 16.dp
            ),
            text = nameAndSurname.value,
            style = profileTextStyle
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextButton(modifier = Modifier.wrapContentSize(),
                onClick = {
                    navigateToCallback.invoke(
                        navController,
                        ProfileScreenRoute.ChangePassword
                    )
                }) {
                Row {
                    Text("Изменить пароль", style = joinTextStyle)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            TextButton(modifier = Modifier.wrapContentSize(),
                onClick = {
                    showDialogLogout.value = true
                }) {
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_logout),
                        contentDescription = "logout text button",
                        tint = petShelterBlack
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Выйти", style = joinTextStyle)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

    }
}

@Composable
fun ProfilePhoto(
    isPhotoBusy: Boolean,
    photoUri: Uri?
) {
    Surface(
        modifier = Modifier
            .size(184.dp)
    ) {

        Card(
            modifier = Modifier.wrapContentSize(),
            shape = CircleShape,
            backgroundColor = ultraLightGray,
            elevation = 0.dp
        ) {
            SubcomposeAsyncImage(
                model = photoUri,
                contentDescription = "profile_photo",
                modifier = Modifier
                    .fillMaxSize()
            ) {
                when (painter.state) {
                    is AsyncImagePainter.State.Loading -> CircularProgressIndicator()
                    is AsyncImagePainter.State.Error -> {
                        when (isPhotoBusy) {
                            true -> CircularProgressIndicator(color = petShelterBlue)
                            else -> {
                                NonPhotoField()
                            }
                        }
                    }
                    else -> {
                        when (isPhotoBusy) {
                            true -> {
                                SubcomposeAsyncImageContent()
                                CircularProgressIndicator(
                                    color = petShelterBlue,
                                    strokeWidth = 4.dp
                                )
                            }
                            else -> {
                                SubcomposeAsyncImageContent()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NonPhotoField() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_image_field),
            contentDescription = stringResource(id = R.string.photo_field),
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
        )
    }
}

@Preview
@Composable
fun ProfileTabScreenPreview() {
    PetShelterTheme {
        ProfileTabScreen(
            {},
            ProfileTabUiState(
                MutableLiveData(""),
                MutableLiveData(null),
                MutableLiveData(false),
                MutableLiveData("")
            ),
            NavController(LocalContext.current),
            {},
            { de, ec -> }
        )
    }
}