package com.milovanjakovljevic.targetvideo.entities

data class VideosEntity(
    val page: Int?,
    val searchId: String?,
    val dataEntity: List<DataEntity>
)

data class DataEntity(
    val id: String?,
    val description: String?,
    val thumbnailImage: String?,
    val videoMp4: String?
)
