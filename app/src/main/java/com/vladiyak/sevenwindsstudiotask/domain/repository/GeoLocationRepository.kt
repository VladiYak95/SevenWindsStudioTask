package com.vladiyak.sevenwindsstudiotask.domain.repository

import android.location.Location
import kotlinx.coroutines.flow.StateFlow

interface GeoLocationRepository {

    val currentLocation: StateFlow<Location>

    fun refreshCurrentLocation(withLastLocation: Boolean = false)
    fun getDistanceToLocation(latitude: Double, longitude: Double): Float?
}
