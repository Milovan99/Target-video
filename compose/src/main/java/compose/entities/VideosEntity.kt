package compose.entities

import com.example.network.entities.DataNetworkEntity
import com.example.network.entities.VideoNetworkEntity

data class VideosEntity(
    val page: Int? = null,
    val searchId: String? = null,
    val dataEntity: List<DataEntity> = listOf()
)

data class DataEntity(
    val id: String?,
    val description: String?,
    val thumbnailImage: String?,
    val videoMp4: String?,
    val videoTitle: String?,
    val searchId: String?
)

fun VideoNetworkEntity.toVideosEntity() = VideosEntity(
    page = page,
    dataEntity = dataNetworkEntity.mapNotNull { it?.toDataEntity(searchId) }
)

fun DataNetworkEntity.toDataEntity(searchId: String?) = DataEntity(
    id = id,
    description = description,
    thumbnailImage = assetsNetworkEntity?.thumbJpgNetworkEntity?.url,
    videoMp4 = assetsNetworkEntity?.previewMp4NetworkEntity?.url,
    videoTitle = originalFilename,
    searchId = searchId
)
