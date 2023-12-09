package com.milovanjakovljevic.targetvideo.network

import com.milovanjakovljevic.targetvideo.network.entities.VideoNetworkEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface ShutterstockVideosApi {
    @GET("/v2/videos/search")
    suspend fun getVideos(
        @Query("page") page: Int,
        @Query("search_id") searchId: String,
    ): VideoNetworkEntity
}
