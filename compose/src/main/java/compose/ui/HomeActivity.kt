package compose.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import compose.ui.theme.ComposeViewModel
import compose.ui.theme.TargetVideoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TargetVideoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF01111B)
                ) {
                    GridOfVideos()
                }
            }
        }
    }
}

@Composable
fun GridOfVideos(
    composeViewModel: ComposeViewModel = viewModel()
) {
    val videoState by composeViewModel.videoDataState.collectAsState()
    composeViewModel.getVideos(1)
    val context = LocalContext.current
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(videoState?.dataEntity ?: emptyList()) { videoEntity ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clickable {
                        openPlayer(
                            context,
                            videoEntity.videoMp4,
                            videoEntity.videoTitle,
                            videoEntity.description
                        )
                    }

            ) {
                Image(
                    painter = rememberImagePainter(data = videoEntity.thumbnailImage),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                        .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))
                ) {
                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .clipToBounds()
                    ) {
                        drawRoundRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color(0xFF000000)),
                                startY = 0f,
                                endY = size.height
                            ),
                            size = size.copy(height = size.height),
                            topLeft = Offset(0f, 0f),
                            cornerRadius = CornerRadius(8.dp.toPx(), 0f)
                        )
                    }
                    Text(
                        text = videoEntity.videoTitle ?: "No title",
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .align(Alignment.BottomStart),
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        fontSize = 14.sp,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

fun openPlayer(
    context: Context,
    videoUrl: String?,
    videoTitle: String?,
    videoDescription: String?
) {
    val intent = Intent(context, PlayerActivity::class.java).apply {
        putExtra("VIDEO_URL", videoUrl)
        putExtra("VIDEO_TITLE", videoTitle)
        putExtra("VIDEO_DESCRIPTION", videoDescription)
    }
    context.startActivity(intent, null)
}
