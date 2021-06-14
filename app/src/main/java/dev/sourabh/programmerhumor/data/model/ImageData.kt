package dev.sourabh.programmerhumor.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageData(
    val title: String,
    val url: String,
    val commentUrl: String?
) : Parcelable