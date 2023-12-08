package com.milovanjakovljevic.targetvideo.repository

import com.milovanjakovljevic.targetvideo.entities.DataState
import com.milovanjakovljevic.targetvideo.entities.VideosEntity
import com.milovanjakovljevic.targetvideo.network.YoutubeVideosApi
import com.milovanjakovljevic.targetvideo.network.entities.toVideoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideosRepository
@Inject
constructor(private val youtubeVideosApi: YoutubeVideosApi) {

    suspend fun getVideos(pageToken: String): Flow<DataState<VideosEntity?>> = flow {
        emit(DataState.Loading)
        val videosNetworkEntity = youtubeVideosApi.getVideos(
            part = "snippet",
            query = "video",
            maxResults = 20,
            pageToken = pageToken
        )
        emit(DataState.Success(videosNetworkEntity.toVideoEntity()))
    }.flowOn(Dispatchers.IO).catch { emit(DataState.Error(it)) }
}
