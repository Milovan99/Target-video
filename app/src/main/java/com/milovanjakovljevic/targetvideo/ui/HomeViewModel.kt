package com.milovanjakovljevic.targetvideo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.milovanjakovljevic.targetvideo.entities.DataState
import com.milovanjakovljevic.targetvideo.entities.VideosEntity
import com.milovanjakovljevic.targetvideo.repository.VideosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject constructor(private val videosRepository: VideosRepository) : ViewModel() {

    val videosLiveDataState: LiveData<DataState<VideosEntity?>>
        get() = mutableVideosDataState

    private val mutableVideosDataState: MutableLiveData<DataState<VideosEntity?>> =
        MutableLiveData()

    fun getVideos(pageToken: String = "") {
        viewModelScope.launch {
            videosRepository.getVideos(pageToken).onEach {
                mutableVideosDataState.postValue(it)
            }.launchIn(this)
        }
    }
}
