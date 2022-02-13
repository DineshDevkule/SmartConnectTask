package com.test.smartconnect.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.test.smartconnect.di.ActivityScoped
import com.test.smartconnect.ui.main.MainActivity

@Module
abstract class ActivityModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [])
    abstract fun mainActivity(): MainActivity

}