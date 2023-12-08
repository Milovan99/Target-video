package com.milovanjakovljevic.targetvideo.entities

data class VideosEntity(
    val nextPageToken: String?,
    val prevPageToken: String?,
    val items: List<ItemsEntity>,
)

data class ItemsEntity(
    val snippet: SnippetEntity? = SnippetEntity()
)

data class SnippetEntity(
    val title: String? = null,
    val description: String? = null,
    val thumbnail: ThumbnailEntity? = null
)

data class ThumbnailEntity(
    val default: DefaultEntity? = null
)

data class DefaultEntity(
    val url: String? = null
)
