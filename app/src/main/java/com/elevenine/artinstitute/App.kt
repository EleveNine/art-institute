package com.elevenine.artinstitute

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // Instance of the AppComponent that will be used by all the Activities in the project
/*
        FirebaseApp.initializeApp(this)*/
    }

}