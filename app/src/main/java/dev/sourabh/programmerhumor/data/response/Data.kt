package dev.sourabh.programmerhumor.data.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "after")
    val after: String?,
    @Json(name = "before")
    val before: String?,
    @Json(name = "children")
    val children: List<Children>
)