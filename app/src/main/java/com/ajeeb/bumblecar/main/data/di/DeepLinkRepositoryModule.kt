package com.ajeeb.bumblecar.main.data.di

import com.ajeeb.bumblecar.main.data.repository.DeepLinkRepositoryImpl
import com.ajeeb.bumblecar.main.domain.repository.DeepLinkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DeepLinkRepositoryModule {

    @Provides
    @Singleton
    fun provideDeepLinkRepository(): DeepLinkRepository = DeepLinkRepositoryImpl()
}