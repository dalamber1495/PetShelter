package com.example.petshelter.tabScreens.createAnnouncementTab.view.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.eql.consts.ui.colors.petShelterBlue
import com.example.petshelter.R
import com.example.petshelter.authScreens.main.components.PetShelterBtn
import com.example.petshelter.ui.theme.PetShelterTheme
import com.example.petshelter.utils.ComposeFileProvider
import java.io.File
import java.util.*

@Composable
fun FirstStepCreateAnnouncementForm(
    screenIsBusy: Boolean,
    avatarUri: Uri?,
    addPhotoCallback: (Uri?) -> Unit,
    firstStepReadyCallback: () -> Unit,
) {

    val context = LocalContext.current
    val contentResolver = context.contentResolver
    val cropImage =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { result ->
            if (result != null) {
                val uri = ComposeFileProvider.getImageUri(context)
                val openOutputStream = contentResolver.openOutputStream(uri)
                val parcelFileDescriptor = contentResolver.openFileDescriptor(result,"r")
                val fileDescriptor = parcelFileDescriptor?.fileDescriptor
                val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
                parcelFileDescriptor?.close()
                image.compress(Bitmap.CompressFormat.JPEG,100,openOutputStream)
                addPhotoCallback.invoke(
                    uri
                )
            } else {
                Log.e("AVATAR_TAG", "error: ")
            }
        }
    val imageUri = remember {
        mutableStateOf<Uri?>(null)
    }
    val setImage = { uri: Uri -> imageUri.value = uri }
    val takePhoto =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()) {
            if (it)
                addPhotoCallback.invoke(imageUri.value)
            else
                Log.e("AVATAR_TAG", "error: ")
        }
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        when (avatarUri != null) {
            true -> {
                TopBarCreateAnnouncement(
                    backArrowShow = true,
                    textTopBar = stringResource(R.string.upload_photo),
                    backArrowCallback = { addPhotoCallback.invoke(null) }
                )
                AnimalPhoto(
                    isPhotoBusy = screenIsBusy,
                    photoUri = avatarUri,
                    firstStepReadyCallback = firstStepReadyCallback,
                )
            }
            false -> {
                TopBarCreateAnnouncement(
                    backArrowShow = false,
                    textTopBar = stringResource(R.string.upload_photo),
                )
                AddPhotoBtn(
                    context = context,
                    cropImage = cropImage,
                    setImage = setImage,
                    takePhoto = takePhoto
                )
            }
        }


    }
}

@Composable
fun AddPhotoBtn(
    context: Context,
    cropImage: ManagedActivityResultLauncher<String, Uri?>,
    takePhoto: ManagedActivityResultLauncher<Uri, Boolean>,
    setImage: (Uri) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PetShelterBtn(
            modifier = Modifier
                .width(250.dp)
                .height(56.dp),
            text = "Сделать фото",
            image = R.drawable.ic_make_photo,
            clickCallback = {
                takePhotoFromCamera(
                    context = context,
                    takePhoto = takePhoto,
                    setImage = setImage
                )
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
        PetShelterBtn(
            modifier = Modifier
                .width(250.dp)
                .height(56.dp),
            text = "Загрузить из галереи",
            image = R.drawable.ic_load_photo,
            clickCallback = {
                takePhoto(cropImage = cropImage)
            }
        )
    }
}

@Composable
fun AnimalPhoto(
    photoUri: Uri?,
    isPhotoBusy: Boolean,
    firstStepReadyCallback: () -> Unit?,
) {

    val photoBusy = remember {
        mutableStateOf(false)
    }
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Card(
            modifier = Modifier
                .wrapContentSize()
                .height(maxWidth),
            elevation = 0.dp,
            shape = RoundedCornerShape(0.dp)
        ) {
            SubcomposeAsyncImage(
                model = photoUri,
                contentDescription = "animal_photo",
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            ) {
                when (painter.state) {
                    is AsyncImagePainter.State.Loading -> photoBusy.value = true
                    is AsyncImagePainter.State.Error -> {
                        when (isPhotoBusy) {
                            true -> photoBusy.value = true
                            else -> {
                                photoBusy.value = false
                            }
                        }
                    }
                    else -> {
                        when (isPhotoBusy) {
                            true -> {
                                SubcomposeAsyncImageContent()
                                photoBusy.value = true
                            }
                            else -> {
                                SubcomposeAsyncImageContent()
                                photoBusy.value = false
                            }
                        }
                    }
                }
            }
            if (photoBusy.value) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier,
                        color = petShelterBlue
                    )
                }

            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        PetShelterBtn(
            modifier = Modifier
                .width(205.dp)
                .height(56.dp),
            text = "Указать адрес",
            imageAfter = R.drawable.ic_baseline_arrow_forward_ios_24_btn,
            clickCallback = { firstStepReadyCallback.invoke() }
        )
        Spacer(modifier = Modifier.height(24.dp))

    }
}

fun takePhoto(
    cropImage: ManagedActivityResultLauncher<String, Uri?>,
) {
    cropImage.launch(
        "image/*"
    )
}

fun takePhotoFromCamera(
    context: Context,
    takePhoto: ManagedActivityResultLauncher<Uri, Boolean>,
    setImage: (Uri) -> Unit
) {
    val uri = ComposeFileProvider.getImageUri(context)
    setImage.invoke(uri)
    takePhoto.launch(
        uri
    )
}

@Preview
@Composable
fun FirstStepCreateAnnouncementFormPreview() {
    PetShelterTheme {
        FirstStepCreateAnnouncementForm(
            screenIsBusy = false,
            avatarUri = null,
            firstStepReadyCallback = {},
            addPhotoCallback = {},
        )
    }
}