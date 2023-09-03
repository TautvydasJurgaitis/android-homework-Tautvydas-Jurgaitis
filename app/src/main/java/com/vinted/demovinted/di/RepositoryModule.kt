package com.vinted.demovinted.di

import com.vinted.demovinted.data.network.api.Api
import com.vinted.demovinted.data.repository.FeedRepositoryImpl
import com.vinted.demovinted.domain.repository.FeedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
class RepositoryModule {

    @Provides
    fun providesFeedRepository (api: Api): FeedRepository {
        return FeedRepositoryImpl(api)
    }
}