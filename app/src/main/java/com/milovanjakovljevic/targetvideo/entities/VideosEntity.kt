package com.milovanjakovljevic.targetvideo.entities

import com.example.network.entities.DataNetworkEntity
import com.example.network.entities.VideoNetworkEntity

data class VideosEntity(
    val page: Int?,
    val searchId: String?,
    val dataEntity: List<DataEntity>
)

data class DataEntity(
    val id: String?,
    val description: String?,
    val thumbnailImage: String?,
    val videoMp4: String?,
    val videoTitle: String?
)

fun VideoNetworkEntity.toVideosEntity() = VideosEntity(
    page = page,
    searchId = searchId,
    dataEntity = dataNetworkEntity.mapNotNull { it?.toDataEntity() }
)

fun DataNetworkEntity.toDataEntity() = DataEntity(
    id = id,
    description = description,
    thumbnailImage = assetsNetworkEntity?.thumbJpgNetworkEntity?.url,
    videoMp4 = assetsNetworkEntity?.previewMp4NetworkEntity?.url,
    videoTitle = originalFilename
)
