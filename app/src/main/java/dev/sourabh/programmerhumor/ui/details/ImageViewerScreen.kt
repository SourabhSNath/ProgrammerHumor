package dev.sourabh.programmerhumor.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.decode.DataSource
import coil.request.CachePolicy
import coil.size.OriginalSize
import com.google.accompanist.coil.rememberCoilPainter
import dev.sourabh.programmerhumor.data.model.ImageData
import dev.sourabh.programmerhumor.utils.gifImageLoader
import dev.sourabh.programmerhumor.utils.shareImage
import timber.log.Timber
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ImageViewerScreen(navController: NavController, modifier: Modifier) {
    val imageData = navController.previousBackStackEntry?.arguments?.getParcelable<ImageData>("image_data")

    DataSource.MEMORY
    val painter = rememberCoilPainter(
        request = imageData?.url,
        imageLoader = gifImageLoader(LocalContext.current)
            /*Trying to load from cache*/
            .diskCachePolicy(CachePolicy.READ_ONLY)
            .networkCachePolicy(CachePolicy.DISABLED)
            .build(),
        requestBuilder = {
            size(OriginalSize)
        }
    )

    var scaleState by remember { mutableStateOf(1f) }
    var rotationState by remember { mutableStateOf(0f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {

        Image(
            painter = painter, contentDescription = "Image",
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer {
                    // adding some Zoom limits (min 90%, max 300%)
                    scaleX = maxOf(0.9f, minOf(3f, scaleState))
                    scaleY = maxOf(0.9f, minOf(3f, scaleState))
                    rotationZ = rotationState
                    translationX = offsetX
                    translationY = offsetY
                }
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, rotation ->
                        scaleState *= zoom
                        rotationState += rotation

                        val x: Float
                        val y: Float
                        // Pan only if it's zoomed over 100%
                        if (scaleState >= 1.05f) {
                            Timber.d("X: ${pan.x}, Y: ${pan.y}")
                            x = pan.x * zoom
                            y = pan.y * zoom
                        } else {
                            x = 0f
                            y = 0f
                        }
                        val angleRad = rotationState * PI / 180.0
                        offsetX += (x * cos(angleRad) - y * sin(angleRad)).toFloat()
                        offsetY += (x * sin(angleRad) + y * cos(angleRad)).toFloat()
                    }
                }
                .pointerInput(Unit) {
                    detectTapGestures(onDoubleTap = {
                        when {
                            scaleState > 1.75f || scaleState < 1f -> {
                                scaleState = 1f
                                0f.let {
                                    rotationState = it
                                    offsetX = it
                                    offsetY = it
                                }
                            }
                            else -> scaleState = 2.5f
                        }
                    })
                }
                .fillMaxSize()
        )

        val scope = rememberCoroutineScope()
        val context = LocalContext.current
        ExtendedFloatingActionButton(
            modifier = modifier.padding(24.dp).align(Alignment.BottomEnd),
            text = { Text(text = "Share", style = MaterialTheme.typography.body2) },
            icon = { Icon(Icons.Outlined.Share, contentDescription = "Share icon") },
            onClick = {
                imageData?.let { shareImage(scope, it.url, imageData.title, context) {} }
            }
        )
    }
}