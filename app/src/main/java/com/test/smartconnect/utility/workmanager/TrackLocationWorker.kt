package com.test.smartconnect.utility.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.test.smartconnect.data.repository.Repository
import com.test.smartconnect.di.module.Provider
import timber.log.Timber
import javax.inject.Inject

class TrackLocationWorker @Inject constructor(
        context: Context,
        workerParams: WorkerParameters
) : Worker(context, workerParams) {

    @Inject lateinit var repository: Repository

    init {
        Provider.appComponent?.inject(this)
    }

    override fun doWork(): Result {
        return try {
            repository.location.getLocation()
            Result.SUCCESS
        } catch (e: Exception) {
            Timber.e(e, "Failure in doing work")
            Result.FAILURE
        }
    }
}