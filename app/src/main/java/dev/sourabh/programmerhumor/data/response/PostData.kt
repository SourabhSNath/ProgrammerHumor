package dev.sourabh.programmerhumor.data.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostData(
    val author: String,
    @Json(name = "created")
    val created: Double,
    @Json(name = "created_utc")
    val createdUtc: Double,
    @Json(name = "id")
    val id: String,
    @Json(name = "is_original_content")
    val isOriginalContent: Boolean,
    @Json(name = "is_self")
    val isSelf: Boolean,
    @Json(name = "is_video")
    val isVideo: Boolean,
    @Json(name = "name")
    val name: String,
    @Json(name = "num_comments")
    val numComments: Int,
    @Json(name = "over_18")
    val over18: Boolean,
    @Json(name = "pinned")
    val pinned: Boolean,
    @Json(name = "post_hint")
    val postHint: String?,
    @Json(name = "score")
    val score: Int,
    @Json(name = "spoiler")
    val spoiler: Boolean,
    @Json(name = "stickied")
    val stickied: Boolean,
    @Json(name = "title")
    val title: String,
    @Json(name = "url")
    val url: String,
    @Json(name = "preview")
    val preview: Preview?,
    @Json(name = "permalink")
    val commentLink: String
)