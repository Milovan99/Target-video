package com.milovanjakovljevic.targetvideo.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.milovanjakovljevic.targetvideo.databinding.ActivityHomeBinding
import com.milovanjakovljevic.targetvideo.entities.DataState
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), VideoAdapter.IVideoClickListener {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val adapter: VideoAdapter by lazy { VideoAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter.videoClickListener = this
        binding.recyclerViewVideos.adapter = adapter
        subscribeToObservable()
        viewModel.getVideos(1)
    }

    private fun subscribeToObservable() {
        viewModel.videosLiveDataState.observe(this) {
            when (it) {
                is DataState.Loading -> Timber.d("Videos are loading")
                is DataState.Success -> adapter.submitList(it.data?.dataEntity)
                is DataState.Error -> Timber.e(it.throwable)
            }
        }
    }

    override fun onVideoClick(videoId: String) {
        Intent(this, PlayerActivity::class.java).apply {
            this.putExtra("VIDEO_ID", videoId)
            startActivity(this)
        }
    }
}
