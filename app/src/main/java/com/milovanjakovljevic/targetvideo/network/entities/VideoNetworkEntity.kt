package com.milovanjakovljevic.targetvideo.network.entities

import com.google.gson.annotations.SerializedName
import com.milovanjakovljevic.targetvideo.entities.ItemsEntity
import com.milovanjakovljevic.targetvideo.entities.SnippetEntity
import com.milovanjakovljevic.targetvideo.entities.VideosEntity

data class VideoNetworkEntity(
    val kind: String? = null,
    val etag: String? = null,
    val nextPageToken: String? = null,
    val prevPageToken: String? = null,
    val regionToken: String? = null,
    @SerializedName("pageInfo")
    val pageInfoNetworkEntity: PageInfoNetworkEntity = PageInfoNetworkEntity(),
    @SerializedName("items")
    val itemsNetworkEntities: List<ItemsNetworkEntity> = listOf()
)

data class PageInfoNetworkEntity(
    val totalResults: Long? = null,
    val resultPerPage: Int? = null
)

data class ItemsNetworkEntity(
    val kind: String? = null,
    val etag: String? = null,
    @SerializedName("id")
    val idNetworkEntity: IdNetworkEntity = IdNetworkEntity(),
    @SerializedName("snippet")
    val snippetNetworkEntity: SnippetNetworkEntity? = SnippetNetworkEntity()
)

data class IdNetworkEntity(
    val kind: String? = null,
    val videoId: String? = null
)

data class SnippetNetworkEntity(
    val publishedAt: String? = null,
    val channelId: String? = null,
    val title: String? = null,
    val description: String? = null,
    @SerializedName("thumbnails")
    val thumbnailsNetworkEntity: ThumbnailsNetworkEntity? = ThumbnailsNetworkEntity(),
    val channelTitle: String? = null,
    val liveBroadcastContent: String? = null,
    val publishTime: String? = null
)

data class ThumbnailsNetworkEntity(
    @SerializedName("default")
    val defaultNetworkEntity: DefaultNetworkEntity? = DefaultNetworkEntity(),
    @SerializedName("medium")
    val mediumNetworkEntity: MediumNetworkEntity? = MediumNetworkEntity(),
    @SerializedName("height")
    val heightNetworkEntity: HeightNetworkEntity? = HeightNetworkEntity()
)

data class DefaultNetworkEntity(
    val url: String? = null,
    val width: Int? = null,
    val height: Int? = null
)

data class MediumNetworkEntity(
    val url: String? = null,
    val width: Int? = null,
    val height: Int? = null
)

data class HeightNetworkEntity(
    val url: String? = null,
    val width: Int? = null,
    val height: Int? = null
)

fun VideoNetworkEntity.toVideoEntity() = VideosEntity(
    nextPageToken = nextPageToken,
    prevPageToken = prevPageToken,
    items = itemsNetworkEntities.map { it.toItemsEntity() }
)

fun ItemsNetworkEntity.toItemsEntity() = ItemsEntity(
    snippet = snippetNetworkEntity?.toSnippetEntity()
)

fun SnippetNetworkEntity.toSnippetEntity() = SnippetEntity(
    title = title,
    description = description
)
