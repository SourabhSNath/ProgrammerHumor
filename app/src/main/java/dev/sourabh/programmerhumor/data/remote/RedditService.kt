package dev.sourabh.programmerhumor.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface RedditService {

    /* raw_json for this reason:
     * https://stackoverflow.com/questions/41909161/reddit-api-how-do-i-convert-amp-to-in-angular-typescript-ionic
     */
    @GET("r/ProgrammerHumor/top.json?raw_json=1")
    suspend fun fetchProgrammerMemes(
        @Query("after") after: String
    )
}