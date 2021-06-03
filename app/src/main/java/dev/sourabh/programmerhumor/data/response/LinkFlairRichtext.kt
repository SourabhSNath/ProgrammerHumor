package dev.sourabh.programmerhumor.data.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LinkFlairRichtext(
    @Json(name = "e")
    val e: String,
    @Json(name = "t")
    val t: String
)