package com.milovanjakovljevic.targetvideo.ui

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.milovanjakovljevic.targetvideo.R
import com.milovanjakovljevic.targetvideo.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding
    private lateinit var player: ExoPlayer

    companion object {
        private var isFullScreen = false
        private var positionOfVideo: Long = 0
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val videoId = intent.getStringExtra("VIDEO_ID")
        player = ExoPlayer.Builder(this).build()
        binding.exoPlayerView.player = player

        if (!videoId.isNullOrEmpty()) {
            initializePlayer(videoId)
        }
        setController()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun initializePlayer(videoUrl: String) {
        val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
        player.setMediaItem(mediaItem)
        player.prepare()
        player.playWhenReady = true
        player.seekTo(positionOfVideo)
        //Fixme remove action bar in theme
        supportActionBar?.hide()
        val speakerButton = findViewById<ImageView>(R.id.exo_speaker)
        val fullScreenButton = findViewById<ImageView>(R.id.exo_fullScreen)

        speakerButton.setOnClickListener {
            if (speakerButton.isSelected) unmuteVideo() else muteVideo()
            speakerButton.isSelected = !speakerButton.isSelected
        }
        fullScreenButton.setOnClickListener {
            toggleFullScreen()
        }
    }

    private fun toggleFullScreen() {
        val time = findViewById<TextView>(androidx.media3.ui.R.id.exo_position).text.toString()
        positionOfVideo = timeToSeconds(time)
        requestedOrientation =
            if (isFullScreen) ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    private fun timeToSeconds(timeString: String): Long {
        val parts = timeString.split(":")

        // Extract hours, minutes, and seconds from the parts
        val hours = if (parts.size > 2) parts[0].toLong() else 0
        val minutes = parts[parts.size - 2].toLong()
        val seconds = parts[parts.size - 1].toLong()

        // Calculate total duration in seconds
        return (hours * 3600 + minutes * 60 + seconds) * 1000
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun setController() {
        val controller = window.insetsController
        if (isFullScreen) {
            controller?.hide(WindowInsetsCompat.Type.systemBars())
        } else {
            controller?.show(WindowInsetsCompat.Type.systemBars())
        }
        isFullScreen = !isFullScreen
    }

    private fun muteVideo() {
        player.volume = 0f
    }

    private fun unmuteVideo() {
        player.volume = 1.0f
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        positionOfVideo = 0
        isFullScreen = false
    }
}
