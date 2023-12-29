package com.vladiyak.sevenwindsstudiotask.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.vladiyak.sevenwindsstudiotask.utils.hasLocationPermission
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class GeoLocationRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val _currentLocation = MutableStateFlow(Location(null))
    val currentLocation = _currentLocation.asStateFlow()

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    init {
        refreshCurrentLocation(true)
    }

    @SuppressLint("MissingPermission")
    fun refreshCurrentLocation(withLastLocation: Boolean) {
        if (!context.hasLocationPermission()) return

        if (withLastLocation) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    _currentLocation.value = it
                }
            }
        }

        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            CancellationTokenSource().token
        ).addOnSuccessListener { location: Location? ->
            location?.let {
                _currentLocation.value = it
            }
        }
    }

    fun getDistanceToLocation(latitude: Double, longitude: Double): Float? {
        val location = _currentLocation.value
        if (location.provider == null) {
            return null
        }

        val results = FloatArray(3)
        Location.distanceBetween(
            location.latitude, location.longitude,
            latitude, longitude,
            results
        )
        return results[0]
    }
}