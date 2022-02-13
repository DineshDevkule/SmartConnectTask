package com.test.periodicwork.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.test.periodicwork.di.ActivityScoped
import com.test.periodicwork.ui.main.MainActivity

@Module
abstract class ActivityModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [])
    abstract fun mainActivity(): MainActivity

}