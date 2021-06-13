package dev.sourabh.programmerhumor.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Preview(
    @Json(name = "images")
    val images: List<Image>?
) {
    @JsonClass(generateAdapter = true)
    data class Image(
        @Json(name = "source")
        val source: Source?
    )
}