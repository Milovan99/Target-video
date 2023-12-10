package compose.repository

import compose.entities.VideosEntity
import compose.network.ShutterstockVideosApi
import compose.network.entities.toVideosEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideosRepository
@Inject
constructor(private val shutterstockVideosApi: ShutterstockVideosApi) {

    suspend fun getVideos(page: Int, searchId: String): Flow<VideosEntity?> = flow {
        val videosNetworkEntity = shutterstockVideosApi.getVideos(
            page = page,
            searchId = searchId
        )
        emit(videosNetworkEntity.toVideosEntity())
    }.flowOn(Dispatchers.IO).catch { Timber.e(it.message) }
}
