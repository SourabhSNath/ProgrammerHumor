package dev.sourabh.programmerhumor.data.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Mp4(
    @Json(name = "resolutions")
    val resolutions: List<ResolutionXX>,
    @Json(name = "source")
    val source: SourceXX
)