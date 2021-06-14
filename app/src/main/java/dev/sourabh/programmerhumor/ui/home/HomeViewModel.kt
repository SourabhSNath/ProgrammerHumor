package dev.sourabh.programmerhumor.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sourabh.programmerhumor.data.response.PostData
import dev.sourabh.programmerhumor.repository.RedditRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: RedditRepository) : ViewModel() {

    private val _postsFlow = MutableStateFlow<PagingData<PostData>>(PagingData.empty())
    val postsFlow: StateFlow<PagingData<PostData>> get() = _postsFlow

    init {
        getPosts()
    }

    fun getPosts(sort: String = "hot") {
        viewModelScope.launch {
            repository.getPosts(sort)
                .cachedIn(this) // Required, app crashes otherwise.
                .collect {
                    _postsFlow.value = it
                }
        }
    }
}