package com.milovanjakovljevic.targetvideo.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter.videoClickListener = this
        binding.recyclerViewVideos.adapter = adapter
        val layoutManager = GridLayoutManager(this, 2)
        binding.recyclerViewVideos.layoutManager = layoutManager

        binding.recyclerViewVideos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
                if (!isLoading && (visibleItemCount + firstVisibleItem) >= totalItemCount && firstVisibleItem >= 0
                ) {
                    loadMoreVideoClips()
                }
            }
        })

        subscribeToObservable()
        viewModel.getVideos(1)
    }

    private fun subscribeToObservable() {
        viewModel.videosLiveDataState.observe(this) {
            when (it) {
                is DataState.Loading -> {
                    isLoading = true
                    binding.progressBarLoadingVideos.visibility = View.VISIBLE
                    Timber.d("Videos are loading")
                }

                is DataState.Success -> {
                    isLoading = false
                    if (it.data?.dataEntity != null) {
                        if (adapter.currentList.size > 0) {
                            adapter.submitList(adapter.currentList + it.data.dataEntity)
                        } else {
                            adapter.submitList(it.data.dataEntity)
                        }
                        page += 1
                        searchId = it.data.searchId.toString()
                    }
                    binding.progressBarLoadingVideos.visibility = View.GONE
                }

                is DataState.Error -> {
                    isLoading = false
                    binding.progressBarLoadingVideos.visibility = View.GONE
                    Timber.e(it.throwable)
                }
            }
        }
    }

    private fun loadMoreVideoClips() {
        viewModel.getVideos(page, searchId)
    }

    override fun onVideoClick(videoUrl: String?, videoDescription: String?, videoTitle: String?) {
        Intent(this, PlayerActivity::class.java).apply {
            this.putExtra("VIDEO_URL", videoUrl)
            this.putExtra("VIDEO_DESCRIPTION", videoDescription)
            this.putExtra("VIDEO_TITLE", videoTitle)
            PlayerActivity.apply {
                this.positionOfVideo = 0
                this.isFullScreen = false
            }
            startActivity(this)
        }
    }

    companion object {
        private var page: Int = 1
        private var searchId: String = ""
    }
}
