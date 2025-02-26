package com.ajeeb.bumblecar

import android.app.Application
import com.google.android.libraries.places.api.Places
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BumbleCarApp : Application() {
    override fun onCreate() {
        super.onCreate()

        //Initialize places client
        //Hardcoding API keys in the codebase is not recommended, only for demo purposes.
        Places.initialize(this, "AIzaSyCW5mZYZBvxDdoFw2ARpPhnOQfz2xIcz2s")
    }
}