package com.example.petshelter.authScreens.main.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.eql.consts.ui.colors.petShelterWhite

@Composable
fun FormField(
    modifier: Modifier,
    placeHolder:String,
    valueCallback:()->Unit
){
    Card(
        Modifier.padding(start = 24.dp,end = 24.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 10.dp
    ) {
        TextField(modifier = modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = petShelterWhite,

                focusedIndicatorColor = petShelterWhite,
                unfocusedIndicatorColor = petShelterWhite
            ),
            value = "",
            onValueChange = {},
            placeholder = { Text(placeHolder) })
    }
}