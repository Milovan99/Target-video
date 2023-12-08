package com.milovanjakovljevic.targetvideo.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.milovanjakovljevic.targetvideo.databinding.ActivityHomeBinding
import com.milovanjakovljevic.targetvideo.entities.DataState
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        subscribeToObservable()
        viewModel.getVideos()
    }

    private fun subscribeToObservable() {
        viewModel.videosLiveDataState.observe(this) {
            when (it) {
                is DataState.Loading -> Timber.d("Videos are loading")
                is DataState.Success -> Timber.d(it.data?.items?.mapNotNull { itemsEntity -> itemsEntity.snippet?.title }
                    .toString())

                is DataState.Error -> Timber.e(it.throwable)
            }
        }
    }
}
