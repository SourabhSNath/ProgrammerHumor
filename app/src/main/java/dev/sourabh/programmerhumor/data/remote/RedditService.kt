package dev.sourabh.programmerhumor.data.remote

import dev.sourabh.programmerhumor.data.response.RedditResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RedditService {

    /* raw_json for this reason:
     * https://stackoverflow.com/questions/41909161/reddit-api-how-do-i-convert-amp-to-in-angular-typescript-ionic
     */
    @GET("r/ProgrammerHumor/{sort}.json?raw_json=1")
    suspend fun fetchProgrammerMemes(
        @Path("sort") sort: String,
        @Query("limit") limit: Int,
        @Query("after") after: String?
    ): RedditResponse
}