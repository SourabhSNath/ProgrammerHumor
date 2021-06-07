package dev.sourabh.programmerhumor.data.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Gildings(
    @Json(name = "gid_1")
    val gid1: Int?
)