package com.example.petshelter.authScreens.main.components

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import com.example.petshelter.R
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.eql.consts.ui.colors.petShelterBlue
import com.eql.consts.ui.colors.petShelterWhite
import com.eql.consts.ui.colors.validationErrorColor
import com.example.petshelter.ui.styles.validationTextStyle
import com.example.petshelter.utils.UiText

@Composable
fun FormField(
    modifier: Modifier,
    text: String = "",
    placeHolder: String,
    visibleIcon: Boolean = false,
    validationValue: UiText = UiText.EmptyString,
    valueCallback: (String) -> Unit

) {


    val visibleText = remember {
        mutableStateOf(!visibleIcon)
    }

    val visualTransformation =
        if (visibleText.value) VisualTransformation.None else PasswordVisualTransformation()

    val borderColor = when (validationValue) {
        is UiText.EmptyString -> Color.Transparent
        else -> validationErrorColor
    }

    Column(
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp)
    ) {
        Card(
            Modifier
                .border(
                    2.dp,
                    borderColor,
                    RoundedCornerShape(8.dp)
                ),
            shape = RoundedCornerShape(8.dp),
            elevation = 5.dp
        ) {
            Column {
                TextField(
                    modifier = modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = petShelterWhite,

                        focusedIndicatorColor = petShelterWhite,
                        unfocusedIndicatorColor = petShelterWhite
                    ),
                    value = text,
                    onValueChange = { valueCallback.invoke(it) },
                    trailingIcon = {
                        if (visibleIcon) {
                            Icon(
                                modifier = Modifier.clickable {
                                    visibleText.value = !visibleText.value
                                },
                                painter = painterResource(id = if (visibleText.value) R.drawable.ic_visible else R.drawable.ic_visibility_off),
                                contentDescription = "trailing icon",
                                tint = petShelterBlue
                            )
                        }

                    },
                    placeholder = { Text(placeHolder) },
                    visualTransformation = visualTransformation
                )
            }
        }


        Text(
            modifier = Modifier.fillMaxWidth(),
            text = validationValue.asString(),
            style = validationTextStyle,
            maxLines = 1,
        )
    }
}