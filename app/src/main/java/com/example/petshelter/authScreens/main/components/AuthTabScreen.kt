package com.example.petshelter.authScreens.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.petshelter.R
import com.example.petshelter.authScreens.main.model.AuthUiState
import com.example.petshelter.navigation.routeObject.AppScreens

@Composable
fun AuthTabScreen(
    uiState: AuthUiState,
    forgetPassCallback: (AppScreens) -> Unit,
    joinEmailCallback: (String) -> Unit,
    joinPasswordCallback: (String) -> Unit,
    nameCallback: (String) -> Unit,
    registerEmailCallback: (String) -> Unit,
    registerPasswordCallback: (String) -> Unit,
    repeatPasswordCallback: (String) -> Unit,
    registerCallback: () -> Unit,
    joinCallback: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(vertical = 30.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxHeight(0.050f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.padding(end = 10.dp),
                painter = painterResource(id = R.drawable.ic_pet_logo2),
                contentDescription = "logo2",
            )
            Image(
                modifier = Modifier.fillMaxHeight(0.75f),
                painter = painterResource(id = R.drawable.ic_pet_shelter2),
                contentDescription = "logoText2",
            )
        }

        Spacer(modifier = Modifier.height(15.dp))
        AuthTabs(
            uiState = uiState,
            forgetPassCallback = forgetPassCallback,
            joinEmailCallback = joinEmailCallback,
            joinPasswordCallback = joinPasswordCallback,
            nameCallback = nameCallback,
            registerEmailCallback = registerEmailCallback,
            registerPasswordCallback = registerPasswordCallback,
            repeatPasswordCallback = repeatPasswordCallback,
            registerCallback = registerCallback,
            joinCallback = joinCallback
        )
    }
}