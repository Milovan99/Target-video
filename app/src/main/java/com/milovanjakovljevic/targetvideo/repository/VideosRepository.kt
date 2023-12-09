package com.milovanjakovljevic.targetvideo.repository

import com.milovanjakovljevic.targetvideo.entities.DataState
import com.milovanjakovljevic.targetvideo.entities.VideosEntity
import com.milovanjakovljevic.targetvideo.network.ShutterstockVideosApi
import com.milovanjakovljevic.targetvideo.network.entities.toVideosEntity

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
constructor(private val shutterstockVideosApi: ShutterstockVideosApi) {

    suspend fun getVideos(page: Int, searchId: String): Flow<DataState<VideosEntity?>> = flow {
        emit(DataState.Loading)
        val videosNetworkEntity = shutterstockVideosApi.getVideos(
            page = page,
            searchId = searchId
        )
        emit(DataState.Success(videosNetworkEntity.toVideosEntity()))
    }.flowOn(Dispatchers.IO).catch { emit(DataState.Error(it)) }
}
