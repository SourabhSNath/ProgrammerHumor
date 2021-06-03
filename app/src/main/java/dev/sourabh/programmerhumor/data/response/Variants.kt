package dev.sourabh.programmerhumor.data.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Variants(
    @Json(name = "gif")
    val gif: Gif,
    @Json(name = "mp4")
    val mp4: Mp4
)