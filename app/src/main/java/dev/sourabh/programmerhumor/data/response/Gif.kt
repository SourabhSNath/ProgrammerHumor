package dev.sourabh.programmerhumor.data.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Gif(
    @Json(name = "resolutions")
    val resolutions: List<Resolution>?,
    @Json(name = "source")
    val source: Source
)