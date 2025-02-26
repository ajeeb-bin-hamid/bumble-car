package com.ajeeb.bumblecar.main.data.di

import android.content.Context
import com.ajeeb.bumblecar.main.data.repository.GooglePlacesRepositoryImpl
import com.ajeeb.bumblecar.main.domain.repository.GooglePlacesRepository
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GooglePlacesRepositoryModule {

    @Provides
    @Singleton
    fun provideGooglePlacesClient(context: Context): PlacesClient =
        Places.createClient(context)

    @Provides
    @Singleton
    fun provideGooglePlacesRepository(
        placesClient: PlacesClient, context: Context
    ): GooglePlacesRepository =
        GooglePlacesRepositoryImpl(placesClient = placesClient, context = context)
}