package dev.sourabh.programmerhumor.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import coil.decode.DataSource
import coil.request.CachePolicy
import coil.size.OriginalSize
import com.google.accompanist.coil.rememberCoilPainter
import dev.sourabh.programmerhumor.data.model.ImageData
import dev.sourabh.programmerhumor.utils.gifImageLoader
import timber.log.Timber
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ImageViewerScreen(navController: NavController) {
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
        }`
    )

    var scaleState by remember { mutableStateOf(1f) }
    var rotationState by remember { mutableStateOf(0f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)
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
        }) {

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
                        // Pan only if it's zoomed by 125%
                        if (scaleState >= 1.25f) {
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
                .fillMaxSize()
        )
    }
}