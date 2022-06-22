package com.example.petshelter.authScreens.main.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import com.example.petshelter.R
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.eql.consts.ui.colors.petShelterBlue
import com.eql.consts.ui.colors.petShelterWhite

@Composable
fun FormField(
    modifier: Modifier,
    value:String = "",
    placeHolder: String,
    visibleIcon: Boolean = false,
    valueCallback: () -> Unit

) {
    val visibleText = remember {
        mutableStateOf(false)
    }
    val visualTransformation = if(visibleText.value) VisualTransformation.None else PasswordVisualTransformation()

    Card(
        Modifier.padding(start = 24.dp, end = 24.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 10.dp
    ) {
        TextField(
            modifier = modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = petShelterWhite,

                focusedIndicatorColor = petShelterWhite,
                unfocusedIndicatorColor = petShelterWhite
            ),
            value = value,
            onValueChange = {},
            trailingIcon = {
                if (visibleIcon) {
                    Icon(
                        modifier = Modifier.clickable {
                            visibleText.value=!visibleText.value
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