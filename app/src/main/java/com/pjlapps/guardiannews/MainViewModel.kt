package com.pjlapps.guardiannews

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pjlapps.guardiannews.data.NewsDetail
import com.pjlapps.guardiannews.data.NewsRepository
import com.pjlapps.guardiannews.domain.NewsListResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MainViewModel @Inject constructor(
    val newsRepository: NewsRepository,
    @Named("ioDispatcher") val coroutineContext: CoroutineContext
): ViewModel() {
    val newsListState = mutableStateOf<NewsListResult?>(null)
    val newsListErrorState = mutableStateOf(false)
    val newsDetailState = mutableStateOf<NewsDetail?>(null)
    val newsDetailErrorState = mutableStateOf(false)
    val newsListLoadingState = mutableStateOf(false)

    fun getNewsList() {
        viewModelScope.launch(coroutineContext) {
            newsListLoadingState.value = true
            newsRepository.getNewsList()
                .onSuccess { result ->
                    newsListLoadingState.value = false
                    newsListState.value = result
                }
                .onFailure { failure ->
                    newsListLoadingState.value = false
                    newsListErrorState.value = true
                }
        }
    }

    fun getNewsDetails(newsId: String) {
        viewModelScope.launch(coroutineContext) {
            val newsDetails = newsRepository.getNewsDetailsById(newsId)
            newsDetails.onSuccess { result ->
                newsDetailState.value = result.newsDetail
            }
            newsDetails.onFailure { failure ->
                newsDetailErrorState.value = true
            }
        }
    }
}