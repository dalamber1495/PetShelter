package com.example.petshelter.utils.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.petshelter.authScreens.main.components.PetShelterBtn
import com.example.petshelter.ui.styles.dialogTextStyle

@Composable
fun PermissionDialog(
    isDisplayed: Boolean,
    toggleDialogDisplayCallback: () -> Unit,
    logoutBtnCallback: () -> Unit
) {

    if (isDisplayed) {
        Dialog(onDismissRequest = toggleDialogDisplayCallback, properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.3f),
                    elevation = 8.dp,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Spacer(modifier = Modifier.fillMaxHeight(0.2f))
                        Text(
                            modifier = Modifier.fillMaxWidth(0.8f),
                            text = "Перейдите в настройки и включите разрешение определения местоположения",
                            textAlign = TextAlign.Center,
                            style = dialogTextStyle,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            PetShelterBtn(
                                modifier = Modifier
                                    .width(140.dp)
                                    .height(56.dp),
                                text = "Закрыть",
                                clickCallback = toggleDialogDisplayCallback
                            )
                        }
                        Spacer(modifier = Modifier.fillMaxHeight(0.1f))
                    }
                }
            }
        }
    }
}