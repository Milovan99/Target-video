package com.milovanjakovljevic.targetvideo.network

import com.milovanjakovljevic.targetvideo.network.entities.VideoNetworkEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeVideosApi {
    @GET("youtube/v3/search")
    suspend fun getVideos(
        @Query("part") part: String,
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int,
        @Query("pageToken") pageToken: String?
    ): VideoNetworkEntity
}
