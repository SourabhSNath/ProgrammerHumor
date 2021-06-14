package dev.sourabh.programmerhumor.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dev.sourabh.programmerhumor.data.remote.RedditService
import dev.sourabh.programmerhumor.repository.paging.PostsDataSource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class RedditRepository @Inject constructor(private val api: RedditService) {

    fun getPosts(sort: String, pageSize: Int = 25) = Pager(
        config = PagingConfig(pageSize = pageSize)
    ) {
        PostsDataSource(api, sort)
    }.flow
}