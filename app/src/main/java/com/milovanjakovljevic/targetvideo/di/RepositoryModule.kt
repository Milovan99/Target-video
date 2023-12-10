package com.milovanjakovljevic.targetvideo.di

import com.example.network.ShutterstockVideosApi
import com.milovanjakovljevic.targetvideo.repository.VideosRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideVideosRepository(shutterstockVideosApi: ShutterstockVideosApi): VideosRepository {
        return VideosRepository(shutterstockVideosApi)
    }
}
