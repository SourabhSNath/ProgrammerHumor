package dev.sourabh.programmerhumor.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import coil.Coil
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.size.OriginalSize
import dev.sourabh.programmerhumor.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream

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

@Suppress("BlockingMethodInNonBlockingContext")
suspend fun getBitmapUri(url: String, context: Context): Uri? {
    val bitmap = getCoilImageBitmap(url, context)

    var bmpUri: Uri? = null
    withContext(Dispatchers.IO) {
        try {
            val file = File(context.externalCacheDir, "image.png")
            val fOut = FileOutputStream(file)
            bitmap?.compress(Bitmap.CompressFormat.PNG, 100, fOut)
            fOut.flush()
            fOut.close()
            bmpUri = FileProvider.getUriForFile(
                context.applicationContext,
                BuildConfig.APPLICATION_ID + ".fileprovider",
                file
            )
            Timber.d("$bmpUri")
        } catch (e: Exception) {
            Timber.e(e)
        }
    }
    return bmpUri
}