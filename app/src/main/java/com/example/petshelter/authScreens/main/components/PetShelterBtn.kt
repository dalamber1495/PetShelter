package com.example.petshelter.authScreens.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eql.consts.ui.colors.petShelterBlue
import com.example.petshelter.R
import com.example.petshelter.authScreens.main.consts.btnTextStyle
import com.example.petshelter.ui.theme.PetShelterTheme

@Composable
fun PetShelterBtn(
    modifier: Modifier,
    text: String,
    image: Int? = null,
    imageAfter: Int? = null,
    clickCallback: () -> Unit
) {
    Button(
        modifier = modifier
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = petShelterBlue
        ), onClick = clickCallback
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            image?.let {
                Image(
                    modifier = Modifier.height(23.dp),
                    painter = painterResource(id = it),
                    contentDescription = "logo_button",
                )

                Spacer(modifier = Modifier.width(10.dp))
            }
            Text(
                text,
                style = btnTextStyle
            )

            imageAfter?.let {
                Spacer(modifier = Modifier.width(10.dp))
                Image(
                    modifier = Modifier.height(23.dp),
                    painter = painterResource(id = it),
                    contentDescription = "logo_button",
                )

            }
        }
    }
}

@Preview
@Composable
fun PetShelterBtnPreview() {
    PetShelterTheme {
        PetShelterBtn(
            Modifier
                .width(147.dp)
                .height(60.dp)
                .shadow(elevation = 8.dp),
            "Войти",
            clickCallback = { /*TODO*/ })

    }
}