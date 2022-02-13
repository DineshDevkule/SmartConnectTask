package com.test.periodicwork.di.module

import android.app.Application
import android.content.Context
import com.google.android.gms.location.LocationRequest
import dagger.Binds
import dagger.Module
import dagger.Provides
import com.test.periodicwork.utility.RxBus
import com.test.periodicwork.utility.rx.AppScheduler
import com.test.periodicwork.utility.rx.Scheduler
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Binds
    abstract fun provideContext(application: Application): Context

    @Module
    companion object {

        @Provides
        @Singleton
        @JvmStatic
        fun provideScheduler(): Scheduler = AppScheduler()

        @Provides
        @Singleton
        @JvmStatic
        fun locationRequest(): LocationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 3 * 1000
            fastestInterval = 5 * 1000
        }

        @Provides
        @Singleton
        @JvmStatic
        fun provideRxBus(): RxBus<Any> = RxBus()
    }
}