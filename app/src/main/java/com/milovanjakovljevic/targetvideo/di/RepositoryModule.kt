package com.milovanjakovljevic.targetvideo.di

import com.milovanjakovljevic.targetvideo.network.YoutubeVideosApi
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
    fun provideVideosRepository(youtubeVideosApi: YoutubeVideosApi): VideosRepository {
        return VideosRepository(youtubeVideosApi)
    }
}
