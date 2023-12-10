package compose.entities

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
    val videoTitle: String?
)
