package com.test.smartconnect.di.module

import android.app.Application
import android.arch.persistence.room.Room
import dagger.Module
import dagger.Provides
import com.test.smartconnect.data.persistence.Database
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): Database =
            Room.databaseBuilder(application, Database::class.java, "WorkManager.db").build()
}