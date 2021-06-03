package dev.sourabh.programmerhumor.data.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RedditResponse(
    @Json(name = "data")
    val `data`: Data,
    @Json(name = "kind")
    val kind: String
)