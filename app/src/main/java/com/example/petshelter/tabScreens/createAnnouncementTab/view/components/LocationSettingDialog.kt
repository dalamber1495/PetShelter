package com.example.petshelter.tabScreens.createAnnouncementTab.view.components

import android.content.Context
import android.content.IntentSender
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority

fun LocationSettingDialog(
    context:Context,
    onSuccess: () -> Unit,
    onFailure: (IntentSenderRequest) -> Unit,
) {




    val locationRequest = LocationRequest.create().apply {
        priority = Priority.PRIORITY_HIGH_ACCURACY
    }
    val locationRequestBuilder = LocationSettingsRequest.Builder()
        .addLocationRequest(locationRequest)
    val locationSettingsResponseTask = LocationServices.getSettingsClient(context)
        .checkLocationSettings(locationRequestBuilder.build())
    locationSettingsResponseTask.addOnSuccessListener {
        onSuccess()
    }
    locationSettingsResponseTask.addOnFailureListener { exception ->
        if (exception is ResolvableApiException){
            try {
                val intentSenderRequest =
                    IntentSenderRequest.Builder(exception.resolution).build()
                onFailure(intentSenderRequest)
            } catch (sendEx: IntentSender.SendIntentException) {
                sendEx.printStackTrace()
            }
        } else {
        }
    }
}