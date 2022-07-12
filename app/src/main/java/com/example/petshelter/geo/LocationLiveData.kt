package com.example.petshelter.geo

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.petshelter.tabScreens.createAnnouncementTab.model.SecondStepLocateData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await

const val TAG = "LOCATE"

class LocationLiveData(val context: Context) : LiveData<SecondStepLocateData>() {

    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)


    companion object {
        val locationRequest: LocationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }


    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        Log.e(TAG, "startLocationUpdates: ")
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null
        )
    }

    @SuppressLint("MissingPermission")
    suspend fun getCurrentPosition(): SecondStepLocateData {
        val location = fusedLocationClient.lastLocation.await()
        return SecondStepLocateData(
            location.latitude,
            location.longitude
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                setLocationData(location)
            }
        }
    }


    private fun setLocationData(location: Location) {
        Log.e(TAG, "setLocationData: ${location.longitude}")
        value = SecondStepLocateData(
            lngPhoto = location.longitude,
            latPhoto = location.latitude
        )
    }

    @SuppressLint("MissingPermission")
    override fun onActive() {
        Log.e(TAG, "onActive: ")
        super.onActive()
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.also {
                    setLocationData(it)
                }
            }
        startLocationUpdates()
    }

    override fun onInactive() {
        super.onInactive()
        fusedLocationClient.removeLocationUpdates(locationCallback)

    }

}