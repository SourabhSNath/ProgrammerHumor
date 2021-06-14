package dev.sourabh.programmerhumor.utils

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.core.graphics.drawable.toBitmap
import coil.Coil
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.size.OriginalSize

fun gifImageLoader(context: Context): ImageLoader.Builder = ImageLoader.Builder(context)
    .componentRegistry {
        if (Build.VERSION.SDK_INT >= 28) add(ImageDecoderDecoder(context)) else add(GifDecoder())
    }


suspend fun getCoilImageBitmap(url: String, context: Context): Bitmap? {

    val r = ImageRequest.Builder(context)
        .data(url)
        .diskCachePolicy(CachePolicy.READ_ONLY)
        .size(OriginalSize)
        .build()

    return when (val result = Coil.execute(r)) {
        is SuccessResult -> result.drawable.toBitmap()
        else -> null
    }
}