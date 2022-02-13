package com.test.smartconnect.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import com.test.smartconnect.WorkManagerApplication
import com.test.smartconnect.di.module.*
import com.test.smartconnect.utility.workmanager.TrackLocationWorker
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    ActivityModule::class,
    AppModule::class,
    RoomModule::class,
    ViewModelModule::class
])
interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(application: WorkManagerApplication)

    fun inject(worker: TrackLocationWorker)

    override fun inject(instance: DaggerApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }
}