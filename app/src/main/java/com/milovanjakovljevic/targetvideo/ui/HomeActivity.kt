package com.milovanjakovljevic.targetvideo.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.milovanjakovljevic.targetvideo.databinding.ActivityHomeBinding
import com.milovanjakovljevic.targetvideo.entities.DataState
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), VideoAdapter.IVideoClickListener {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val adapter: VideoAdapter by lazy { VideoAdapter() }
    private var isLoading = false
    private var ss = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter.videoClickListener = this
        binding.recyclerViewVideos.adapter = adapter

        binding.recyclerViewVideos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && (visibleItemCount + firstVisibleItem) >= totalItemCount
                    && firstVisibleItem >= 0 && totalItemCount >= viewModel.videosTuShow.value!!
                ) {
                    loadMoreVideoClips()
                }
            }
        })

        subscribeToObservable()
        //Fixme add page logic and shift id
        viewModel.getVideos(1)
    }

    private fun subscribeToObservable() {
        viewModel.videosLiveDataState.observe(this) {
            when (it) {
                is DataState.Loading -> {
                    isLoading = true
                    Timber.d("Videos are loading")
                }

                is DataState.Success -> {
                    isLoading = false
                    if (it.data?.dataEntity != null) {
                        if (adapter.currentList.size > 0) {
                            adapter.submitList(adapter.currentList + it.data.dataEntity)
                            viewModel.videosTuShow.postValue(viewModel.videosTuShow.value?.plus(20))
                        } else {
                            adapter.submitList(it.data.dataEntity)
                        }
                    }
                }

                is DataState.Error -> Timber.e(it.throwable)
            }
        }
    }

    private fun loadMoreVideoClips() {
        viewModel.getVideos(2)
    }

    //Fixme rename videoId to videoUrl
    override fun onVideoClick(videoId: String) {
        Intent(this, PlayerActivity::class.java).apply {
            this.putExtra("VIDEO_ID", videoId)
            PlayerActivity.apply {
                this.positionOfVideo = 0
                this.isFullScreen = false
            }
            startActivity(this)
        }
    }
}
