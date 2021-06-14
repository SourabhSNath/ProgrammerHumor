package dev.sourabh.programmerhumor.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.sourabh.programmerhumor.data.remote.RedditService
import dev.sourabh.programmerhumor.data.response.PostData
import timber.log.Timber
import java.util.*

class PostsDataSource(private val apiService: RedditService, private val sort: String) :
    PagingSource<String, PostData>() {

    override fun getRefreshKey(state: PagingState<String, PostData>): String? {
        return null
        /*state.anchorPosition?.let { anchorPos ->
                state.closestPageToPosition(anchorPos)?.prevKey ?: state.closestPageToPosition(anchorPos)?.nextKey
            }*/
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, PostData> {
        return try {
            val data = apiService.fetchProgrammerMemes(
                sort = sort.lowercase(Locale.ENGLISH),
                limit = params.loadSize - 1,
                after = params.key
            ).data

            val children = data.children.map { it.postData }
            Timber.d("${data.after}, ${children[0].title}")

            LoadResult.Page(
                data = children,
                prevKey = data.before,
                nextKey = data.after
            )
        } catch (e: Exception) {
            Timber.d("Load Exception $e")
            LoadResult.Error(e)
        }
    }

}