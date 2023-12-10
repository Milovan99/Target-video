package compose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.media3.common.MediaItem
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val videoUrl = intent.getStringExtra("VIDEO_URL") ?: ""
        val videoDescription = intent.getStringExtra("VIDEO_DESCRIPTION") ?: ""
        val videoTitle = intent.getStringExtra("VIDEO_TITLE") ?: ""

        setContent {

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color(0xFF01111B)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    VideoPlayer(
                        modifier = Modifier.height(200.dp),
                        url = videoUrl,
                        videoTitle
                    )
                    TitleAndDescription(
                        title = videoTitle,
                        description = videoDescription
                    )
                }
            }
        }
    }

    @Composable
    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    fun VideoPlayer(modifier: Modifier, url: String, videoTitle: String) {
        val context = LocalContext.current

        val exoPlayer = remember {
            ExoPlayer.Builder(context)
                .build()
                .apply {
                    val defaultDataSourceFactory = DefaultDataSource.Factory(context)
                    val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(
                        context,
                        defaultDataSourceFactory
                    )
                    val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(MediaItem.fromUri(url))

                    setMediaSource(source)
                    prepare()
                }
        }

        exoPlayer.playWhenReady = true

        Box(modifier) {
            DisposableEffect(
                AndroidView(factory = {
                    PlayerView(context).apply {
                        hideController()
                        useController = true
                        setShowRewindButton(false)
                        setShowFastForwardButton(false)
                        player = exoPlayer
                        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                        title = videoTitle
                    }
                })
            ) {
                onDispose { exoPlayer.release() }
            }
        }
    }

    @Composable
    fun TitleAndDescription(title: String, description: String) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column {
                // Title
                Text(
                    text = title,
                    color = Color.White,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                )

                Text(
                    text = description,
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 12.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                )

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    color = Color(0xFF4b4b54)
                )
            }
        }
    }

}