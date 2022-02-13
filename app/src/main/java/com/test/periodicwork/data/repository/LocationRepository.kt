package com.test.periodicwork.data.repository

import android.annotation.SuppressLint
import android.app.Application
import android.os.Looper
import com.google.android.gms.location.LocationServices
import io.reactivex.Flowable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.test.periodicwork.data.persistence.Database
import com.test.periodicwork.data.persistence.Location
import com.test.periodicwork.utility.extentions.checkLocationPermission
import com.test.periodicwork.utility.extentions.isGPSEnabled
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(
        private val application: Application,
        private val database: Database
) {

    @SuppressLint("MissingPermission")
    fun getLocation() {
        /*
         * One time location request
         */
        if (application.isGPSEnabled() && application.checkLocationPermission()) {
            LocationServices.getFusedLocationProviderClient(application)
                    ?.lastLocation
                    ?.addOnSuccessListener { location: android.location.Location? ->
                        if (location != null)
                            saveLocation(Location(0, location.latitude, location.longitude, System.currentTimeMillis()))
                    }
        }
    }

    private fun saveLocation(location: Location) = GlobalScope.launch { database.locationDao().insert(location) }

    fun getSavedLocation(): Flowable<List<Location>> = database.locationDao().selectAll()
}