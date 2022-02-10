package com.elevenine.artinstitute

import android.app.Application
import com.elevenine.artinstitute.di.AppComponent
import com.elevenine.artinstitute.di.DaggerAppComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        sAppComponent = DaggerAppComponent.builder().app(this).build()
        sAppComponent.inject(this)
    }

    companion object {

        private lateinit var sAppComponent: AppComponent

        fun getAppComponent(): AppComponent = sAppComponent
    }

}