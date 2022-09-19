package com.example.petshelter.tabScreens.profileTab.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.petshelter.authScreens.main.components.PetShelterBtn
import com.example.petshelter.ui.styles.dialogBtnTextStyle
import com.example.petshelter.ui.styles.dialogTextStyle
import com.example.petshelter.ui.theme.PetShelterTheme

@Composable
fun LogoutDialog(
    isDisplayed: Boolean,
    toggleDialogDisplayCallback: () -> Unit,
    logoutBtnCallback: () -> Unit
) {

    if (isDisplayed) {
        Dialog(onDismissRequest = toggleDialogDisplayCallback) {
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
                            text = "Выйти  из аккаунта",
                            textAlign = TextAlign.Center,
                            style = dialogTextStyle,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            TextButton(
                                modifier = Modifier
                                    .width(140.dp)
                                    .height(56.dp),
                                onClick = logoutBtnCallback,
                            ) {
                                Text(text = "Выйти", style = dialogBtnTextStyle)
                            }
                            PetShelterBtn(
                                modifier = Modifier
                                    .width(140.dp)
                                    .height(56.dp),
                                text = "Остаться",
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

@Preview
@Composable
fun LogoutDialogPreview() {
    PetShelterTheme {
        LogoutDialog(
            true,
            {},
            {}
        )
    }
}