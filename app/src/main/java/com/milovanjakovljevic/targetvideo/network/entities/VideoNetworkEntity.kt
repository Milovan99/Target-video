package com.milovanjakovljevic.targetvideo.network.entities

import com.google.gson.annotations.SerializedName
import com.milovanjakovljevic.targetvideo.entities.DataEntity
import com.milovanjakovljevic.targetvideo.entities.VideosEntity

data class VideoNetworkEntity(
    val page: Int? = null,
    @SerializedName("per_page")
    val perPage: Int? = null,
    @SerializedName("total_count")
    val totalCount: Int? = null,
    @SerializedName("search_id")
    val searchId: String? = null,
    @SerializedName("data")
    val dataNetworkEntity: List<DataNetworkEntity?> = listOf()
)

data class DataNetworkEntity(
    val id: String? = null,
    val aspect: Float? = null,
    @SerializedName("aspect_ratio")
    val aspectRatio: String? = null,
    @SerializedName("assets")
    val assetsNetworkEntity: AssetsNetworkEntity? = AssetsNetworkEntity(),
    @SerializedName("contributor")
    val contributorNetworkEntity: ContributorNetworkEntity? = ContributorNetworkEntity(),
    val description: String? = null,
    val duration: Int? = null,
    @SerializedName("has_model_release")
    val hasModelRelease: Boolean? = null,
    @SerializedName("media_type")
    val mediaType: String? = null,
    @SerializedName("original_filename")
    val originalFilename: String? = null
)

data class AssetsNetworkEntity(
    @SerializedName("thumb_webm")
    val thumbWebmNetworkEntity: ThumbWebmNetworkEntity? = ThumbWebmNetworkEntity(),
    @SerializedName("thumb_mp4")
    val thumbMp4NetworkEntity: ThumbMp4NetworkEntity? = ThumbMp4NetworkEntity(),
    @SerializedName("preview_webm")
    val previewWebmNetworkEntity: PreviewWebmNetworkEntity? = PreviewWebmNetworkEntity(),
    @SerializedName("preview_mp4")
    val previewMp4NetworkEntity: PreviewMp4NetworkEntity? = PreviewMp4NetworkEntity(),
    @SerializedName("thumb_jpg")
    val thumbJpgNetworkEntity: ThumbJpgNetworkEntity? = ThumbJpgNetworkEntity(),
    @SerializedName("preview_jpg")
    val previewJpgNetworkEntity: PreviewJpgNetworkEntity? = PreviewJpgNetworkEntity()
)

data class ContributorNetworkEntity(
    val id: String? = null
)

data class ThumbWebmNetworkEntity(
    val url: String? = null
)

data class ThumbMp4NetworkEntity(
    val url: String? = null
)

data class PreviewWebmNetworkEntity(
    val url: String? = null
)

data class PreviewMp4NetworkEntity(
    val url: String? = null
)

data class ThumbJpgNetworkEntity(
    val url: String? = null
)

data class PreviewJpgNetworkEntity(
    val url: String? = null
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
    videoMp4 = assetsNetworkEntity?.previewMp4NetworkEntity?.url
)
