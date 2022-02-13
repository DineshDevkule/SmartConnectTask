package com.test.periodicwork.ui.main

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import io.reactivex.rxkotlin.subscribeBy
import com.test.periodicwork.data.models.Response
import com.test.periodicwork.data.persistence.Location
import com.test.periodicwork.data.repository.Repository
import com.test.periodicwork.ui.base.BaseViewModel
import com.test.periodicwork.utility.RxBus
import com.test.periodicwork.utility.extentions.addTo
import com.test.periodicwork.utility.extentions.fromWorkerToMain
import com.test.periodicwork.utility.rx.Scheduler
import com.test.periodicwork.utility.workmanager.TrackLocationWorker
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainViewModel @Inject constructor(
        private val repository: Repository,
        private val scheduler: Scheduler,
        private val application: Application,
        private val locationRequest: LocationRequest,
        private val bus: RxBus<Any>
) : BaseViewModel() {

    companion object {
        const val LOCATION_WORK_TAG = "LOCATION_WORK_TAG"
    }

    val enableLocation: MutableLiveData<Response<Boolean>> = MutableLiveData()
    val location: MutableLiveData<Response<List<Location>>> = MutableLiveData()

    fun locationSetup() {
        enableLocation.value = Response.loading()
        LocationServices.getSettingsClient(application)
                .checkLocationSettings(
                        LocationSettingsRequest.Builder()
                                .addLocationRequest(locationRequest)
                                .setAlwaysShow(true)
                                .build())
                .addOnSuccessListener { enableLocation.value = Response.success(true) }
                .addOnFailureListener {
                    Timber.e(it, "Gps not enabled")
                    enableLocation.value = Response.error(it)
                }
    }

    fun trackLocation() {
        val locationWorker = PeriodicWorkRequestBuilder<TrackLocationWorker>(15, TimeUnit.MINUTES).addTag(LOCATION_WORK_TAG).build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(LOCATION_WORK_TAG, ExistingPeriodicWorkPolicy.KEEP, locationWorker)
    }

    fun stopTrackLocation() {
        WorkManager.getInstance().cancelAllWorkByTag(LOCATION_WORK_TAG)
    }

    fun getSavedLocation() {
        repository.location.getSavedLocation()
                .fromWorkerToMain(scheduler)
                .subscribeBy(
                        onNext = {
                            location.value = Response.success(it)
                        },
                        onError = {
                            Timber.e(it, "Error in getting saved locations")
                            location.value = Response.error(it)
                        }
                )
                .addTo(getCompositeDisposable())
    }
}