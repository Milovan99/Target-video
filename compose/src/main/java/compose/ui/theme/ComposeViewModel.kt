package compose.ui.theme


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import compose.entities.VideosEntity
import compose.repository.VideosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComposeViewModel
@Inject constructor(private val videosRepository: VideosRepository) : ViewModel() {

    private val _videosDataState: MutableStateFlow<VideosEntity?> = MutableStateFlow(VideosEntity())
    val videoDataState: StateFlow<VideosEntity?> = _videosDataState.asStateFlow()

    fun getVideos(page: Int, searchId: String = "") {
        viewModelScope.launch {
            videosRepository.getVideos(page, searchId).onEach {
                _videosDataState.value = it
            }.launchIn(this)
        }
    }
}
