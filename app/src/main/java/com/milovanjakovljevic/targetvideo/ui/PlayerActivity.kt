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

    companion object Info {
        var isFullScreen = false
        var positionOfVideo: Long = 0
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Fixme move to companion object
        val videoUrl = intent.getStringExtra("VIDEO_URL")
        val videoDescription = intent.getStringExtra("VIDEO_DESCRIPTION")
        val title = getTitle(videoDescription)

        binding.textViewTitle.text = title
        binding.textViewDescription.text = videoDescription

        val exoPlayerTitle = findViewById<TextView>(R.id.exo_title)
        exoPlayerTitle.text = title

        player = ExoPlayer.Builder(this).build()
        binding.exoPlayerView.player = player

        if (!videoUrl.isNullOrEmpty()) {
            initializePlayer(videoUrl)
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
        requestedOrientation =
            if (isFullScreen) ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    private fun saveTime() {
        val time = findViewById<TextView>(androidx.media3.ui.R.id.exo_position).text.toString()
        positionOfVideo = timeToSeconds(time)
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


    //I made this functions as improvisation to get video title because JSON response doesn't provide title
    private fun getTitle(string: String?): String {
        val words = string?.split("\\s+".toRegex()) ?: listOf("")

        return when (words.size) {
            0 -> "" // Empty string
            1 -> words[0] // Only one word
            else -> "${words[0]} ${words[1]}" // Two words
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
        saveTime()
    }
}
