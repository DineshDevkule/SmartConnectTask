package com.test.smartconnect
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import com.test.smartconnect.di.component.DaggerAppComponent
import com.test.smartconnect.di.module.Provider

class WorkManagerApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
        Provider.appComponent = appComponent
        return appComponent
    }
}
